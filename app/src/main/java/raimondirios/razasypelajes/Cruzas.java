package raimondirios.razasypelajes;

import android.app.Activity;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HostnameVerifier;

import raimondirios.razasypelajes.Helpers.JSONHelper;
import raimondirios.razasypelajes.Horses.Horse;
import raimondirios.razasypelajes.Horses.Padres;

public class Cruzas extends Activity {
    private Map<String, MediaPlayer> sounds;
    private ArrayList<ImageView> horsesViews;
    TextView question;
    int answer;
    ArrayList<List<String>> horses;

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
        horses = new ArrayList<>();
        String json = getJSONFromRaw(R.raw.horses);
        Resources resources = getResources();

        List<Horse> horse = JSONHelper.fromJSON(Horse.class, resources.openRawResource(R.raw.horses));
        List<Padres> padres = JSONHelper.fromJSON(Padres.class, resources.openRawResource(R.raw.padres));

        newGame();
    }

    private String getJSONFromRaw(int res) {
        InputStream is = getResources().openRawResource(res);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is));
            int n;
            while ((n = reader.read(buffer)) != -1) writer.write(buffer, 0, n);
            is.close();
        } catch (IOException e) { e.printStackTrace(); }
        return writer.toString();
    }

    private void newGame(){
        Collections.shuffle(horses);

        int id;
        for (int i = 0; i < horsesViews.size(); i++) {
            id = getResources().getIdentifier(horses.get(i).get(2), "drawable", getPackageName());
            horsesViews.get(i).setImageResource(id);
        }

        Random r = new Random();
        int answerIndex = r.nextInt(horsesViews.size());
        question.setText(horses.get(answerIndex).get(0));
        answer = horsesViews.get(answerIndex).getId();
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

    public void goBack(View view){
        finish();
    }
}
