package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class EnterNoteActivity extends AppCompatActivity {

    private EditText editTextEnterNote;
    private RadioButton radioButtonLow;
    private RadioButton radioButtonMedium;
    private Button buttonSave;

    private EnterNoteViewModel viewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_note);
        viewModel = new ViewModelProvider(this).get(EnterNoteViewModel.class);
        viewModel.getShouldCloseScreen().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldClose) {
                if (shouldClose){
                    finish();
                }
            }
        });
        init();
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void init(){
        editTextEnterNote = findViewById(R.id.EditTextEnterNote);
        radioButtonLow = findViewById(R.id.RadioButtonLow);
        radioButtonMedium = findViewById(R.id.RadioButtonMedium);
        buttonSave = findViewById(R.id.ButtonSave);
    }

    private void saveNote(){
        String text = editTextEnterNote.getText().toString().trim();
        int priority = getPriority();
        Note note = new Note(text,priority);
        viewModel.saveNote(note);

    }
    private int getPriority(){
        int priority;
        if (radioButtonLow.isChecked()){
            priority = 0;
        } else if (radioButtonMedium.isChecked()) {
            priority = 1;
        }
        else {
            priority = 2;
        }
        return priority;
    }
    public static Intent newIntent(Context context){
        return new Intent(context,EnterNoteActivity.class);
    }
}