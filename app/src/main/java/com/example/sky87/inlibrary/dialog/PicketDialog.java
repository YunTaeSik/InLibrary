package com.example.sky87.inlibrary.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sky87.inlibrary.R;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by sky87 on 2016-06-24.
 */
public class PicketDialog extends Activity implements View.OnClickListener {
    private GregorianCalendar mCalendar;
    private TimePicker mTime;
    private DatePicker data_picker;
    private Button ok_btn;
    private Button cancel_btn;
    private String vistData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_picketdialog);
        setFinishOnTouchOutside(false);

        ok_btn = (Button) findViewById(R.id.ok_btn);
        cancel_btn = (Button) findViewById(R.id.cancel_btn);
        ok_btn.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
        mCalendar = new GregorianCalendar();
        mTime = (TimePicker) findViewById(R.id.time_picker);
        data_picker = (DatePicker) findViewById(R.id.data_picker);
        mTime.setCurrentHour(mCalendar.get(Calendar.HOUR_OF_DAY));
        mTime.setCurrentMinute(mCalendar.get(Calendar.MINUTE));
        mTime.setIs24HourView(true);
        data_picker.setCalendarViewShown(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok_btn:
                vistData = "도착시간:"
                        + String.valueOf(data_picker.getYear()) + "년"
                        + String.valueOf(data_picker.getMonth() + 1) + "월"
                        + String.valueOf(data_picker.getDayOfMonth()) + "일"
                        + String.valueOf(mTime.getCurrentHour()) + "시"
                        + String.valueOf(mTime.getCurrentMinute()) + "분"
                        + "\n";
                ;
              //  Toast.makeText(getApplicationContext(), vistData, Toast.LENGTH_SHORT).show();
                Intent purpose = new Intent(getApplication(), PurposeDialog.class);
                purpose.putExtra("vistData", vistData);
                startActivity(purpose);
                finish();
                break;
            case R.id.cancel_btn:
                finish();
                Toast.makeText(getApplicationContext(), "취소되었습니다.", Toast.LENGTH_SHORT
                ).show();
                break;
        }

    }
}
