package com.example.tidu.attendancemanager2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class listAdapter extends RecyclerView.Adapter<listAdapter.listViewHolder> {
    Context mContext;
    List<SubjectInfo> itemList;
    DatabaseHelper mdb;

    public listAdapter(Context mContext, List<SubjectInfo> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public listViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View v = inflater.inflate(R.layout.recycler_list,viewGroup,false);
        return new listViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull listViewHolder holder, final int i) {
        holder.sub.setText(itemList.get(i).getName());
        holder.mini.setText(itemList.get(i).getMinimum());
        holder.pres.setText(itemList.get(i).getPres());
        int pre = Integer.parseInt(itemList.get(i).getPres());
        int ab = Integer.parseInt(itemList.get(i).getAbs());
        final String total = Integer.toString(pre + ab);
        holder.tot.setText(total);
        holder.id.setText(Integer.toString(itemList.get(i).getId()));
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, detailsSubject.class);

                intent.putExtra("IName", itemList.get(i).getName());
                intent.putExtra("IMin", itemList.get(i).getMinimum());
                intent.putExtra("Ipres", itemList.get(i).getPres());
                intent.putExtra("ITot",total);
                intent.putExtra("Id",Integer.toString(itemList.get(i).getId()));
                mContext.startActivity(intent);
            }


        });
    }

    @Override
    public int getItemCount() {
        itemList.size();
        return itemList.size();
    }
    public void updateData(List<SubjectInfo> viewModels) {
        itemList.clear();
        itemList.addAll(viewModels);
        notifyDataSetChanged();
    }

    public class listViewHolder extends RecyclerView.ViewHolder{
        TextView id,sub,mini,pres,tot;
        RelativeLayout parentLayout;

        public listViewHolder(@NonNull View itemView) {
            super(itemView);
            sub = (TextView) itemView.findViewById(R.id.subject);
            mini = (TextView) itemView.findViewById(R.id.minAtten);
            pres = (TextView) itemView.findViewById(R.id.pres);
            tot = (TextView) itemView.findViewById(R.id.total);
            parentLayout=(RelativeLayout)itemView.findViewById(R.id.parent);
            id=(TextView) itemView.findViewById(R.id.id);}
    }
}
