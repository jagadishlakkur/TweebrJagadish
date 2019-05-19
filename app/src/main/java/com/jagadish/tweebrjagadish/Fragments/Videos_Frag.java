package com.jagadish.tweebrjagadish.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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

import com.github.hiteshsondhi88.libffmpeg.ExecuteBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.FFmpeg;
import com.github.hiteshsondhi88.libffmpeg.LoadBinaryResponseHandler;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegCommandAlreadyRunningException;
import com.github.hiteshsondhi88.libffmpeg.exceptions.FFmpegNotSupportedException;
import com.jagadish.tweebrjagadish.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.picasso.transformations.internal.Utils;

import static android.media.tv.TvTrackInfo.TYPE_VIDEO;


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
   ProgressDialog progressDialog;
    FFmpeg ffmpeg;
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

            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading..");


             ffmpeg = FFmpeg.getInstance(getActivity());
            try
            {
                ffmpeg.loadBinary(new LoadBinaryResponseHandler() {

                    @Override
                    public void onStart() {}

                    @Override
                    public void onFailure() {}

                    @Override
                    public void onSuccess() {}

                    @Override
                    public void onFinish() {}
                });
            } catch (FFmpegNotSupportedException e) {
                // Handle if FFmpeg is not supported by device
                Log.d("ResponseOutputBinEx",e.getMessage().toString());
            }

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
             /* text_videoview.setVisibility(View.VISIBLE);

              text_videoview.setVideoURI(videouri);
              text_videoview.start();*/

             embedTextOnVideo(text.getText().toString(),videouri.getPath(),16);



            }


        }

    }


    public void embedTextOnVideo(String text, String path, int fontSize)
    {

        final File out = getOutputFile(TYPE_VIDEO);

        String[] cmd = new String[] {
                "-y", "-i", path, "-vf", "drawtext=text='helloworld':fontfile=/storage/emulated/0/text.ttf: fontcolor=white: fontsize=24: x=(w-tw)/2: y=(h/PHI)+th box=0:", out.getAbsolutePath()
        };

        try {




            ffmpeg.execute(cmd, new ExecuteBinaryResponseHandler() {

                @Override
                public void onStart() {

                    progressDialog.show();
                }


                @Override
                public void onProgress(String message) {}

                @Override
                public void onFailure(String message) {
                    progressDialog.dismiss();
                    Log.d("ResponseOutputFailure",message);
                }

                @Override
                public void onSuccess(String message) {
                    progressDialog.dismiss();
                    Log.d("ResponseOutputSuces",message);


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            text_videoview.setVisibility(View.VISIBLE);

                            text_videoview.setVideoPath(out.getPath());
                            text_videoview.start();
                        }
                    });
                }

                @Override
                public void onFinish() {
                    progressDialog.dismiss();
                    Log.d("ResponseOutput","finish");
                }
            });
        } catch (FFmpegCommandAlreadyRunningException e) {
            // Handle if FFmpeg is already running
            Log.d("ResponseOutputCmdEx",e.toString());
        }
    }

    @Nullable
    private Throwable writeDataToFile(byte[] data, File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
        } catch (FileNotFoundException e) {
            return e;
        } catch (IOException e) {
            return e;
        }

        return null;
    }



    @Nullable
    private File getOutputFile(int type) {
        File storageDir = Environment.getExternalStorageDirectory();

// Create storage dir if it does not exist
        if (!storageDir.exists()) {
            if (!storageDir.mkdirs()) {
                Log.e("ResponseOutput", "Failed to create directory:" + storageDir.getAbsolutePath());
                return null;
            }
        }

// media file name
        String fileName = String.format("%s", new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));


        if (type == TYPE_VIDEO) {
            fileName = String.format("VID_%s.mp4", fileName);
        } else {
            Log.e("ResponseOutput", "Unsupported media type:" + type);
            return null;
        }

        return new File(String.format("%s%s%s", storageDir.getPath(), File.separator, fileName));
    }

}
