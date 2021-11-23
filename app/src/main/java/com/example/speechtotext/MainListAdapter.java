package com.example.speechtotext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainListAdapter  extends RecyclerView.Adapter<MainListAdapter.MyViewHolder> {
    Context context;
    String data1[],data2[];
    int imgList[];
    public MainListAdapter(Context context,String s1[],String s2[],int imgList[]){
        this.context = context;
        this.data1 = s1;
        this.data2 = s2;
        this.imgList = imgList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.row_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtTitle.setText(data1[position]);
        holder.txtDescription.setText(data2[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtDescription;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtView_row_item_title);
            txtDescription = itemView.findViewById(R.id.txtView_row_item_desc);
            img = itemView.findViewById(R.id.imgView_row_item);
        }
    }
}
