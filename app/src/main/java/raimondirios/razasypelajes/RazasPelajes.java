package raimondirios.razasypelajes;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class RazasPelajes extends AppCompatActivity {
    private Map<String, MediaPlayer> sounds;

    private void initializeSounds() {
        sounds = new HashMap<>();
        sounds.put("horse", MediaPlayer.create(this, R.raw.horse_sound));
        sounds.put("error", MediaPlayer.create(this, R.raw.error_sound));
        sounds.put("correct", MediaPlayer.create(this, R.raw.success_sound));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeSounds();

        setContentView(R.layout.interaccion_b);
    }

    public void playError(View view) {
        sounds.get("error").start();
    }

    public void playHorse(View view) {
        sounds.get("horse").start();
    }

    public void playCorrect(View view) {
        sounds.get("correct").start();
    }
}
