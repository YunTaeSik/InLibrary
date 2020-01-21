package com.example.sky87.inlibrary.dialog;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sky87.inlibrary.R;
import com.example.sky87.inlibrary.db.DBManager;
import com.example.sky87.inlibrary.util.Contact;

/**
 * Created by sky87 on 2016-07-02.
 */
public class ActionDialog extends Activity implements View.OnClickListener {
    private Spinner action_spinner;
    private Spinner purpose_spinner;
    private Spinner route_spinner;
    private Button ok_btn;
    private Button cancel_btn;

    private DBManager dbManager;

    private String action;
    private String purpose;
    private String route;
    private String SumData;
    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_actiondialog);
        setFinishOnTouchOutside(false);

        position = getIntent().getStringExtra("position");

        action_spinner = (Spinner) findViewById(R.id.action_spinner);
        purpose_spinner = (Spinner) findViewById(R.id.purpose_spinner);
        route_spinner = (Spinner) findViewById(R.id.route_spinner);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);

        String[] actionitems = new String[]{"1층", "2층", "3층", "4층", "B1층"};
        ArrayAdapter actionadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, actionitems);
        actionadapter.setDropDownViewResource(R.layout.spinnerdown_item);
        action_spinner.setAdapter(actionadapter);

        String[] purposeitems = new String[]{"자료대출", "자료열람", "도서반납", "공부", "과제수행", "학술정보PC 이용", "기타"};
        ArrayAdapter purposeadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, purposeitems);
        purposeadapter.setDropDownViewResource(R.layout.spinnerdown_item);
        purpose_spinner.setAdapter(purposeadapter);

        String[] routeitems = new String[]{"계단", "엘리베이터", "층간이동 없음"};
        ArrayAdapter routeadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner_item, routeitems);
        routeadapter.setDropDownViewResource(R.layout.spinnerdown_item);
        route_spinner.setAdapter(routeadapter);

        dbManager = new DBManager(getApplicationContext(), "vist", null, 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                action = action_spinner.getSelectedItem().toString();
                purpose = purpose_spinner.getSelectedItem().toString();
                route = route_spinner.getSelectedItem().toString();

                SumData = "[활동리스트]" + "\n" +"활동층:" + action + "\n" +
                        "목적:" + purpose + "\n" +
                        "이동 수단:" + route;

                SQLiteDatabase db = dbManager.getWritableDatabase();
                db.execSQL("CREATE TABLE IF NOT EXISTS " + Contact.ACTION_LIST + position + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT not null, action TEXT not null);");

                dbManager.insert("INSERT INTO " + Contact.ACTION_LIST + position + " VALUES(null, '" + SumData + "','" + action + "');");
                // dbManager.insert("insert into " + Contact.ACTION_LIST + position + " values(null, '" + SumData + "' );");
                Toast.makeText(getApplicationContext(), position + SumData, Toast.LENGTH_SHORT).show();

                finish();
                break;
            case R.id.cancel_btn:
                finish();
                break;
        }

    }
}
