package com.chahye.datatransferjava;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.chahye.datatransferjava.databinding.ActivityResultBinding;

public class ResultActivity extends AppCompatActivity {
    private ActivityResultBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toast.makeText(this, "message: " + getIntent().getStringExtra("message"), Toast.LENGTH_SHORT).show();

        binding.buttonBack.setOnClickListener(v -> {
            // https://developer.android.com/training/sharing/send#java
            Intent result = new Intent();
            result.putExtra("message", "Hey, SecondFragment");
            setResult(Activity.RESULT_OK, result);
            finish();
        });
    }

}