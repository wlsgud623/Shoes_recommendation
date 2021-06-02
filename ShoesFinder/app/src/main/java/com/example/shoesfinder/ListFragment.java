package com.example.shoesfinder;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class ListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 2;
    View filter, empty;
    Spinner color, brand, price;
    GridView gridView;
    ArrayAdapter ColorAdapter, PriceAdapter, BrandAdapter;
    String selectedbrand, selectedprice, selectedcolor;
    FirebaseStorage fs;
    FirebaseFirestore db;
    CollectionReference cf;
    StorageReference storageReference;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListFragment() {

    }

    // TODO: Customize parameter initialization
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    public void setList(String brand, String price, String color, ListAdapter listAdapter, CollectionReference cf){
        listAdapter.empty();
        if (brand == null){
            if (price == null){
                if(color == null){
                    cf.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            listAdapter.empty();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                listAdapter.addItem(new shoes(document.getId(), document.get("name").toString(), document.get("brand").toString(), document.get("image").toString(), Integer.parseInt(document.get("price").toString())));
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                else{
                    cf.whereArrayContains("color", color).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            listAdapter.empty();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                listAdapter.addItem(new shoes(document.getId(), document.get("name").toString(), document.get("brand").toString(), document.get("image").toString(), Integer.parseInt(document.get("price").toString())));
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
            else{
                if(color == null){
                    cf.whereEqualTo("price", Integer.parseInt(price)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            listAdapter.empty();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                listAdapter.addItem(new shoes(document.getId(), document.get("name").toString(), document.get("brand").toString(), document.get("image").toString(), Integer.parseInt(document.get("price").toString())));
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                else{
                    cf.whereArrayContains("color", color).whereEqualTo("price", Integer.parseInt(price)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            listAdapter.empty();
                            for(QueryDocumentSnapshot document : task.getResult()){
                                listAdapter.addItem(new shoes(document.getId(), document.get("name").toString(), document.get("brand").toString(), document.get("image").toString(), Integer.parseInt(document.get("price").toString())));
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        }
        else{
            if (price == null){
                if(color == null){
                    cf.whereEqualTo("brand", brand).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                listAdapter.addItem(new shoes(document.getId(), document.get("name").toString(), document.get("brand").toString(), document.get("image").toString(), Integer.parseInt(document.get("price").toString())));
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                else{
                    cf.whereArrayContains("color", color).whereEqualTo("brand", brand).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                listAdapter.addItem(new shoes(document.getId(), document.get("name").toString(), document.get("brand").toString(), document.get("image").toString(), Integer.parseInt(document.get("price").toString())));
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
            else{
                if(color == null){
                    cf.whereEqualTo("price", Integer.parseInt(price)).whereEqualTo("brand", brand).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                listAdapter.addItem(new shoes(document.getId(), document.get("name").toString(), document.get("brand").toString(), document.get("image").toString(), Integer.parseInt(document.get("price").toString())));
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
                else{
                    cf.whereEqualTo("price", Integer.parseInt(price)).whereArrayContains("color", color).whereEqualTo("brand", brand).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            for(QueryDocumentSnapshot document : task.getResult()){
                                listAdapter.addItem(new shoes(document.getId(), document.get("name").toString(), document.get("brand").toString(), document.get("image").toString(), Integer.parseInt(document.get("price").toString())));
                                listAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        filter = view.findViewById(R.id.filter);

        selectedbrand = null;
        selectedcolor = null;
        selectedprice = null;

        color = (Spinner) filter.findViewById(R.id.select_color);
        brand = (Spinner) filter.findViewById(R.id.select_brand);
        price = (Spinner) filter.findViewById(R.id.select_cost);

        gridView = (GridView) view.findViewById(R.id.list);
        ListAdapter listAdapter = new ListAdapter(getActivity());
        gridView.setAdapter(listAdapter);
        empty = view.findViewById(R.id.empty_view) ;
        gridView.setEmptyView(empty);

        ColorAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.color, R.layout.support_simple_spinner_dropdown_item);
        BrandAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.brand, R.layout.support_simple_spinner_dropdown_item);
        PriceAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.cost, R.layout.support_simple_spinner_dropdown_item);

        color.setAdapter(ColorAdapter);
        brand.setAdapter(BrandAdapter);
        price.setAdapter(PriceAdapter);

        db = FirebaseFirestore.getInstance();
        cf = db.collection("shose");
        cf.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        Log.d("check", "shoese" + document.get("name"));
                        listAdapter.addItem(new shoes(document.getId(), document.get("name").toString(), document.get("brand").toString(), document.get("image").toString(), Integer.parseInt(document.get("price").toString())));
                        listAdapter.notifyDataSetChanged();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Network DB Error!",Toast.LENGTH_SHORT).show();
                }
            }
        });

       color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if (i == 0){
                        selectedcolor = null;
                        setList(selectedbrand, selectedprice, selectedcolor, listAdapter, cf);
               }
               else {
                        selectedcolor = color.getItemAtPosition(i).toString();
                        setList(selectedbrand, selectedprice, selectedcolor, listAdapter, cf);
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {
                selectedcolor = null;
           }
       });

       brand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if(i==0){
                    selectedbrand = null;
                    setList(selectedbrand, selectedprice, selectedcolor, listAdapter, cf);
               }
               else {
                    selectedbrand = brand.getItemAtPosition(i).toString();
                    setList(selectedbrand, selectedprice, selectedcolor, listAdapter, cf);
               }
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {
                selectedbrand = null;
           }
       });
       price.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if(i==0){
                   selectedprice = null;
                   setList(selectedbrand, selectedprice, selectedcolor, listAdapter, cf);
               }
               else{
                   selectedprice = Integer.toString(i);
                   setList(selectedbrand, selectedprice, selectedcolor, listAdapter, cf);
               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {
                selectedprice = null;
           }
       });

        return view;
    }

}