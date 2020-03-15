package com.akdev.covid19tracker.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.akdev.covid19tracker.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SymptomsFragment extends Fragment {

    private SymptomsViewModel symptomsViewModel;

    @BindView(R.id.tvHowItSpreadsDesc)
    TextView tvHowItSpreadsDesc;

    @BindView(R.id.tvCleanYourHands)
    TextView tvCleanYourHands;

    @BindView(R.id.tvAvoidCloseContact)
    TextView tvAvoidCloseContact;

    @BindView(R.id.tvStayHome)
    TextView tvStayHome;

    @BindView(R.id.tvCoverCough)
    TextView tvCoverCough;

    @BindView(R.id.tvWearFaceMask)
    TextView tvWearFaceMask;

    @BindView(R.id.tvCleanAndDisinfect)
    TextView tvCleanAndDisinfect;

    @BindView(R.id.adView)
    AdView adView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        symptomsViewModel = ViewModelProviders.of(this).get(SymptomsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_symptoms, container, false);
        ButterKnife.bind(this, root);

        symptomsViewModel.getTextHowItSpreadsDesc().observe(getViewLifecycleOwner(), tvHowItSpreadsDesc::setText);
        symptomsViewModel.getTextCleanYourHands().observe(getViewLifecycleOwner(), tvCleanYourHands::setText);
        symptomsViewModel.getTextVoidCloseContact().observe(getViewLifecycleOwner(), tvAvoidCloseContact::setText);
        symptomsViewModel.getTextStayHome().observe(getViewLifecycleOwner(), tvStayHome::setText);
        symptomsViewModel.getTextCoverCough().observe(getViewLifecycleOwner(), tvCoverCough::setText);
        symptomsViewModel.getTextWearFaceMask().observe(getViewLifecycleOwner(), tvWearFaceMask::setText);
        symptomsViewModel.getTextCleanAndDisinfect().observe(getViewLifecycleOwner(), tvCleanAndDisinfect::setText);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        return root;
    }
}
