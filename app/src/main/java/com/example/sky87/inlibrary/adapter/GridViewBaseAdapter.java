package com.example.sky87.inlibrary.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sky87.inlibrary.R;

/**
 * Created by YunTaeSik on 2016-08-22.
 */
public class GridViewBaseAdapter extends BaseAdapter {
    private Context c;
    TextView textview;
    ImageView grid_item_image;
    LinearLayout grid_item_layout;
    private TextView mail_btn;
    private Cursor mCursor;

    public GridViewBaseAdapter(Context c, Cursor cursor) {
        this.c = c;
        this.mCursor = cursor;
    }

    @Override
    public int getCount() {
        return mCursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(c);
        View v = layoutInflater.inflate(R.layout.grid_mail_item, parent, false);
        textview = (TextView) v.findViewById(R.id.grid_item_text);
        grid_item_image = (ImageView) v.findViewById(R.id.grid_item_image);
        grid_item_layout = (LinearLayout) v.findViewById(R.id.grid_item_layout);
        mail_btn = (TextView) v.findViewById(R.id.mail_btn);
        mail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("mail");
                intent.putExtra("position", position);
                c.sendBroadcast(intent);
            }
        });
        mCursor.moveToPosition(position);
        textview.setText(mCursor.getString(1));
        return v;
    }
}
