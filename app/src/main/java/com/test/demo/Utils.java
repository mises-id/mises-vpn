package com.test.demo;

import okhttp3.*;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Utils {

    private static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
    private static final String AUTHORIZATION_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJldGhfYWRkcmVzcyI6IjB4MzgzNmY2OThkNGU3ZDcyNDljY2MzMjkxZDljY2Q2MDhlZTcxODk4OCIsImV4cCI6MTcxNjc5MzM4MywibWlzZXNpZCI6ImRpZDptaXNlczptaXNlczE3a2RxeGZ6cDc1NHcwMDNhaGZqbDU0eXdwajU1amM5M3l1NjNzZiIsInVpZCI6NTAxNzA4LCJ1c2VybmFtZSI6IiJ9.pj4UKuYXvTeAh962AnvJxDNcO8WCWoFXvwHI2pO1kMQ";
    private static final long TIMEOUT = 30L; // Timeout in seconds

    public static Response executeRequest(String method, String url, RequestBody requestBody, Map<String, String> headers) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();

        Request.Builder requestBuilder = new Request.Builder().url(url);

        // Automatically include the Authorization header
        requestBuilder.header("Authorization", AUTHORIZATION_TOKEN);

        // Allow overriding the Authorization header if needed
        if (headers!= null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                if (!entry.getKey().equals("Authorization")) { // Do not override the Authorization header
                    requestBuilder.addHeader(entry.getKey(), entry.getValue());
                }
            }
        }

        if (requestBody!= null) {
            requestBuilder.method(method, requestBody);
        }

        Request request = requestBuilder.build();

        return client.newCall(request).execute();
    }

    public static String httpGet(String urlString, Map<String, String> headers) throws IOException {
        Response response = executeRequest("GET", urlString, null, headers);
        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("Unexpected code " + response.code() + ", no body");
        }
        try (ResponseBody responseBody = response.body()) {
            return responseBody.string();
        }
    }

    public static String httpPost(String url, String jsonPayload, Map<String, String> headers) throws IOException {
        MediaType mediaType = MediaType.get(CONTENT_TYPE_JSON);
        RequestBody body = RequestBody.create(mediaType, jsonPayload);
        Response response = executeRequest("POST", url, body, headers);
        if (!response.isSuccessful() || response.body() == null) {
            throw new IOException("Unexpected code " + response.code() + ", no body");
        }
        try (ResponseBody responseBody = response.body()) {
            return responseBody.string();
        }
    }

}
