package com.example.mp3player;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;


public class CustomAdapter extends ArrayAdapter {


    public CustomAdapter( Context context,  File[] files ) {
        super(context , R.layout.custom_row , files );
    }



    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext()) ;
        View customView = inflater.inflate(R.layout.custom_row, parent , false)  ;

        File file = (File)getItem(position) ;
        String filename = file.getName() ;

        TextView txt = (TextView)customView.findViewById(R.id.txt) ;
        ImageView img = (ImageView)customView.findViewById(R.id.imageView) ;

        txt.setText(filename) ;
        img.setImageResource(R.drawable.msc);

        return  customView ;


    }


}
