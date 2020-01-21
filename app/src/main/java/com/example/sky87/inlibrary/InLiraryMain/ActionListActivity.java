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
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sky87.inlibrary.R;
import com.example.sky87.inlibrary.adapter.GridViewAdapter;
import com.example.sky87.inlibrary.db.DBManager;
import com.example.sky87.inlibrary.dialog.ActionDialog;
import com.example.sky87.inlibrary.util.Contact;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

/**
 * Created by sky87 on 2016-06-25.
 */
public class ActionListActivity extends Activity implements View.OnClickListener {
    private String position;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private TextView visit_text;

    private ImageButton add_btn;
    private ListView grid_view;
    private DBManager dbManager;
    private ImageView delete_image;
    private GridViewAdapter gridViewAdapter;

    private String mPosition;
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionlist);
        setSlideMenu();
        Intent intent = getIntent();
        position = intent.getStringExtra("position");

        add_btn = (ImageButton) findViewById(R.id.add_btn);
        grid_view = (ListView) findViewById(R.id.grid_view);
        delete_image = (ImageView) findViewById(R.id.delete_image);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        visit_text = (TextView) findViewById(R.id.visit_text);
        add_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        visit_text.setOnClickListener(this);
        //Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        int count = 0;
        try {
            dbManager = new DBManager(getApplicationContext(), "vist", null, 1);
            SQLiteDatabase db = dbManager.getReadableDatabase();
            final Cursor cursor = db.rawQuery("select * from " + Contact.ACTION_LIST + position, null);
            gridViewAdapter = new GridViewAdapter(this, cursor);
            grid_view.setAdapter(gridViewAdapter);
            grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int actionposition, long id) {
                    Intent intent = new Intent(getApplicationContext(), RouteListActivity.class);
                    intent.putExtra("visite_position", String.valueOf(position));
                    intent.putExtra("action_position", String.valueOf(actionposition));
                    intent.putExtra("action", cursor.getString(2));
                    startActivity(intent);
                }
            });
            grid_view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                    ClipData data = new ClipData("view", mimeTypes, item);
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                    view.startDrag(data, shadowBuilder, view, 0);
                    mPosition = String.valueOf(position);
                    return true;
                }
            });
            delete_image.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DROP:
                            if (v == delete_image) {
                                try {
                                    dbManager.delete("delete from " + Contact.ACTION_LIST + position + " where _id = '" + cursor.getInt(0) + "';");
                                    dbManager.delete("delete from " + Contact.ROUTE_LIST + position + mPosition + " ;");
                                } catch (SQLiteException e) {
                                    e.printStackTrace();
                                }
                                onResume();
                            }
                            break;
                    }
                    return true;
                }
            });
        } catch (SQLiteException e) {
            e.printStackTrace();
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
                Intent intent = new Intent(getApplicationContext(), ActionDialog.class);
                intent.putExtra("position", position);
                startActivity(intent);
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.visit_text:
                finish();
                break;
        }
    }

}
