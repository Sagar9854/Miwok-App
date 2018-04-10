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
public class ColorsFragment extends Fragment {

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

    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.list_view, container, false );

        mAudioManager = (AudioManager) getActivity().getSystemService( Context.AUDIO_SERVICE );

        final ArrayList<Word> colors = new ArrayList<Word>();
        colors.add( new Word( "Red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red ) );
        colors.add( new Word( "Green", "chokokki", R.drawable.color_green, R.raw.color_green ) );
        colors.add( new Word( "Brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown ) );
        colors.add( new Word( "Gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray ) );
        colors.add( new Word( "Black", "kululli", R.drawable.color_black, R.raw.color_black ) );
        colors.add( new Word( "White", "kelelli", R.drawable.color_white, R.raw.color_white ) );
        colors.add( new Word( "Dusty Yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow ) );
        colors.add( new Word( "Musturd Yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow ) );


        WordAdapter colorArrayAdapter = new WordAdapter( getActivity(), colors, R.color.category_colors );

        final ListView colorsList = (ListView) rootView.findViewById( R.id.list );

        colorsList.setAdapter( colorArrayAdapter );

        colorsList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
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
                            colors.get( i ).getMusicId() );
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
