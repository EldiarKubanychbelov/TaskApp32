package kg.geektech.taskapp31.ui.profile;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.Preference;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import kg.geektech.taskapp31.App;
import kg.geektech.taskapp31.Prefs;
import kg.geektech.taskapp31.R;
import kg.geektech.taskapp31.models.Task;


public class ProfileFragment extends Fragment {

    private EditText edUserName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edUserName = view.findViewById(R.id.ed_name);

        String text = edUserName.getText().toString();

        Task task = new Task(text);
        App.getAppDatabase().taskDao().insert(task);


        Prefs prefs = new Prefs(getContext());
        edUserName.setText(prefs.getString("autoSave"));


        edUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.putString("autoSave", s.toString());
            }
        });
    }




}