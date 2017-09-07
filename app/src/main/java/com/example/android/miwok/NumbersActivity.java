package com.example.android.miwok;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    //Handles playbacks of all the sound files
    private MediaPlayer mMediaPlayer;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("one", "ఒకటి(okaṭi)",R.drawable.number_one,R.raw.number_one));
        words.add(new Word("two", "రెండు(reṇḍu)",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three", "మూడు(mūḍu)",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four", "నాలుగు(nālugu)",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five", "అయిదు(ayidu)",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six", "ఆరు(āru)", R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven", "ఏడు(ēḍu)",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight", "ఎనిమిది(enimidi)",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine", "తొమ్మిది(tommidi)",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten", "పది(padi)",R.drawable.number_ten,R.raw.number_ten));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(this, words,R.color.category_numbers);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // activity_numbers.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        //set a click listner to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();
                //Get the {@link word} object at the given position the user clicked on
                Word word = words.get(position);

                //create and setup the {@link MediaPlayer for the audio resource associated
                //with the current word
                mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceId());
                //start the audio file
                mMediaPlayer.start();
                // Setup a listener on the media player, so that we can stop and release the
                // media player once the sound has finished playing.
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });
    }
    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }
}