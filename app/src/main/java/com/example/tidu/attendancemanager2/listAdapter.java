package com.example.tidu.attendancemanager2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class listAdapter extends RecyclerView.Adapter<listAdapter.listViewHolder> {
    Context mContext;
    List<SubjectInfo> itemList;

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
    public void onBindViewHolder(@NonNull listViewHolder holder, int i) {
        holder.sub.setText(itemList.get(i).getName());
        holder.mini.setText(itemList.get(i).getMinimum());
        holder.pres.setText(itemList.get(i).getPres());
        String nn = itemList.get(i).getName();
        int pre = Integer.parseInt(itemList.get(i).getPres());
        int ab = Integer.parseInt(itemList.get(i).getAbs());
        int total = pre+ab;
        holder.tot.setText(total+"");
    }

    @Override
    public int getItemCount() {
        int h= itemList.size();
        return itemList.size();
    }
    public void updateData(List<SubjectInfo> viewModels) {
        itemList.clear();
        itemList.addAll(viewModels);
        notifyDataSetChanged();
    }

    public class listViewHolder extends RecyclerView.ViewHolder{
        TextView sub,mini,pres,tot;

        public listViewHolder(@NonNull View itemView) {
            super(itemView);
            sub = (TextView) itemView.findViewById(R.id.subject);
            mini = (TextView) itemView.findViewById(R.id.minAtten);
            pres = (TextView) itemView.findViewById(R.id.pres);
            tot = (TextView) itemView.findViewById(R.id.total);
        }
    }
}
