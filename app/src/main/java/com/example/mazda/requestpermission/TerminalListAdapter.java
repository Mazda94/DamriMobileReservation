package com.example.mazda.requestpermission;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.mazda.requestpermission.Model.Terminal;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TerminalListAdapter extends ArrayAdapter<Terminal> {

    private ArrayList<Terminal> terminals;
    Context context;

    public TerminalListAdapter(@NonNull Context context, ArrayList<Terminal> terminals) {
        super(context, R.layout.terminal_row, terminals);
        this.terminals = terminals;
        this.context = context;
    }



    private static class ViewHolder{
        TextView textViewTerminalName;
        TextView getTextViewTerminalCity;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Terminal terminal = getItem(position);
        ViewHolder viewHolder;

        final View result;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.terminal_row, parent, false);
            viewHolder.textViewTerminalName = convertView.findViewById(R.id.terminal_name);
            viewHolder.getTextViewTerminalCity = convertView.findViewById(R.id.terminal_city);

            result = convertView;
            convertView.setTag(viewHolder)  ;
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.textViewTerminalName.setText(terminal.getTerminalName());
        viewHolder.getTextViewTerminalCity.setText(terminal.getTerminalCity());
        return convertView;
    }
}
