package raimondirios.razasypelajes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.sex), true);
        editor.putBoolean(getString(R.string.level), true);
        editor.putInt(getString(R.string.minijuego), 0);
        editor.putInt(getString(R.string.viewMode), 0);
        editor.putStringSet(getString(R.string.gameMode), new HashSet<String>());
        editor.putInt(getString(R.string.modo_interaccion), 1);
        editor.apply();
    }

    public void toConfig(View view) {
        Intent intent = new Intent(this, ConfigActivity.class);
        startActivity(intent);
    }

    public void toRazasPelajes(View view) {
        Intent intent = new Intent(this, RazasPelajes.class);
        startActivity(intent);
    }

    public void toCruzas(View view) {
        Intent intent = new Intent(this, Cruzas.class);
        startActivity(intent);
    }
}
