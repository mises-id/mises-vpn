package com.test.demo

import android.app.Application
import com.vpn.lib.VPNInit

class DemoApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        VPNInit.init(this, object : VPNInit.ISdk {
            override fun getConfig(ip: String): String {
                val errResponse = """
                {
                    "code":-1,
                    "msg":"service error"
                }
                """.trimIndent()
                val url = "https://api1.test.mises.site/api/v1/vpn/server_link"
                val jsonPayload = """
                {
                    "server": "$ip"
                }
                """.trimIndent()
                return try {
                    httpPost(url, jsonPayload) ?: errResponse
                } catch (e: Exception) {
                    errResponse
                }
            }

            override fun getServer(): String {
                val errResponse = """
                {
                    "code":-1,
                    "msg":"service error"
                }
                """.trimIndent()
                val url = "https://api1.test.mises.site/api/v1/vpn/server_list"
                return try {
                    httpGet(url) ?: ""
                } catch (e: Exception) {
                    errResponse
                }
            }
        })
    }
}