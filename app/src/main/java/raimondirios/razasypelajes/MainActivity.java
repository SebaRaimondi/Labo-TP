package raimondirios.razasypelajes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toConfig(View view) {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }

    public void onJugar(View view) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        Intent intent = new Intent(this, RazasPelajes.class);
        switch (sharedPref.getInt(getString(R.string.minijuego), R.id.razasYPelajes)){
            case R.id.razasYPelajes:
                intent = new Intent(this, RazasPelajes.class);
                break;
            case R.id.razasYPelajesJuntas:
                intent = new Intent(this, RazasPelajes.class);
                break;
            case R.id.Cruzas:
                intent = new Intent(this, Cruzas.class);
                break;
        }
        startActivity(intent);
    }
}
