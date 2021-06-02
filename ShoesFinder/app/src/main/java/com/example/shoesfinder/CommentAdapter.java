package com.example.shoesfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends BaseAdapter {

    Context context;
    ArrayList<comment> list;
    LayoutInflater inflater;

    CommentAdapter(Context context){
        this.context = context;
        this.list = new ArrayList<comment>();
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public void additem(String s, int n){
        list.add(new comment(s, n));
    }

    public void additem(List<String> s){
        for (int i=0;i<s.size(); i++){
            list.add(new comment(s.get(i), i));
        }
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
        CommentViewHolder viewHolder;
        if(view == null){
            view = inflater.inflate(R.layout.comment_each, viewGroup, false);
            viewHolder = new CommentViewHolder();
            viewHolder.CommentNumber = view.findViewById(R.id.commentnumber);
            viewHolder.CommentText = view.findViewById(R.id.commenttext);
            viewHolder.layout = view.findViewById(R.id.comment_list);
            view.setTag(viewHolder);
        }
        else{
            viewHolder = (CommentViewHolder) view.getTag();
        }
        comment com = list.get(i);
        String text = com.getComm();
        int index = com.getNumber();

        viewHolder.CommentText.setText(text);
        viewHolder.CommentNumber.setText(Integer.toString(index+1));

        return view;
    }

    public void empty(){
        list = new ArrayList<comment>();
    }

    public class CommentViewHolder{
        public TextView CommentNumber, CommentText;
        public LinearLayout layout;
    }
}
