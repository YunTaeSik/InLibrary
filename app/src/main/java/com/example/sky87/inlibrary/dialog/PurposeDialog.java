package com.example.sky87.inlibrary.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sky87.inlibrary.R;
import com.example.sky87.inlibrary.db.DBManager;
import com.example.sky87.inlibrary.util.Contact;

/**
 * Created by sky87 on 2016-06-24.
 */
public class PurposeDialog extends Activity implements View.OnClickListener {
    private Button ok_btn;
    private Button cancel_btn;
    private EditText purpose_edit;
    private String purpose;
    private String route;
    private Spinner route_spinner;
    private String vistData;
    private String sumData;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_purposedialog);
        setFinishOnTouchOutside(false);

        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        purpose_edit = (EditText) findViewById(R.id.purpose_edit);
        route_spinner = (Spinner) findViewById(R.id.route_spinner);

        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        String[] items = new String[]{"B1층", "1층"};
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, items);
        adapter.setDropDownViewResource(R.layout.spinnerdown_item);

        route_spinner.setAdapter(adapter);

        Intent intent = getIntent();
        vistData = intent.getStringExtra("vistData");

        dbManager = new DBManager(getApplicationContext(), "vist", null, 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                if (purpose_edit.getText().toString().length() > 0) {
                    purpose = purpose_edit.getText().toString() + "\n";
                    route = route_spinner.getSelectedItem().toString();
                    finish();
                    sumData = "[방문리스트]" + "\n" + vistData + "목적:" + purpose + "입장 경로:" + route;
                    dbManager.insert("insert into " + Contact.VIST_LIST + " values(null, '" + sumData + "' );");
                   /* Intent intent = new Intent("NOTIFICATION");
                    getApplicationContext().sendBroadcast(intent);*/
                } else {
                    Toast.makeText(getApplicationContext(), "목적을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel_btn:
                finish();
                Toast.makeText(getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}


