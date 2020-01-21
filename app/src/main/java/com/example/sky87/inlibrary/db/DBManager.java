package com.example.sky87.inlibrary.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.sky87.inlibrary.util.Contact;

/**
 * Created by sky87 on 2016-06-23.
 */
public class DBManager extends SQLiteOpenHelper {
    private SQLiteDatabase date;

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Contact.VIST_LIST + "( _id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void update(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public void delete(String _query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(_query);
        db.close();
    }

    public String PrintData() {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        Cursor cursor = db.rawQuery("select * from " + Contact.VIST_LIST, null);
        cursor.getCount();
        Log.e("getcount", String.valueOf(cursor.getCount()));
        while (cursor.moveToNext()) {
            str += cursor.getInt(0)
                    +
                    cursor.getString(1)
                    + "\n";
        }

        return str;
    }

    public String ReadData(String list, int position) {
        SQLiteDatabase db = getReadableDatabase();
        String str = "";
        Cursor cursor = db.rawQuery("select * from " + Contact.VIST_LIST, null);
        cursor.getCount();
        Log.e("getcount", String.valueOf(cursor.getCount()));
        while (cursor.moveToPosition(position)) {
            str += " : 도착시간 " +
                    cursor.getString(1) + "\n"
                    + ",방문 목적 = "
                    + cursor.getString(2) + "\n"
                    + ",입장 경로 = "
                    + cursor.getString(3);
        }

        return str;
    }

    public Cursor getCursor(String list) {
        SQLiteDatabase db = getReadableDatabase();
        if (list.equals(Contact.VIST_LIST)) {
            Cursor cursor = db.rawQuery("select * from " + Contact.VIST_LIST, null);
            return cursor;
        } else {
            Cursor cursor = db.rawQuery("select * from " + Contact.VIST_LIST, null);
            return cursor;
        }
    }
}
