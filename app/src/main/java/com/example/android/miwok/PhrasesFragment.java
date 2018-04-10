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
public class PhrasesFragment extends Fragment {

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

    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.list_view, container, false );

        mAudioManager = (AudioManager) getActivity().getSystemService( Context.AUDIO_SERVICE );

        final ArrayList<Word> phrasesList = new ArrayList<Word>();

        phrasesList.add( new Word( "Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going ) );
        phrasesList.add( new Word( "What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name ) );
        phrasesList.add( new Word( "My name is...", "oyaaset...", R.raw.phrase_my_name_is ) );
        phrasesList.add( new Word( "How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling ) );
        phrasesList.add( new Word( "I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good ) );
        phrasesList.add( new Word( "Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming ) );
        phrasesList.add( new Word( "Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming ) );
        phrasesList.add( new Word( "I’m coming.", "әәnәm", R.raw.phrase_im_coming ) );
        phrasesList.add( new Word( "Let’s go.", "yoowutis", R.raw.phrase_lets_go ) );
        phrasesList.add( new Word( "Come here.", "әnni'nem", R.raw.phrase_come_here ) );

        WordAdapter phrasesAdapter = new WordAdapter( getActivity(), phrasesList, R.color.category_phrases );

        ListView phrasesListView = (ListView) rootView.findViewById( R.id.list );

        phrasesListView.setAdapter( phrasesAdapter );

        phrasesListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
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
                            phrasesList.get( i ).getMusicId() );
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
