package com.example.shoesfinder;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.AlertDialog;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CameraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CameraFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int Image_Capture_Code = 1;
    private static final int Gallery_Code = 2;

    View view;

    Button cam;
    Button gal;


    public CameraFragment() {
        // Required empty public constructor
    }

    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Image_Capture_Code) {
            if (resultCode == Activity.RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bp.compress(Bitmap.CompressFormat.JPEG,80,stream);
                byte[] Bytearray = stream.toByteArray();
                Intent intent = new Intent(getActivity(), PhotoActivity.class);
                intent.putExtra("PhotoforSearch",Bytearray);
                startActivity(intent);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(),"File   Error", Toast.LENGTH_LONG).show();
            }

        }
        else if (requestCode == Gallery_Code) {
            if (resultCode == Activity.RESULT_OK && data.getData() != null )  {
                Uri photo = data.getData();
                try {
                    Bitmap bp = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photo);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bp.compress(Bitmap.CompressFormat.JPEG,50,stream);
                    byte[] Bytearray = stream.toByteArray();
                    Intent intent = new Intent(getActivity(), PhotoActivity.class);
                    intent.putExtra("PhotoforSearch",Bytearray);
                    startActivity(intent);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getActivity(),"File Error", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_camera, container, false);

        cam = (Button) view.findViewById(R.id.Cambutton);
        gal = (Button) view.findViewById(R.id.Galbutton);

        gal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
                //PackageManager packageManager = getActivity().getPackageManager();
                /*
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, Gallery_Code);
                 */
                Intent intent = new Intent(getActivity(), GallerySpalsh.class);
                startActivity(intent);
            }
        });

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder cameraAlert = new AlertDialog.Builder(getActivity());
                cameraAlert.setTitle("경고");
                cameraAlert.setMessage("당사자의 동의 없는 촬영으로 벌어질 수 있는 모든 문제에 대해 본 앱은 어떤 종류의 법적 책임도 지지 않습니다.");
                AlertDialog alertDialog = cameraAlert.create();
                alertDialog.show();
                /*
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA},1);
                PackageManager packageManager = getActivity().getPackageManager();
                if(packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false) {
                    Toast.makeText(getActivity(), "카메라에 연결할수 없습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, Image_Capture_Code);
                 */
                Intent intent = new Intent(getActivity(), CameraSplash.class);
                startActivity(intent);
            }
        });
        return view;
    }

}