package raimondirios.razasypelajes;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

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

public class Cruzas extends Activity {
    private Map<String, MediaPlayer> sounds;
    private ArrayList<ImageView> horsesViews;
    ImageView question;
    int answer;
    ArrayList<List<String>> horses;
    ArrayList<List<String>> parents;

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
        question = findViewById(R.id.mainHorseImg);
        horses = new ArrayList<>();

        String json = getJSONFromRaw(R.raw.horses);
        List<String> horse;
        try {
            JSONArray horsesJSON = new JSONArray(json);
            for (int i = 0; i < horsesJSON.length(); i++) {
                JSONObject horseJSON = horsesJSON.getJSONObject(i);
                String raza = horseJSON.getString("raza");
                String pelaje = horseJSON.getString("pelaje");
                String image = horseJSON.getString("image");
                horse= Arrays.asList(raza, pelaje, image);
                horses.add(horse);
            }
        } catch (JSONException e) { e.printStackTrace(); }

        parents = new ArrayList<>();

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
        answer = horsesViews.get(answerIndex).getId();
        ArrayList<String> parentsOfAnswer = new ArrayList<>();
        for (int i = 0; i < parents.size(); i++) {
            // si la cruza es la raza
            if (parents.get(i).get(0).equals(horses.get(answerIndex).get(0)))
                // añadir imagen a imagenes de padres de la respuesta
                parentsOfAnswer.add(parents.get(i).get(1));
        }
        int parentAnswerIndex = r.nextInt(parentsOfAnswer.size());
        id = getResources().getIdentifier(parentsOfAnswer.get(parentAnswerIndex), "drawable", getPackageName());
        question.setImageResource(id);
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
