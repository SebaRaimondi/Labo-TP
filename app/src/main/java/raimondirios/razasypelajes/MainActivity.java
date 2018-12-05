package raimondirios.razasypelajes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String json = getJSONFromRaw(R.raw.horses);
        try {
            JSONArray horses = new JSONArray(json);
            for (int i = 0; i < horses.length(); i++) {
                JSONObject horse = horses.getJSONObject(i);
                String raza = horse.getString("raza");
                String pelaje = horse.getString("pelaje");
                String image = horse.getString("image");
            }
        } catch (JSONException e) { e.printStackTrace(); }
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
