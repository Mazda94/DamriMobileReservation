package com.example.mazda.requestpermission;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.mazda.requestpermission.Model.Rute;
import com.example.mazda.requestpermission.Model.Terminal;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class RouteListAdapter extends ArrayAdapter<Rute> {

    private ArrayList<Rute> rutes;
    Context context;

    public RouteListAdapter(Context context, ArrayList<Rute> rutes) {
        super(context, R.layout.route_row, rutes);
        this.rutes = rutes;
        this.context = context;
    }

    private static class ViewHolder {
        TextView textViewTipeBus;
        TextView textViewHargaTiket;
        TextView textViewTerminalOrigin;
        TextView textViewTerminalDestinasi;
        TextView textViewDepartureDate;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Rute rute = getItem(position);
        RouteListAdapter.ViewHolder viewHolder;

        final View result;
        if (convertView == null) {
            viewHolder = new RouteListAdapter.ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.route_row, parent, false);
            viewHolder.textViewTipeBus = convertView.findViewById(R.id.tipe_bus);
            viewHolder.textViewHargaTiket = convertView.findViewById(R.id.harga_tiket);
            viewHolder.textViewDepartureDate = convertView.findViewById(    R.id.departure_date);
            viewHolder.textViewTerminalOrigin = convertView.findViewById(R.id.terminal_origin);
            viewHolder.textViewTerminalDestinasi = convertView.findViewById(R.id.terminal_tujuan);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RouteListAdapter.ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.textViewTipeBus.setText(rute.getTipeBus());
        viewHolder.textViewHargaTiket.setText(rute.getHargaTiket());
        viewHolder.textViewDepartureDate.setText(rute.getDepartureDate());
        viewHolder.textViewTerminalOrigin.setText(rute.getOrgName());
        viewHolder.textViewTerminalDestinasi.setText(rute.getDesName());
        return convertView;
    }
}
