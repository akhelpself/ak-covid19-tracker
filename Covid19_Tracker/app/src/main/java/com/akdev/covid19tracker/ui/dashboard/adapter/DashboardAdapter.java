package com.akdev.covid19tracker.ui.dashboard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akdev.covid19tracker.R;
import com.akdev.covid19tracker.model.CovidData;

import java.text.DecimalFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<CovidData> items;
    private Context context;

    public DashboardAdapter(Context context, List<CovidData> items) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_report, parent, false);
        return new DashboardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvCountry.setText(items.get(position).getLocation().getCountry());
        holder.tvProvince.setText(items.get(position).getLocation().getProvince());

        String pattern = "###,###";
        final DecimalFormat decimalFormat = new DecimalFormat(pattern);
        holder.tvConfirmed.setText(decimalFormat.format(items.get(position).getConfirmed()));
        holder.tvDeaths.setText(decimalFormat.format(items.get(position).getDeaths()));
        holder.tvRecovered.setText(decimalFormat.format(items.get(position).getRecovered()));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvCountry)
        TextView tvCountry;

        @BindView(R.id.tvProvince)
        TextView tvProvince;

        @BindView(R.id.tvConfirmed)
        TextView tvConfirmed;

        @BindView(R.id.tvDeaths)
        TextView tvDeaths;

        @BindView(R.id.tvRecovered)
        TextView tvRecovered;


        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, itemView);
        }
    }

}
