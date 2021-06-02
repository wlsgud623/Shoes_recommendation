package com.example.shoesfinder;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private View userView, userlog, mylog;
    private View fs, ss, ts;
    private View sfs, sss, sts;
    private TextView idView;
    private  TextView fisrt_name, second_name, third_name;
    private  TextView fisrt_price, second_price, third_price;
    private  ImageView fisrt_image, second_image, third_image;

    private  TextView sfisrt_name, ssecond_name, sthird_name;
    private  TextView sfisrt_price, ssecond_price, sthird_price;
    private  ImageView sfisrt_image, ssecond_image, sthird_image;
    private View profileView;
    FirebaseUser currentUser;

    FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home, container, false);
        userView = v.findViewById(R.id.CurrentUser);
        db = FirebaseFirestore.getInstance();
        idView = (TextView) userView.findViewById(R.id.profile_name);
        profileView = (View) userView.findViewById(R.id.main_back);


        try {
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
            String uid = currentUser.getEmail();
            MainActivity.id = uid;
            idView.setText(uid);
        }
        catch (NullPointerException e){
            idView.setText("Noname");
            MainActivity.id = "Noname";
        }
        Log.d("id", MainActivity.id);
        userlog = (View) v.findViewById(R.id.userlog);
        mylog = (View) v.findViewById(R.id.mylog) ;

        String[] user_vistied = new String[3];
        String[] my_vistied = new String[3];

        sfs = (View) userlog.findViewById(R.id.first_Shoes);
        sss = (View) userlog.findViewById(R.id.second_Shoes);
        sts = (View) userlog.findViewById(R.id.third_Shoes);

        fs = (View) mylog.findViewById(R.id.first_Shoes);
        ss = (View) mylog.findViewById(R.id.second_Shoes);
        ts = (View) mylog.findViewById(R.id.third_Shoes);

        fisrt_name = (TextView) fs.findViewById(R.id.Shoes_Name);
        second_name = (TextView) ss.findViewById(R.id.Shoes_Name);
        third_name = (TextView) ts.findViewById(R.id.Shoes_Name);

        fisrt_price = (TextView) fs.findViewById(R.id.Shows_Price);
        second_price = (TextView) ss.findViewById(R.id.Shows_Price);
        third_price = (TextView) ts.findViewById(R.id.Shows_Price);

        fisrt_image = (ImageView) fs.findViewById(R.id.Shoes_Photo);
        second_image = (ImageView) ss.findViewById(R.id.Shoes_Photo);
        third_image = (ImageView) ts.findViewById(R.id.Shoes_Photo);

        sfisrt_name = (TextView) sfs.findViewById(R.id.Shoes_Name);
        ssecond_name = (TextView) sss.findViewById(R.id.Shoes_Name);
        sthird_name = (TextView) sts.findViewById(R.id.Shoes_Name);

        sfisrt_price = (TextView) sfs.findViewById(R.id.Shows_Price);
        ssecond_price = (TextView) sss.findViewById(R.id.Shows_Price);
        sthird_price = (TextView) sts.findViewById(R.id.Shows_Price);

        sfisrt_image = (ImageView) sfs.findViewById(R.id.Shoes_Photo);
        ssecond_image = (ImageView) sss.findViewById(R.id.Shoes_Photo);
        sthird_image = (ImageView) sts.findViewById(R.id.Shoes_Photo);





            db.collection("user").document(MainActivity.id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot documentSnapshot = task.getResult();
                        List<String> visit = (List<String>) documentSnapshot.get("visited");
                        if (visit!= null && visit.size() == 1) {
                            db.collection("shose").document(visit.get(0)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot doc = task.getResult();
                                    fisrt_name.setText(doc.get("name").toString());
                                    fisrt_price.setText(doc.get("price").toString() + " 만원");
                                    Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(fisrt_image);
                                }
                            });
                        } else if (visit!= null && visit.size() == 2) {
                            db.collection("shose").document(visit.get(1)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot doc = task.getResult();
                                    fisrt_name.setText(doc.get("name").toString());
                                    fisrt_price.setText(doc.get("price").toString() + " 만원");
                                    Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(fisrt_image);
                                }
                            });
                            db.collection("shose").document(visit.get(0)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot doc = task.getResult();
                                    second_name.setText(doc.get("name").toString());
                                    second_price.setText(doc.get("price").toString() + " 만원");
                                    Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(second_image);
                                }
                            });
                        } else if (visit!= null && visit.size() >= 3) {
                            db.collection("shose").document(visit.get(visit.size()-1)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot doc = task.getResult();
                                    fisrt_name.setText(doc.get("name").toString());
                                    fisrt_price.setText(doc.get("price").toString() + " 만원");
                                    Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(fisrt_image);
                                }
                            });
                            db.collection("shose").document(visit.get(visit.size()-2)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot doc = task.getResult();
                                    second_name.setText(doc.get("name").toString());
                                    second_price.setText(doc.get("price").toString() + " 만원");
                                    Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(second_image);
                                }
                            });
                            db.collection("shose").document(visit.get(visit.size()-3)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot doc = task.getResult();
                                    third_name.setText(doc.get("name").toString());
                                    third_price.setText(doc.get("price").toString() + " 만원");
                                    Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(third_image);
                                }
                            });
                        }
                    } else {
                        Map<String, Object> log = new HashMap<>();
                        String [] arr = new String[0];
                        log.put("visited", arr);
                        db.collection("user").document(MainActivity.id).set(log).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "home fragment success");
                            }
                        });
                    }
                }});



        db.collection("user").document("All").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                List<String> visit = (List<String>) documentSnapshot.get("visited");
                if (visit!= null && visit.size() == 1) {
                    db.collection("shose").document(visit.get(0)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc = task.getResult();
                            sfisrt_name.setText(doc.get("name").toString());
                            sfisrt_price.setText(doc.get("price").toString() + " 만원");
                            Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(sfisrt_image);
                        }
                    });
                } else if (visit!= null && visit.size() == 2) {
                    db.collection("shose").document(visit.get(1)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc = task.getResult();
                            sfisrt_name.setText(doc.get("name").toString());
                            sfisrt_price.setText(doc.get("price").toString() + " 만원");
                            Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(sfisrt_image);
                        }
                    });
                    db.collection("shose").document(visit.get(0)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc = task.getResult();
                            ssecond_name.setText(doc.get("name").toString());
                            ssecond_price.setText(doc.get("price").toString() + " 만원");
                            Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(ssecond_image);
                        }
                    });
                } else if (visit!= null && visit.size() >= 3) {
                    db.collection("shose").document(visit.get(visit.size()-1)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc = task.getResult();
                            sfisrt_name.setText(doc.get("name").toString());
                            sfisrt_price.setText(doc.get("price").toString() + " 만원");
                            Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(sfisrt_image);
                        }
                    });
                    db.collection("shose").document(visit.get(visit.size()-2)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc = task.getResult();
                            ssecond_name.setText(doc.get("name").toString());
                            ssecond_price.setText(doc.get("price").toString() + " 만원");
                            Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(ssecond_image);
                        }
                    });
                    db.collection("shose").document(visit.get(visit.size()-3)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            DocumentSnapshot doc = task.getResult();
                            sthird_name.setText(doc.get("name").toString());
                            sthird_price.setText(doc.get("price").toString() + " 만원");
                            Glide.with(getActivity()).load(doc.get("image").toString()).override(160, 200).into(sthird_image);
                        }
                    });
                }
            }});


        return v;

    }
}