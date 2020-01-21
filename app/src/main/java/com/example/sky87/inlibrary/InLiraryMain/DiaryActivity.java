package com.example.sky87.inlibrary.InLiraryMain;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.DragEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sky87.inlibrary.R;
import com.example.sky87.inlibrary.adapter.GridViewBaseAdapter;
import com.example.sky87.inlibrary.db.DBManager;
import com.example.sky87.inlibrary.dialog.PicketDialog;
import com.example.sky87.inlibrary.dialog.SuveyCheckDialog;
import com.example.sky87.inlibrary.util.Contact;
import com.example.sky87.inlibrary.util.SharedPrefsUtils;
import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sky87 on 2016-06-23.
 */
public class DiaryActivity extends FragmentActivity implements View.OnClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerArrowDrawable drawerArrow;
    private boolean drawerArrowColor;

    private GridViewBaseAdapter gridViewAdapter;
    private GridView grid_view;
    private ImageButton add_btn;
    private DBManager dbManager;
    private Cursor cursor;
    private ImageView delete_image;
    private String mPosition;
    private ImageView back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        grid_view = (GridView) findViewById(R.id.grid_view);
        add_btn = (ImageButton) findViewById(R.id.add_btn);
        delete_image = (ImageView) findViewById(R.id.delete_image);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        setSlideMenu();

        add_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dbManager = new DBManager(getApplicationContext(), "vist", null, 1);
        cursor = dbManager.getCursor(Contact.VIST_LIST);
        gridViewAdapter = new GridViewBaseAdapter(this, cursor);
        grid_view.setAdapter(gridViewAdapter);
        grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ActionListActivity.class);
                intent.putExtra("position", String.valueOf(position)); //구분자
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
                                dbManager.delete("delete from " + Contact.VIST_LIST + " where _id = '" + cursor.getInt(0) + "';");
                                dbManager.delete("delete from " + Contact.ACTION_LIST + mPosition + " ;");

                                SQLiteDatabase db = dbManager.getReadableDatabase();
                                final Cursor cursor2 = db.rawQuery("select * from " + Contact.ACTION_LIST + mPosition, null);
                                while (cursor2.moveToNext()) {
                                }
                                for (int i = 0; i <= cursor2.getCount(); i++) {
                                    try {
                                        final Cursor cursor3 = db.rawQuery("delete from " + Contact.ROUTE_LIST + mPosition + i, null);
                                        while (cursor3.moveToNext()) {
                                        }
                                    } catch (SQLiteException e) {
                                        e.printStackTrace();
                                    }
                                }
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
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("mail");
        registerReceiver(broadcastReceiver, intentFilter);
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
                        break;
                    case 3:

                        break;
                    case 4:
                        mDrawerLayout.closeDrawer(mDrawerList);
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
                Intent picket = new Intent(getApplicationContext(), PicketDialog.class);
                startActivity(picket);
                break;
            case R.id.back_btn:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void EmailSend(String senddata, String mPosition, int count) {
        Intent it = new Intent(Intent.ACTION_SEND_MULTIPLE);
        it.setType("plain/text");
        String[] tos = {"sungjae.p@gmail.com"};
        it.putExtra(Intent.EXTRA_EMAIL, tos);
        it.putExtra(Intent.EXTRA_SUBJECT, "인라이브러리");
        it.putExtra(Intent.EXTRA_TEXT, senddata);
        ArrayList<Uri> uriList = new ArrayList<Uri>();
        for (int i = 0; i < count; i++) {
            uriList.add(Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/InLibrary/" + mPosition + i + ".png")));
        }
        it.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        startActivity(it);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("mail")) {
                try {
                    if (SharedPrefsUtils.getStringPreference(getApplicationContext(), Contact.SURVEY).equals("ON")) {
                        int position = intent.getIntExtra("position", 0);
                        Log.e("position", String.valueOf(position));
                        SQLiteDatabase db = dbManager.getReadableDatabase();
                        String str = "";
                        str += "사용자:" + SharedPrefsUtils.getStringPreference(getApplicationContext(), Contact.USER) + "\n";  //사용자 정보 추출
                        str += /*"방문리스트:" + */cursor.getString(1) + "\n";  //방문 리스트 추출
                        final Cursor cursor2 = db.rawQuery("select * from " + Contact.ACTION_LIST + String.valueOf(position), null);
                        while (cursor2.moveToNext()) {
                            str += cursor2.getString(1) + "\n";   //활동 리스트 추출
                        }
                        for (int i = 0; i <= cursor2.getCount(); i++) {
                            try {
                                final Cursor cursor3 = db.rawQuery("select * from " + Contact.ROUTE_LIST + String.valueOf(position) + i, null);
                                while (cursor3.moveToNext()) {
                                    str += cursor3.getString(1) + "\n";  //경로 리스트 추출
                                }

                            } catch (SQLiteException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.e("test", str + String.valueOf(position) + cursor2.getCount());
                        EmailSend(str, String.valueOf(position), cursor2.getCount());
                    } else {
                        Intent suvey = new Intent(getApplication(), SuveyCheckDialog.class);
                        startActivity(suvey);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getApplication(), "기록이 완료되지 않았습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        }

    };
}

