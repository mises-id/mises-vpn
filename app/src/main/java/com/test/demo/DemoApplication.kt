package com.test.demo

import android.app.Application
import com.vpn.lib.VPNInit

class DemoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        //subUrl为服务器地址，国内测试url为https://f4ee0069-3385-3d53-b31e-2e26602d6337.subdotu.com/link/EExDwNuEySSN43Gl?sub=3，正式上线请通知我们配置
        VPNInit.init(this,"https://f4ee0069-3385-3d53-b31e-2e26602d6337.subdotu.com/link/EExDwNuEySSN43Gl?sub=3")
    }
}