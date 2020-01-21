package com.example.sky87.inlibrary.dialog;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sky87.inlibrary.R;
import com.example.sky87.inlibrary.util.Contact;
import com.example.sky87.inlibrary.util.SharedPrefsUtils;

/**
 * Created by sky87 on 2016-07-03.
 */
public class UserDialog extends Activity implements View.OnClickListener {
    private Button ok_btn;
    private EditText user_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_userdialog);
        setFinishOnTouchOutside(false);

        ok_btn = (Button) findViewById(R.id.ok_btn);
        user_edit = (EditText) findViewById(R.id.user_edit);
        ok_btn.setOnClickListener(this);
        user_edit.setSelection(user_edit.getText().length());
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                if (user_edit.getText().toString().length() > 0) {
                    SharedPrefsUtils.setStringPreference(getApplicationContext(), Contact.USER, user_edit.getText().toString());
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
