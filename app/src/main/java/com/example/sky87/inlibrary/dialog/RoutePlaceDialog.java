package com.example.sky87.inlibrary.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.sky87.inlibrary.R;

/**
 * Created by sky87 on 2016-07-03.
 */
public class RoutePlaceDialog extends Activity implements View.OnClickListener {
    private String visite_position;
    private String action_position;
    private String action;
    private String timedate;
    private String place;
    private LinearLayout one_restroom;
    private LinearLayout one_happiness;
    private LinearLayout one_child_readingroom;
    private LinearLayout one_hat_readingroom;

    private LinearLayout two_multi;
    private LinearLayout two_disabled;
    private LinearLayout two_electronic;
    private LinearLayout two_multicultural;
    private LinearLayout two_computerroom;
    private LinearLayout two_workshop;
    private LinearLayout two_culturelove;
    private LinearLayout two_restroom;

    private LinearLayout three_reference_room;
    private LinearLayout three_breastfeeding;
    private LinearLayout three_lounge;
    private LinearLayout three_restroom;
    private LinearLayout three_staff;
    private LinearLayout three_creatroom;
    private LinearLayout three_infinite;

    private LinearLayout four_reference_room;
    private LinearLayout four_roof;
    private LinearLayout four_roof_two;
    private LinearLayout four_fitting;
    private LinearLayout four_restroom;
    private LinearLayout four_honor;
    private LinearLayout four_office;

    private LinearLayout bone_restaurant;
    private LinearLayout bone_warehouse;
    private LinearLayout bone_preservation;
    private LinearLayout bone_ready;
    private LinearLayout bone_restroom;
    private LinearLayout bone_versatile;
    private LinearLayout bone_adjustment;
    private LinearLayout bone_machine;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setFinishOnTouchOutside(false);

        visite_position = getIntent().getStringExtra("visite_position");
        action_position = getIntent().getStringExtra("action_position");
        action = getIntent().getStringExtra("action");
        timedate = getIntent().getStringExtra("timedate");

        if (action.equals("1층")) {
            setContentView(R.layout.activity_routepalcedialog_one);
            one_restroom = (LinearLayout) findViewById(R.id.one_restroom);
            one_happiness = (LinearLayout) findViewById(R.id.one_happiness);
            one_child_readingroom = (LinearLayout) findViewById(R.id.one_child_readingroom);
            one_hat_readingroom = (LinearLayout) findViewById(R.id.one_hat_readingroom);
            one_restroom.setOnClickListener(this);
            one_happiness.setOnClickListener(this);
            one_child_readingroom.setOnClickListener(this);
            one_hat_readingroom.setOnClickListener(this);
        } else if (action.equals("2층")) {
            setContentView(R.layout.activity_routepalcedialog_two);
            two_multi = (LinearLayout) findViewById(R.id.two_multi);
            two_disabled = (LinearLayout) findViewById(R.id.two_disabled);
            two_electronic = (LinearLayout) findViewById(R.id.two_electronic);
            two_multicultural = (LinearLayout) findViewById(R.id.two_multicultural);
            two_computerroom = (LinearLayout) findViewById(R.id.two_computerroom);
            two_workshop = (LinearLayout) findViewById(R.id.two_workshop);
            two_culturelove = (LinearLayout) findViewById(R.id.two_culturelove);
            two_restroom = (LinearLayout) findViewById(R.id.two_restroom);
            two_multi.setOnClickListener(this);
            two_disabled.setOnClickListener(this);
            two_electronic.setOnClickListener(this);
            two_multicultural.setOnClickListener(this);
            two_computerroom.setOnClickListener(this);
            two_workshop.setOnClickListener(this);
            two_culturelove.setOnClickListener(this);
            two_restroom.setOnClickListener(this);
        } else if (action.equals("3층")) {
            setContentView(R.layout.activity_routepalcedialog_three);
            three_reference_room = (LinearLayout) findViewById(R.id.three_reference_room);
            three_breastfeeding = (LinearLayout) findViewById(R.id.three_breastfeeding);
            three_lounge = (LinearLayout) findViewById(R.id.three_lounge);
            three_restroom = (LinearLayout) findViewById(R.id.three_restroom);
            three_staff = (LinearLayout) findViewById(R.id.three_staff);
            three_creatroom = (LinearLayout) findViewById(R.id.three_creatroom);
            three_infinite = (LinearLayout) findViewById(R.id.three_infinite);
            three_reference_room.setOnClickListener(this);
            three_breastfeeding.setOnClickListener(this);
            three_lounge.setOnClickListener(this);
            three_restroom.setOnClickListener(this);
            three_staff.setOnClickListener(this);
            three_creatroom.setOnClickListener(this);
            three_infinite.setOnClickListener(this);
        } else if (action.equals("4층")) {
            setContentView(R.layout.activity_routepalcedialog_four);
            four_reference_room = (LinearLayout) findViewById(R.id.four_reference_room);
            four_roof = (LinearLayout) findViewById(R.id.four_roof);
            four_roof_two = (LinearLayout) findViewById(R.id.four_roof_two);
            four_fitting = (LinearLayout) findViewById(R.id.four_fitting);
            four_restroom = (LinearLayout) findViewById(R.id.four_restroom);
            four_honor = (LinearLayout) findViewById(R.id.four_honor);
            four_office = (LinearLayout) findViewById(R.id.four_office);

            four_reference_room.setOnClickListener(this);
            four_roof.setOnClickListener(this);
            four_roof_two.setOnClickListener(this);
            four_fitting.setOnClickListener(this);
            four_restroom.setOnClickListener(this);
            four_honor.setOnClickListener(this);
            four_office.setOnClickListener(this);
        } else if (action.equals("B1층")) {
            setContentView(R.layout.activity_routepalcedialog_bone);
            bone_restaurant = (LinearLayout) findViewById(R.id.bone_restaurant);
            bone_warehouse = (LinearLayout) findViewById(R.id.bone_warehouse);
            bone_preservation = (LinearLayout) findViewById(R.id.bone_preservation);
            bone_ready = (LinearLayout) findViewById(R.id.bone_ready);
            bone_restroom = (LinearLayout) findViewById(R.id.bone_restroom);
            bone_versatile = (LinearLayout) findViewById(R.id.bone_versatile);
            bone_adjustment = (LinearLayout) findViewById(R.id.bone_adjustment);
            bone_machine = (LinearLayout) findViewById(R.id.bone_machine);

            bone_restaurant.setOnClickListener(this);
            bone_warehouse.setOnClickListener(this);
            bone_preservation.setOnClickListener(this);
            bone_ready.setOnClickListener(this);
            bone_restroom.setOnClickListener(this);
            bone_versatile.setOnClickListener(this);
            bone_adjustment.setOnClickListener(this);
            bone_machine.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.one_restroom:
            case R.id.one_happiness:
            case R.id.one_child_readingroom:
            case R.id.one_hat_readingroom:
            case R.id.two_multi:
            case R.id.two_disabled:
            case R.id.two_electronic:
            case R.id.two_multicultural:
            case R.id.two_computerroom:
            case R.id.two_workshop:
            case R.id.two_culturelove:
            case R.id.two_restroom:
            case R.id.three_reference_room:
            case R.id.three_breastfeeding:
            case R.id.three_lounge:
            case R.id.three_restroom:
            case R.id.three_staff:
            case R.id.three_creatroom:
            case R.id.three_infinite:
            case R.id.four_reference_room:
            case R.id.four_roof:
            case R.id.four_roof_two:
            case R.id.four_fitting:
            case R.id.four_restroom:
            case R.id.four_honor:
            case R.id.four_office:
            case R.id.bone_restaurant:
            case R.id.bone_warehouse:
            case R.id.bone_preservation:
            case R.id.bone_ready:
            case R.id.bone_restroom:
            case R.id.bone_versatile:
            case R.id.bone_adjustment:
            case R.id.bone_machine:
                place = String.valueOf(v.getTag());
                Toast.makeText(getApplicationContext(), String.valueOf(v.getTag()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RouteRealActionDialog.class);
                intent.putExtra("visite_position", visite_position);
                intent.putExtra("action_position", action_position);
                intent.putExtra("action", action);
                intent.putExtra("timedate", timedate);
                intent.putExtra("place", place);
                startActivity(intent);
                finish();
                break;

        }
    }

}
