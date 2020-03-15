package com.akdev.covid19tracker.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.text.DecimalFormat;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    @BindView(R.id.tvConfirmed)
    TextView tvConfirmed;

    @BindView(R.id.tvDeaths)
    TextView tvDeaths;

    @BindView(R.id.tvRecovered)
    TextView tvRecovered;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        String pattern = "###,###";
        final DecimalFormat decimalFormat = new DecimalFormat(pattern);

        homeViewModel.getLatestData().observe(getViewLifecycleOwner(), latestData -> {
            tvConfirmed.setText(decimalFormat.format(latestData.getConfirmed()));
            tvDeaths.setText(decimalFormat.format(latestData.getDeaths()));
            tvRecovered.setText(decimalFormat.format(latestData.getRecovered()));
        });

        homeViewModel.getDialog().observe(getViewLifecycleOwner(), dialogMsg -> {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle(getString(R.string.dialog_error_header));
            alertDialog.setMessage(dialogMsg);
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    (dialog, which) -> dialog.dismiss()
            );
            alertDialog.show();
        });
        return root;
    }
}
