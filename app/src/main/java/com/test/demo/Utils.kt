package com.test.demo

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

const val CONTENT_TYPE_JSON = "application/json; charset=utf-8"
const val AUTHORIZATION_TOKEN = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJldGhfYWRkcmVzcyI6IjB4MzgzNmY2OThkNGU3ZDcyNDljY2MzMjkxZDljY2Q2MDhlZTcxODk4OCIsImV4cCI6MTcxNTg4OTc2MSwibWlzZXNpZCI6ImRpZDptaXNlczptaXNlczE3a2RxeGZ6cDc1NHcwMDNhaGZqbDU0eXdwajU1amM5M3l1NjNzZiIsInVpZCI6NTAxNzA4LCJ1c2VybmFtZSI6IiJ9.XmLhTuAAWFZmfhcOkhwkRNDdCtO0a3_NICRSedFYpQQ"
private const val TIMEOUT = 30L // Timeout in seconds

fun executeRequest(method: String, url: String, requestBody: RequestBody? = null, headers: Map<String, String>? = null): Response? {
    val client = OkHttpClient.Builder()
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    val requestBuilder = Request.Builder()
        .url(url)

    // Automatically include the Authorization header
    requestBuilder.header("Authorization", AUTHORIZATION_TOKEN)

    // Allow overriding the Authorization header if needed
    headers?.let { headerMap ->
        headerMap.forEach { (key, value) ->
            if (key!= "Authorization") { // Do not override the Authorization header
                requestBuilder.addHeader(key, value)
            }
        }
    }

    if (requestBody!= null) {
        requestBuilder.method(method, requestBody)
    }

    val request = requestBuilder.build()

    return try {
        client.newCall(request).execute()
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun httpGet(urlString: String, headers: Map<String, String>? = null): String? {
    return executeRequest("GET", urlString, headers = headers)?.use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code ${response.code}")
        response.body?.string()
    }
}

fun httpPost(url: String, jsonPayload: String, headers: Map<String, String>? = null): String? {
    val body = jsonPayload.toRequestBody(CONTENT_TYPE_JSON.toMediaTypeOrNull())
    return executeRequest("POST", url, body, headers = headers)?.use { response ->
        if (!response.isSuccessful) throw IOException("Unexpected code ${response.code}")
        response.body?.string()
    }
}
