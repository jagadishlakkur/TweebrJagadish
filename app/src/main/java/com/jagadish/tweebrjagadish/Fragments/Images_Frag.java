package com.jagadish.tweebrjagadish.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jagadish.tweebrjagadish.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropTransformation;
import jp.wasabeef.picasso.transformations.gpu.ContrastFilterTransformation;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link Images_Frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Images_Frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View view;
    Button browse_pic;
    ImageView pic;
    Button filter_contrast,filter_crop,filter_blur;
    LinearLayout linearLayout;
    Uri uri;


    public Images_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Images_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Images_Frag newInstance(String param1, String param2) {
        Images_Frag fragment = new Images_Frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view==null)
        {
            view=inflater.inflate(R.layout.fragment_images_, container, false);

            browse_pic=(Button)view.findViewById(R.id.browseimg);
            pic=(ImageView)view.findViewById(R.id.pic);

            filter_contrast=(Button) view.findViewById(R.id.filter_contrast);
            filter_blur=(Button) view.findViewById(R.id.filter_blur);
            filter_crop=(Button) view.findViewById(R.id.filter_crop);
            linearLayout=(LinearLayout)view.findViewById(R.id.linealayout);



            browse_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 21);
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(
                                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    22);
                        }
                    }

                }
            });





            filter_contrast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Transformation trans1 = new ContrastFilterTransformation(getActivity(), 1.5f);
                    Picasso.with(getActivity()).load(uri)
                            .transform(trans1)
                            .into(pic);
                }
            });

            filter_blur.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Transformation trans1 = new BlurTransformation(getActivity(), 1);
                    Picasso.with(getActivity()).load(uri)
                            .transform(trans1)
                            .into(pic);
                }
            });

            filter_crop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Transformation trans1 = new CropTransformation(200,200);
                    Picasso.with(getActivity()).load(uri)
                            .transform(trans1)
                            .into(pic);
                }
            });


        }
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 22   && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 21);
        }else {
            Toast.makeText(getActivity(), "Persmission Denied", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 21 && data != null) {



                linearLayout.setVisibility(View.VISIBLE);
                pic.setImageURI(data.getData());
                uri=data.getData();


            }


        }

    }



}
