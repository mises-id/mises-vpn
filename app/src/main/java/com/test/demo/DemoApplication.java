package com.test.demo;

import static com.test.demo.Utils.httpGet;
import static com.test.demo.Utils.httpPost;

import android.app.Application;

import androidx.annotation.NonNull;

import com.vpn.lib.VPNInit;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        VPNInit.INSTANCE.init(this, new VPNInit.ISdk() {
            @NonNull
            @Override
            public String getConfig(String ip) {
                String errResponse = "{ \"code\": -1, \"msg\": \"service error\" }";
                String url = "https://api1.test.mises.site/api/v1/vpn/server_link";
                String jsonPayload = "{ \"server\": \"" + ip + "\" }";

                try {
                    // Assuming httpPost and httpGet methods are defined elsewhere or imported
                    String result = httpPost(url, jsonPayload, null);
                    if (result.isEmpty()) {
                        return errResponse;
                    }
                    return result;
                } catch (Exception e) {
                    return errResponse;
                }
            }

            @NonNull
            @Override
            public String getServer() {
                String errResponse = "{ \"code\": -1, \"msg\": \"service error\" }";
                String url = "https://api1.test.mises.site/api/v1/vpn/server_list";

                try {
                    // Assuming httpGet method is defined elsewhere or imported
                    String result = httpGet(url, null);
                    if (result.isEmpty()) {
                        return "";
                    }
                    return result;
                } catch (Exception e) {
                    return errResponse;
                }
            }
        });
    }
}
