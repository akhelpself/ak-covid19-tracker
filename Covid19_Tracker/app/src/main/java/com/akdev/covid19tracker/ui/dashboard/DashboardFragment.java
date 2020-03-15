package com.akdev.covid19tracker.ui.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.akdev.covid19tracker.R;
import com.akdev.covid19tracker.ui.dashboard.adapter.DashboardAdapter;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private DashboardAdapter dashboardAdapter;

    @BindView(R.id.rvDashboard)
    RecyclerView rvDashboard;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, root);

        dashboardViewModel.getCovidData().observe(getViewLifecycleOwner(), items -> {
            dashboardAdapter = new DashboardAdapter(getContext(), items);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            rvDashboard.setLayoutManager(manager);
            rvDashboard.setAdapter(dashboardAdapter);
        });

        dashboardViewModel.getDialog().observe(getViewLifecycleOwner(), dialogMsg -> {
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
