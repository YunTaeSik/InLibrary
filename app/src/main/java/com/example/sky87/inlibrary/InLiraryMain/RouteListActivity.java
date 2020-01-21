package com.example.sky87.inlibrary.InLiraryMain;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sky87.inlibrary.R;
import com.example.sky87.inlibrary.adapter.GridViewAdapter;
import com.example.sky87.inlibrary.db.DBManager;
import com.example.sky87.inlibrary.dialog.RouteFinishDialog;
import com.example.sky87.inlibrary.dialog.RouteTimeDialog;
import com.example.sky87.inlibrary.util.Contact;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

/**
 * Created by sky87 on 2016-07-03.
 */
public class RouteListActivity extends Activity implements View.OnClickListener {
    private String visite_position;
    private String action_position;
    private String action;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;

    private ImageButton add_btn;
    private ListView grid_view;
    private DBManager dbManager;
    private ImageView delete_image;
    private GridViewAdapter gridViewAdapter;
    private LinearLayout finish_layout;
    private ImageView back_btn;
    private TextView visit_text;
    private TextView action_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routelist);
        setSlideMenu();
        Intent intent = getIntent();
        visite_position = intent.getStringExtra("visite_position");
        action_position = intent.getStringExtra("action_position");
        action = intent.getStringExtra("action");

        add_btn = (ImageButton) findViewById(R.id.add_btn);
        grid_view = (ListView) findViewById(R.id.grid_view);
        delete_image = (ImageView) findViewById(R.id.delete_image);
        finish_layout = (LinearLayout) findViewById(R.id.finish_layout);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        visit_text = (TextView) findViewById(R.id.visit_text);
        action_text = (TextView) findViewById(R.id.action_text);
        add_btn.setOnClickListener(this);
        finish_layout.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        visit_text.setOnClickListener(this);
        action_text.setOnClickListener(this);
        // Toast.makeText(getApplicationContext(), visite_position + action_position + action, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            dbManager = new DBManager(getApplicationContext(), "vist", null, 1);
            SQLiteDatabase db = dbManager.getReadableDatabase();
            final Cursor cursor = db.rawQuery("select * from " + Contact.ROUTE_LIST + visite_position + action_position, null);
            Log.e("test", String.valueOf(cursor.getCount()));
            gridViewAdapter = new GridViewAdapter(this, cursor);
            grid_view.setAdapter(gridViewAdapter);

            grid_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                    ClipData data = new ClipData("view", mimeTypes, item);
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    return true;
                }
            });
            delete_image.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DROP:
                            if (v == delete_image) {
                                dbManager.delete("delete from " + Contact.ROUTE_LIST + visite_position + action_position + " where _id = '" + cursor.getInt(0) + "';");
                                onResume();
                            }
                            break;
                    }
                    return true;
                }
            });
        } catch (SQLiteException e) {
            //e.printStackTrace();
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
                        Intent main = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(main);
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

                        break;
                    case 4:
                        mDrawerLayout.closeDrawer(mDrawerList);
                        Intent writing_diary2 = new Intent(getApplicationContext(), DiaryActivity.class);
                        startActivity(writing_diary2);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_btn:
                Intent intent = new Intent(getApplicationContext(), RouteTimeDialog.class);
                intent.putExtra("visite_position", visite_position);
                intent.putExtra("action_position", action_position);
                intent.putExtra("action", action);
                startActivity(intent);
                break;
            case R.id.finish_layout:
                Intent finish_dialog = new Intent(getApplicationContext(), RouteFinishDialog.class);
                finish_dialog.putExtra("visite_position", visite_position);
                finish_dialog.putExtra("action_position", action_position);
                finish_dialog.putExtra("action", action);
                // startActivity(finish_dialog);
                startActivityForResult(finish_dialog, 1);
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.visit_text:
                finish();
                Intent writing_diary = new Intent(getApplicationContext(), DiaryActivity.class);
                startActivity(writing_diary);
                break;
            case R.id.action_text:
                finish();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == 1) {
                finish();
            }
        }
    }
}