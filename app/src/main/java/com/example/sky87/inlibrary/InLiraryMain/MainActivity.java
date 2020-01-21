package com.example.sky87.inlibrary.InLiraryMain;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sky87.inlibrary.R;
import com.example.sky87.inlibrary.dialog.UserDialog;
import com.example.sky87.inlibrary.util.Contact;
import com.example.sky87.inlibrary.util.SharedPrefsUtils;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

public class MainActivity extends Activity implements View.OnClickListener {
    private LinearLayout menual_layout;
    private LinearLayout write_layout;
    private LinearLayout survey_layout;
    private LinearLayout mail_layout;
    private TextView appname;
    private TextView appname_two;
    private Animation alphaAni;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSlideMenu();

        menual_layout = (LinearLayout) findViewById(R.id.menual_layout);
        write_layout = (LinearLayout) findViewById(R.id.write_layout);
        survey_layout = (LinearLayout) findViewById(R.id.survey_layout);
        mail_layout = (LinearLayout) findViewById(R.id.mail_layout);
        appname = (TextView) findViewById(R.id.appname);
        appname_two = (TextView) findViewById(R.id.appname_two);
        menual_layout.setOnClickListener(this);
        write_layout.setOnClickListener(this);
        survey_layout.setOnClickListener(this);
        mail_layout.setOnClickListener(this);

        alphaAni = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
        appname.startAnimation(alphaAni);
        appname_two.startAnimation(alphaAni);

        user = SharedPrefsUtils.getStringPreference(getApplicationContext(), Contact.USER);

        if (user == null) {
            Intent intent = new Intent(getApplicationContext(), UserDialog.class);
            startActivity(intent);
        }
        if (SharedPrefsUtils.getStringPreference(getApplicationContext(), Contact.SURVEY) == null) {
            SharedPrefsUtils.setStringPreference(getApplicationContext(), Contact.SURVEY, "OFF");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menual_layout:
                Intent view_menual = new Intent(getApplicationContext(), MenualActivity.class);
                startActivity(view_menual);
                break;
            case R.id.write_layout:
                Intent writing_diary = new Intent(getApplicationContext(), DiaryActivity.class);
                startActivity(writing_diary);
                break;
            case R.id.survey_layout:
                mDrawerLayout.closeDrawer(mDrawerList);
                String url = "https://docs.google.com/forms/d/e/1FAIpQLSf__LOayMothUW3vmaaJJS8Es28JrZM7V3UcOFEzisn53LFJg/viewform";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                SharedPrefsUtils.setStringPreference(getApplicationContext(), Contact.SURVEY, "ON");
                break;
            case R.id.mail_layout:
                Intent mail = new Intent(getApplicationContext(), DiaryActivity.class);
                startActivity(mail);
                break;
        }

    }

    private void setSlideMenu() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navdrawer);


        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();


        String[] values = new String[]{
                "홈",
                "메뉴얼",
                "다이어리 기록",
                "설문하기",
                "이메일 보내기",
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        mDrawerLayout.closeDrawer(mDrawerList);
                        break;
                    case 1:
                        mDrawerLayout.closeDrawer(mDrawerList);
                        Intent view_menual = new Intent(getApplicationContext(), MenualActivity.class);
                        startActivity(view_menual);

                        break;
                    case 2:
                        mDrawerLayout.closeDrawer(mDrawerList);
                        Intent writing_diary = new Intent(getApplicationContext(), DiaryActivity.class);
                        startActivity(writing_diary);
                        break;
                    case 3:
                        mDrawerLayout.closeDrawer(mDrawerList);
                        String url = "https://docs.google.com/forms/d/e/1FAIpQLSf__LOayMothUW3vmaaJJS8Es28JrZM7V3UcOFEzisn53LFJg/viewform";
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        SharedPrefsUtils.setStringPreference(getApplicationContext(), Contact.SURVEY, "ON");
                        break;
                    case 4:
                        mDrawerLayout.closeDrawer(mDrawerList);
                        Intent diary = new Intent(getApplicationContext(), DiaryActivity.class);
                        startActivity(diary);
                        break;
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


}
