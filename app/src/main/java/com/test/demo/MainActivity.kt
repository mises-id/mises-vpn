package com.test.demo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.test.demo.databinding.ActivitiyMainBinding
import com.vpn.lib.VPNInit

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivitiyMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitiyMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        VPNInit.setVip(true)//购买VIP后设置true
        binding.start.setOnClickListener {
            VPNInit.startVpn(this)
        }
    }

    override fun onResume() {
        super.onResume()

    }


}