package com.example.android.miwok;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sagar on 15/12/17.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;

    public WordAdapter(Activity context, ArrayList<Word> words, int colorVal) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super( context, 0, words );
        mColorResourceId = colorVal;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from( getContext() ).inflate(
                    R.layout.list_item, parent, false );
        }

        final Word currentWord = getItem( position );

        TextView def = (TextView) listItemView.findViewById( R.id.default_text_view );
        TextView mimok = (TextView) listItemView.findViewById( R.id.miwok_text_view );
        ImageView img = (ImageView) listItemView.findViewById( R.id.image );

        /**
         * setting the default and the mimok translated words
         */

        def.setText( currentWord.getmDefaultString() );
        mimok.setText( currentWord.getmMemokString() );
        //Log.d("Image Value", "getView: " + currentWord.getImageId());
        /**
         * checking if the Vieew has a image or not
         * if there is, it must display
         * else it should remove the image field
         */

        if (currentWord.getImageResouceId() != 0)
            img.setImageResource( currentWord.getImageResouceId() );
        else img.setVisibility( View.GONE );

        /**
         * setting the backgound color
         */

        View textContainer = listItemView.findViewById( R.id.text_container );

        int color = ContextCompat.getColor( getContext(), mColorResourceId );
        textContainer.setBackgroundColor( color );

        return listItemView;
    }


}
