package com.example.sky87.inlibrary.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sky87.inlibrary.R;


/**
 * Created by User on 2016-03-08.
 */
public class GridViewAdapter extends CursorAdapter {

    private Context c;
    TextView textview;
    ImageView grid_item_image;
    LinearLayout grid_item_layout;
    private TextView mail_btn;
    private Cursor mCursor;

    public GridViewAdapter(Context c, Cursor cursor) {
        super(c, cursor);
        this.c = c;
        this.mCursor = cursor;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View v = layoutInflater.inflate(R.layout.grid_item, parent, false);
        return v;
    }

    @Override
    public void bindView(View v, final Context context, final Cursor cursor) {
        textview = (TextView) v.findViewById(R.id.grid_item_text);
        grid_item_image = (ImageView) v.findViewById(R.id.grid_item_image);
        grid_item_layout = (LinearLayout) v.findViewById(R.id.grid_item_layout);
        //mail_btn = (TextView) v.findViewById(R.id.mail_btn);
      /*  mail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, String.valueOf(cursor.getPosition()), Toast.LENGTH_SHORT).show();
             *//*   Intent intent = new Intent("mail");
                intent.putExtra("position", cursor.getPosition());
                c.sendBroadcast(intent);*//*
            }
        });*/
        textview.setText(cursor.getString(1));

    }

    @Override
    public void changeCursor(Cursor cursor) {
        super.changeCursor(cursor);
    }


}
