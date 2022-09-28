package com.chahye.datatransferjava;

import android.Manifest;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chahye.datatransferjava.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {
    private FragmentFirstBinding binding;

    // https://developer.android.com/training/basics/intents/result#java
    private ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(), uri -> {
                binding.imageView.setImageURI(uri);
            }
    );

    // https://developer.android.com/training/permissions/requesting
//    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
//            new ActivityResultContracts.RequestPermission(), isGranted -> {
//                if (isGranted) {
//                    Toast.makeText(requireContext(), "성공", Toast.LENGTH_SHORT).show();
//                }
//            }
//    );

    // https://developer.android.com/training/location/permissions
    private ActivityResultLauncher<String[]> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                Boolean coarseLocationGranted = result.get(Manifest.permission.ACCESS_COARSE_LOCATION);
                Boolean writeStorageGranted = result.get(Manifest.permission.READ_EXTERNAL_STORAGE);

                if (coarseLocationGranted != null && coarseLocationGranted) {
                    Toast.makeText(requireContext(), "ACCESS_COARSE_LOCATION 성공", Toast.LENGTH_SHORT).show();
                }

                if (writeStorageGranted != null && writeStorageGranted) {
                    Toast.makeText(requireContext(), "READ_EXTERNAL_STORAGE 성공", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button.setOnClickListener(v -> {
            Bundle result = new Bundle();
            result.putString("data", "hello");
            getParentFragmentManager().setFragmentResult("requestKey", result);
            Navigation.findNavController(view).navigate(R.id.action_firstFragment_to_secondFragment);
        });

        binding.buttonPhoto.setOnClickListener(v -> {
            mGetContent.launch("image/*");
        });

        binding.buttonPermission.setOnClickListener(v -> {
            //requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
            requestPermissionLauncher.launch(new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            });
        });
    }

}