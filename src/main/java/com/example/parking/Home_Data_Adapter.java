package com.example.parking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Home_Data_Adapter extends BaseAdapter {

    private List<Data> lData;
    private Context D;

    public Home_Data_Adapter(List<Data> lData, Context d) {
        this.lData = lData;
        D = d;
    }

    @Override
    public int getCount() {
        return this.lData.size();
    }

    @Override
    public Object getItem(int position) {
        return this.lData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(this.D);
            convertView = inflater.inflate(R.layout.affichage_data, null);
        }
        Data current = (Data) getItem(position);

        TextView tv = convertView.findViewById(R.id.lt_data);
        tv.setText(current.getNom());
        return convertView;
    }
}
