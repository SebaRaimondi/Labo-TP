package raimondirios.razasypelajes;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Switch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ConfigActivity extends AppCompatActivity {
    Switch levelSwitch;
    Switch sexSwitch;
    RadioGroup minijuegoRadio;
    RadioGroup viewModeRadio;
    RadioGroup modoInteraccionRadio;
    HashMap<String, CheckBox> gameModeCheckBox = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        setValues();
    }

    private void setValues(){
        levelSwitch = (Switch) findViewById(R.id.Level);
        sexSwitch = (Switch) findViewById(R.id.Sex);
        minijuegoRadio = (RadioGroup) findViewById(R.id.Minijuego);
        viewModeRadio = (RadioGroup) findViewById(R.id.ViewMode);
        modoInteraccionRadio = (RadioGroup) findViewById(R.id.ModoInteraccion);
        gameModeCheckBox.put(getString(R.string.razas), (CheckBox)findViewById(R.id.RazasCheckbox));
        gameModeCheckBox.put(getString(R.string.pelajes), (CheckBox)findViewById(R.id.PelajesCheckbox));
        gameModeCheckBox.put(getString(R.string.cruzas), (CheckBox)findViewById(R.id.CruzasCheckbox));

        levelSwitch.setChecked(
                getPreferences(Context.MODE_PRIVATE)
                        .getBoolean(getString(R.string.level), false));
        sexSwitch.setChecked(
                getPreferences(Context.MODE_PRIVATE)
                        .getBoolean(getString(R.string.sex), false));
        minijuegoRadio.check(getPreferences(Context.MODE_PRIVATE)
                .getInt(getString(R.string.minijuego), 0));
        viewModeRadio.check(getPreferences(Context.MODE_PRIVATE)
                .getInt(getString(R.string.viewMode), 0));
        modoInteraccionRadio.check(getPreferences(Context.MODE_PRIVATE)
                .getInt(getString(R.string.modo_interaccion), 1));
        for (CheckBox checkBox : gameModeCheckBox.values()){
            checkBox.setChecked(false);
        }
        for (String checkBoxKey : getPreferences(Context.MODE_PRIVATE)
                .getStringSet(getString(R.string.gameMode), new HashSet<String>())){
            gameModeCheckBox.get(checkBoxKey).setChecked(true);
        }
    }

    public void onAccept(View view) {
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(getString(R.string.sex), sexSwitch.isChecked());
        editor.putBoolean(getString(R.string.level), levelSwitch.isChecked());
        editor.putInt(getString(R.string.minijuego), minijuegoRadio.getCheckedRadioButtonId());
        editor.putInt(getString(R.string.viewMode), viewModeRadio.getCheckedRadioButtonId());
        editor.putInt(getString(R.string.modo_interaccion), modoInteraccionRadio.getCheckedRadioButtonId());
        HashSet<String> selectedGameModes = new HashSet<>();
        for (Map.Entry<String, CheckBox> entry : gameModeCheckBox.entrySet()){
            if (entry.getValue().isChecked()){
                selectedGameModes.add(entry.getKey());
            }
        }
        editor.putStringSet(getString(R.string.gameMode), selectedGameModes);
        editor.apply();
        finish();
    }

}
