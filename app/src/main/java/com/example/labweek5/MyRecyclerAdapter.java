package com.example.labweek5;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    public MyRecyclerAdapter() {
    }

    private List<Book> data = new ArrayList<>();

    public void setData(List<Book> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cards, parent, false); //CardView inflated as RecyclerView list item
        ViewHolder viewHolder = new ViewHolder(v);
        Log.d("week6App","onCreateViewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.author.setText("Author: " + data.get(position).getAuthor());
        holder.title.setText("Title: " + data.get(position).getTitle());
        holder.price.setText("Price: " + data.get(position).getPrice());
        holder.id.setText("ID: " + data.get(position).getId());
        holder.isbn.setText("ISBN: " + data.get(position).getIsbn());
        holder.description.setText("Description: " + data.get(position).getDescription());
        holder.position.setText("Position: " + (position));
        Log.i("lab6", "Title: " + data.get(position).getTitle());
        Log.d("week6App","onBindViewHolder");

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView author;
        public TextView id;
        public TextView isbn;
        public TextView price;
        public TextView description;
        public TextView position;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleCard);
            author = itemView.findViewById(R.id.authorCard);
            id = itemView.findViewById(R.id.idCard);
            isbn = itemView.findViewById(R.id.isbnCard);
            price = itemView.findViewById(R.id.priceCard);
            description = itemView.findViewById(R.id.descCard);
            position = itemView.findViewById(R.id.positionCard);
        }
    }
}

