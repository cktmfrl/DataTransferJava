package com.chahye.datatransferjava;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chahye.datatransferjava.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {
    private FragmentSecondBinding binding;

    // https://developer.android.com/training/basics/intents/result#java
    private ActivityResultLauncher<Intent> mStartForResult  = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                String message = result.getData().getStringExtra("message");
                Toast.makeText(requireContext(), "message: " + message, Toast.LENGTH_SHORT).show();
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
            String data = bundle.getString("data");
            Toast.makeText(requireContext(), data, Toast.LENGTH_SHORT).show();
        });

        binding.button.setOnClickListener(v -> {
            //Navigation.findNavController(view).navigate(R.id.action_secondFragment_to_firstFragment);
            NavHostFragment.findNavController(SecondFragment.this).popBackStack();
        });

        binding.buttonResult.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ResultActivity.class);
            intent.putExtra("message", "Hello~ ResultActivity");
            mStartForResult.launch(intent);
        });
    }

}