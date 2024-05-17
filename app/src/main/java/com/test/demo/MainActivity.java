package com.test.demo;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.test.demo.databinding.ActivityMainBinding;
import com.vpn.lib.VPNInit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        VPNInit.setVip(true); // Set VIP mode after purchasing
        binding.start.setOnClickListener(v -> VPNInit.startVpn(MainActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
