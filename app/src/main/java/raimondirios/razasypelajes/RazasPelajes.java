package raimondirios.razasypelajes;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RazasPelajes extends AppCompatActivity {
    final MediaPlayer horseSound = MediaPlayer.create(this, R.raw.horse_sound);
    final MediaPlayer correctSound = MediaPlayer.create(this, R.raw.success_sound);
    final MediaPlayer errorSound = MediaPlayer.create(this, R.raw.error_sound);
    final int correct = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zzz_viejo_interacion_b);
    }

    public void playError(View view) {
        errorSound.start();
    }

    public void playHorse(View view) {
        horseSound.start();
    }

    public void playCorrect(View view) {
        correctSound.start();
    }
}
