package com.example.sky87.inlibrary.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.sky87.inlibrary.R;
import com.example.sky87.inlibrary.util.Contact;
import com.example.sky87.inlibrary.util.SharedPrefsUtils;

/**
 * Created by YunTaeSik on 2016-09-06.
 */
public class SuveyCheckDialog extends Activity implements View.OnClickListener {
    private Button ok_btn;
    private Button cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_suvey_check_dialog);
        setFinishOnTouchOutside(false);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                SharedPrefsUtils.setStringPreference(getApplicationContext(), Contact.SURVEY, "ON");
                String url = "https://docs.google.com/forms/d/e/1FAIpQLSf__LOayMothUW3vmaaJJS8Es28JrZM7V3UcOFEzisn53LFJg/viewform";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                finish();
                break;
            case R.id.cancel_btn:
                finish();
                break;
        }

    }
}