package com.example.shoesfinder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends BaseAdapter {
    Context context;
    ArrayList<shoes> list;
    LayoutInflater inflater;
    String id;

    public ListAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<shoes>();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void addItem(shoes shoe){
        list.add(shoe);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ShoesViewHolder viewHolder;

        if(view == null){
            view = inflater.inflate(R.layout.one_shoes_icon, viewGroup, false);
            viewHolder = new ShoesViewHolder();
            viewHolder.shoes_image = view.findViewById(R.id.Shoes_Photo);
            viewHolder.shoes_name = view.findViewById(R.id.Shoes_Name);
            viewHolder.shoese_price = view.findViewById(R.id.Shows_Price);
            viewHolder.layout = view.findViewById(R.id.icon_layout);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ShoesViewHolder) view.getTag();
        }

        if(list.isEmpty()){
            return null;
        }
        shoes shoe = list.get(i);
        String name =  shoe.getName();
        int price = shoe.getPrice();
        String url = shoe.getUrl();
        String doc = shoe.getDocument();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EachActivity.class);
                intent.putExtra("document", doc);
                context.startActivity(intent);
            }
        });
        viewHolder.shoes_name.setText(name);
        viewHolder.shoese_price.setText(Integer.toString(price) + " 만원");
        Glide.with(context).load(url).override(120,120).into(viewHolder.shoes_image);

        return view;
    }
    public void empty(){
        list = new ArrayList<shoes>();
    }

    public class ShoesViewHolder{
        public ImageView shoes_image;
        public TextView shoes_name, shoese_price;
        public LinearLayout layout;
    }


}
