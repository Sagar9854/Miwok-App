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
public class FamilyFragment extends Fragment {


    private MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Permanent loss of audio focus
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mediaPlayer.pause();
                        mediaPlayer.seekTo( 0 );
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
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


    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.list_view, container, false );

        mAudioManager = (AudioManager) getActivity().getSystemService( Context.AUDIO_SERVICE );

        final ArrayList<Word> familyArray = new ArrayList<Word>();

        familyArray.add( new Word( "Father", "әpә", R.drawable.family_father, R.raw.family_father ) );
        familyArray.add( new Word( "Mother", "әṭa", R.drawable.family_mother, R.raw.family_mother ) );
        familyArray.add( new Word( "Son", "angsi", R.drawable.family_son, R.raw.family_son ) );
        familyArray.add( new Word( "daughter", "әṭa", R.drawable.family_daughter, R.raw.family_daughter ) );
        familyArray.add( new Word( "Older Brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother ) );
        familyArray.add( new Word( "Younger Brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother ) );
        familyArray.add( new Word( "Older Sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister ) );
        familyArray.add( new Word( "Younger Sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister ) );
        familyArray.add( new Word( "Grand Mother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother ) );
        familyArray.add( new Word( "Grand Father", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather ) );


        WordAdapter familyAdapter = new WordAdapter( getActivity(), familyArray, R.color.category_family );

        ListView familyList = (ListView) rootView.findViewById( R.id.list );

        familyList.setAdapter( familyAdapter );

        familyList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
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
                            familyArray.get( i ).getMusicId() );
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
            mediaPlayer.stop();
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
