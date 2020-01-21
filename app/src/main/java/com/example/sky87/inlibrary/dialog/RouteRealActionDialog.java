package com.example.sky87.inlibrary.dialog;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sky87.inlibrary.R;
import com.example.sky87.inlibrary.db.DBManager;
import com.example.sky87.inlibrary.util.Contact;

/**
 * Created by sky87 on 2016-07-03.
 */
public class RouteRealActionDialog extends Activity implements View.OnClickListener {
    private Button ok_btn;
    private Button cancel_btn;
    private EditText purpose_edit;

    private String visite_position;
    private String action_position;
    private String timedate;
    private String place;
    private String purpose;
    private String SumData;
    private String action;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_routerealactiondialog);
        setFinishOnTouchOutside(false);

        visite_position = getIntent().getStringExtra("visite_position");
        action_position = getIntent().getStringExtra("action_position");
        timedate = getIntent().getStringExtra("timedate");
        place = getIntent().getStringExtra("place");
        action = getIntent().getStringExtra("action");

        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        purpose_edit = (EditText) findViewById(R.id.purpose_edit);
        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        dbManager = new DBManager(getApplicationContext(), "vist", null, 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                if (purpose_edit.getText().toString().length() > 0) {
                    purpose = purpose_edit.getText().toString();
                    SumData = "[경로리스트]" + "\n" + "활동층:" + action + "\n" + timedate + "\n" + "장소:" + place.replace("\n", "") + "\n" + "목적:" + purpose;
                   // Toast.makeText(getApplicationContext(), SumData, Toast.LENGTH_SHORT).show();
                    SQLiteDatabase db = dbManager.getWritableDatabase();
                    db.execSQL("CREATE TABLE IF NOT EXISTS " + Contact.ROUTE_LIST + visite_position + action_position + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT not null);");
                    dbManager.insert("insert into " + Contact.ROUTE_LIST + visite_position + action_position + " values(null, '" + SumData + "' );");
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "목적을 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.cancel_btn:
                finish();
                break;
        }
    }
}
