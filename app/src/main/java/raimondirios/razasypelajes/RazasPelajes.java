package raimondirios.razasypelajes;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RazasPelajes extends AppCompatActivity {
    private Map<String, MediaPlayer> sounds;
    private ArrayList<ImageView> horsesViews;
    TextView question;
    int answer;

    private void initializeSounds() {
        sounds = new HashMap<>();
        sounds.put(getString(R.string.horse_sound_key), MediaPlayer.create(this, R.raw.horse_sound));
        sounds.put(getString(R.string.error_sound_key), MediaPlayer.create(this, R.raw.error_sound));
        sounds.put(getString(R.string.correct_sound_key), MediaPlayer.create(this, R.raw.success_sound));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeSounds();
        setContentView(R.layout.interaccion_b);
        horsesViews = new ArrayList<>();
        horsesViews.add((ImageView) findViewById(R.id.horseImg1));
        horsesViews.add((ImageView) findViewById(R.id.horseImg2));
        horsesViews.add((ImageView) findViewById(R.id.horseImg3));
        horsesViews.add((ImageView) findViewById(R.id.horseImg4));
        question = findViewById(R.id.raceText);

        newGame();
    }

    private void newGame(){
        // get this list from somewhere with (race, hair, image name without extension)
        ArrayList<ArrayList<String>> horses = new ArrayList<>();
        Collections.shuffle(horses);

        int id;
        for (int i = 0; i < horsesViews.size(); i++) {
            id = getResources().getIdentifier(horses.get(i).get(2), "drawable", getPackageName());
            horsesViews.get(i).setImageResource(id);
        }

        Random r = new Random();
        int answerIndex = r.nextInt(horsesViews.size());
        question.setText(horses.get(answerIndex).get(0));
        answer = getResources().getIdentifier(horses.get(answerIndex).get(2), "drawable", getPackageName());
    }

    private void playSound(String str) {
        MediaPlayer sound = sounds.get(str);
        if (sound != null) sound.start();
    }

    public void selectedAnswer(View view){
        if (view.getId() == answer){
            playCorrect();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    newGame();
                }
            }, 1000);
        }else{
            playError();
        }
    }

    public void playError() {
        playSound(getString(R.string.error_sound_key));
    }

    public void playHorse(View view) {
        playSound(getString(R.string.horse_sound_key));
    }

    public void playCorrect() {
        playSound(getString(R.string.correct_sound_key));
    }
}
