package com.jagadish.tweebrjagadish.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.jagadish.tweebrjagadish.R;

import java.util.List;



public class Videos_Frag extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   View view;

   EditText text;
   VideoView text_videoview;
   Button browse_textvideo;

   Uri videouri;

    public Videos_Frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Videos_Frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Videos_Frag newInstance(String param1, String param2) {
        Videos_Frag fragment = new Videos_Frag();
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
            view= inflater.inflate(R.layout.fragment_videos_, container, false);

            text=(EditText)view.findViewById(R.id.video_texthint);
            text_videoview=(VideoView)view.findViewById(R.id.videos_videotext);
            browse_textvideo=(Button)view.findViewById(R.id.videos_browsetextvideo);



            text_videoview.setMediaController(new MediaController(getActivity()));


            browse_textvideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (text.getText().toString().isEmpty())
                    {
                        Snackbar.make(getActivity().getWindow().getDecorView().getRootView(),"Enter Text",Snackbar.LENGTH_LONG).show();
                    }else {


                        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);
                            startActivityForResult(takeVideoIntent, 100);
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(
                                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        22);
                            }
                        }



                    }
                }
            });


        }
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 22   && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 15);
            startActivityForResult(takeVideoIntent, 100);
        }else {
            Toast.makeText(getActivity(), "Persmission Denied", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == 100 && data != null) {//crop



              videouri=data.getData();
              text_videoview.setVisibility(View.VISIBLE);

              text_videoview.setVideoURI(videouri);
              text_videoview.start();



            }


        }

    }

    private void TrimVideofile(Uri data) {

        Intent trimVideoIntent = new Intent("com.android.camera.action.TRIM");

// The key for the extra has been discovered from com.android.gallery3d.app.PhotoPage.KEY_MEDIA_ITEM_PATH
        trimVideoIntent.putExtra("media-item-path",data.getPath());
        trimVideoIntent.setData(data);

// Check if the device can handle the Intent
        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(trimVideoIntent, 0);
        if (null != list && list.size() > 0) {
            startActivityForResult(trimVideoIntent,30); // Fires TrimVideo activity into being active
        }else {
            Toast.makeText(getActivity(), "not supported",Toast.LENGTH_SHORT).show();
        }
    }


    void WriteTextOnImageView(Context context,String text,int x,int y)
    {

        FrameLayout mFrame=new FrameLayout(context);
        TextView tv=new TextView(context);
        tv.setTextColor(getResources().getColor(R.color.colorAccent));

        if(x!=0 && y!=0)
        {
            FrameLayout.LayoutParams mParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            mFrame.setLayoutParams(mParams);
            mFrame.setPadding(x, y, 0, 0);
            tv.setLayoutParams(mParams);
            tv.setText(text);
            mFrame.addView(tv);
           // frameLayout.addView(mFrame);


        }else {

            Toast.makeText(context, "Wrong Co-ordinates", Toast.LENGTH_SHORT).show();
        }


    }

}
