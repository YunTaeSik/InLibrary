package com.example.sky87.inlibrary.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sky87.inlibrary.R;

/**
 * Created by sky87 on 2016-07-03.
 */
public class RouteTimeDialog extends Activity implements View.OnClickListener {
    private TimePicker start_time_picker;
    private TimePicker end_time_picker;
    private Button ok_btn;
    private Button cancel_btn;

    private String visite_position;
    private String action_position;
    private String action;
    private String timedate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_routetimedialog);
        setFinishOnTouchOutside(false);

        visite_position = getIntent().getStringExtra("visite_position");
        action_position = getIntent().getStringExtra("action_position");
        action = getIntent().getStringExtra("action");

        start_time_picker = (TimePicker) findViewById(R.id.start_time_picker);
        end_time_picker = (TimePicker) findViewById(R.id.end_time_picker);
        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
        start_time_picker.setIs24HourView(true);
        end_time_picker.setIs24HourView(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                if (start_time_picker.getCurrentHour() > end_time_picker.getCurrentHour()) {
                    Toast.makeText(getApplicationContext(), "종료 시간이 시작시간보다 늦어야합니다.", Toast.LENGTH_SHORT).show();
                } else if (start_time_picker.getCurrentHour() == end_time_picker.getCurrentHour()) {
                    if (start_time_picker.getCurrentMinute() >= end_time_picker.getCurrentMinute()) {
                        Toast.makeText(getApplicationContext(), "종료 시간이 시작시간보다 늦어야합니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        NextDialog();
                    }
                } else {
                    NextDialog();
                }

                break;
            case R.id.cancel_btn:
                finish();
                break;
        }
    }

    private void NextDialog() {
        timedate = "이용시간:" + String.valueOf(start_time_picker.getCurrentHour()) + "시" + String.valueOf(start_time_picker.getCurrentMinute()) + "분~" + String.valueOf(end_time_picker.getCurrentHour()) + "시"
                + String.valueOf(end_time_picker.getCurrentMinute()) + "분";
     //   Toast.makeText(getApplicationContext(), timedate, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), RoutePlaceDialog.class);
        intent.putExtra("visite_position", visite_position);
        intent.putExtra("action_position", action_position);
        intent.putExtra("action", action);
        intent.putExtra("timedate", timedate);
        startActivity(intent);
        finish();
    }
}
