package com.akdev.covid19tracker.ui.dashboard;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.akdev.covid19tracker.R;
import com.akdev.covid19tracker.model.CovidData;
import com.akdev.covid19tracker.ui.dashboard.adapter.DashboardAdapter;

import java.util.ArrayList;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private DashboardAdapter dashboardAdapter;

    @BindView(R.id.rvDashboard)
    RecyclerView rvDashboard;

    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.btnClearSearch)
    Button btnClearSearch;

    private List<CovidData> filterDataList = new ArrayList<>();
    private List<CovidData> covidDataList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, root);

        dashboardViewModel.getCovidData().observe(getViewLifecycleOwner(), items -> {
            covidDataList.clear();
            filterDataList.clear();

            covidDataList.addAll(items);
            filterDataList.addAll(items);
            dashboardAdapter = new DashboardAdapter(getContext(), filterDataList);
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

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterByKeyWords(s.toString());
            }
        });

        btnClearSearch.setOnClickListener(v -> {
            etSearch.setText("");
        });
        return root;
    }

    private void filterByKeyWords(String k) {
        if (k.length() > 1) {
            List<CovidData> results = new ArrayList<>();
            for (CovidData data : covidDataList) {
                if (data.getLocation().getCountry().toLowerCase().contains(k.toLowerCase())
                        || data.getLocation().getProvince().toLowerCase().contains(k.toLowerCase())) {
                    results.add(data);
                }
            }
            filterDataList.clear();
            filterDataList.addAll(results);
            dashboardAdapter.notifyDataSetChanged();
        } else {
            filterDataList.clear();
            filterDataList.addAll(covidDataList);
            dashboardAdapter.notifyDataSetChanged();
        }
    }
}
