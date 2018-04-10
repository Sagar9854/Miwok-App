package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        releaseMediaPlayer();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mediaPlayer.pause();
                        mediaPlayer.seekTo( 0 );
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        mediaPlayer.start();
                    }
                }
            };
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };


    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.list_view, container, false );

        mAudioManager = (AudioManager) getActivity().getSystemService( Context.AUDIO_SERVICE );

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add( new Word( "One", "Lutti", R.drawable.number_one, R.raw.number_one ) );
        words.add( new Word( "Two", "Ottiko", R.drawable.number_two, R.raw.number_two ) );
        words.add( new Word( "Three", "Tolookosu", R.drawable.number_three, R.raw.number_three ) );
        words.add( new Word( "Four", "oyyisa", R.drawable.number_four, R.raw.number_four ) );
        words.add( new Word( "Five", "massokka", R.drawable.number_five, R.raw.number_five ) );
        words.add( new Word( "Six", "temmokka", R.drawable.number_six, R.raw.number_six ) );
        words.add( new Word( "Seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven ) );
        words.add( new Word( "Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight ) );
        words.add( new Word( "Nine", "wo'e", R.drawable.number_nine, R.raw.number_nine ) );
        words.add( new Word( "Ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten ) );

//        Log.d("NumbersActivity", "onCreate: " + words.get(0));
//        Log.d("NumbersActivity", "onCreate: " + words.get(1));

        WordAdapter itemsAdapter = new WordAdapter( getActivity(), words, R.color.category_numbers );

        ListView listView = (ListView) rootView.findViewById( R.id.list );

        listView.setAdapter( itemsAdapter );

        listView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                releaseMediaPlayer();

                int result = mAudioManager.requestAudioFocus( afChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT );

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // Start playback.
                    mediaPlayer = MediaPlayer.create( getActivity(),
                            words.get( i ).getMusicId() );
                    mediaPlayer.start();
                    /**
                     * must release the mediaPlayer object to free the memory use when the
                     * music has completed playing
                     */
                    mediaPlayer.setOnCompletionListener( mCompletionListener );
                }
            }
        } );

        return rootView;
    }

    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mAudioManager.abandonAudioFocus( afChangeListener );
            mediaPlayer = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
