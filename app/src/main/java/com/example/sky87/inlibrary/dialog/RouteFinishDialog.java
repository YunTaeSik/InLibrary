package com.example.sky87.inlibrary.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.sky87.inlibrary.R;
import com.example.sky87.inlibrary.drawble.MyView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by YunTaeSik on 2016-07-30.
 */
public class RouteFinishDialog extends Activity implements View.OnClickListener {
    private String visite_position;
    private String action_position;
    private String action;

    private LinearLayout drawable_layout;
    private Button ok_btn;
    private Button cancel_btn;

    private String fodername;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFinishOnTouchOutside(false);
        setContentView(R.layout.activity_route_finish_dialog);

        visite_position = getIntent().getStringExtra("visite_position");
        action_position = getIntent().getStringExtra("action_position");
        action = getIntent().getStringExtra("action");
        drawable_layout = (LinearLayout) findViewById(R.id.drawable_layout);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        fodername = makeDir("InLibrary");

        switch (action) {
            case "1층":
                drawable_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_one_image));
                break;
            case "2층":
                drawable_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_two_image));
                break;
            case "3층":
                drawable_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_three_image));
                break;
            case "4층":
                drawable_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_four_image));
                break;
            case "B1층":
                drawable_layout.setBackgroundDrawable(getResources().getDrawable(R.drawable.action_bone_image));
                break;
        }
        MyView myView = new MyView(this);
        drawable_layout.addView(myView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                try {
                    screenshot(drawable_layout);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setResult(1);
                finish();
                break;
            case R.id.cancel_btn:
                finish();
                break;
        }
    }

    private static String makeDir(String dirName) {
        String mRootPath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + dirName;
        File fRoot = new File(mRootPath);
        if (!fRoot.exists()) {
            fRoot.mkdir();
        }
        return mRootPath + "/";
    }

    public void screenshot(View view) throws Exception {

        view.setDrawingCacheEnabled(true);

        Bitmap screenshot = view.getDrawingCache();

        String filename = visite_position + action_position + ".png";

        try {
            FileOutputStream fos = new FileOutputStream(fodername + filename);
            screenshot.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
            /*sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://" + Environment.getExternalStorageDirectory())));*/
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fodername + filename)));

        } catch (IOException e) {
            e.printStackTrace();
        }
        view.setDrawingCacheEnabled(false);
    }
}
