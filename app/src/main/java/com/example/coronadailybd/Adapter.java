package com.example.coronadailybd;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.UserViewHolder> {

    private Context context;
    List<Data> userList;
    // List<Data> filteredUserdataList;


    public Adapter(Context context, List<Data> userList) {
        this.context = context;
        this.userList = userList;


        //this.filteredUserdataList = userList;


    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        Data data = userList.get(position);
        String f = data.getDate();
        f = f.replaceAll("T.*", "");



        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(f);
            SimpleDateFormat sdfnewformat = new SimpleDateFormat("dd MMM yyyy");
            String finalDateString = sdfnewformat.format(convertedDate);
            holder.da.setText(finalDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.con.setText(data.getConfirmed().toString());
        holder.deat.setText(data.getDeaths().toString());
        holder.rec.setText(data.getRecovered().toString());
        holder.ac.setText(data.getActive().toString());


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView da, con, deat, rec, ac;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            da = itemView.findViewById(R.id.textView);
            con = itemView.findViewById(R.id.textView11);
            deat = itemView.findViewById(R.id.textView12);
            rec = itemView.findViewById(R.id.textView13);
            ac = itemView.findViewById(R.id.textView14);
        }
    }

   /* public Filter getFilter(){

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String Key = charSequence.toString();
                if (Key.isEmpty()){
                    filteredUserdataList = userList;
                }
                else {

                    List<Data> lstFiltered = new ArrayList<>();
                    for (Data row: userList){



                        String f = row.getDate();
                        f = f.replaceAll("T.*", "");




                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date convertedDate = new Date();
                        try {
                            convertedDate = dateFormat.parse(f);
                            SimpleDateFormat sdfnewformat = new SimpleDateFormat("MMM");
                            String finalDateString = sdfnewformat.format(convertedDate);
                            if(finalDateString.toLowerCase().contains(Key.toLowerCase())){
                                *//*lstFiltered.clear();
                                lstFiltered.add(row);*//*
                                //Toast.makeText(Adapter.this,, Toast.LENGTH_LONG).show();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    }

                    filteredUserdataList = lstFiltered;

                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserdataList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {

                filteredUserdataList = (List<Data>) results.values;
                notifyDataSetChanged();
            }
        };*/

//}
}
