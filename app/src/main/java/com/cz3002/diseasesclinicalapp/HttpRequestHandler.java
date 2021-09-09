package com.cz3002.diseasesclinicalapp;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpRequestHandler {
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private OkHttpClient client;
    public HttpRequestHandler()
    {
        this.client = new OkHttpClient();
    }
    private String post(String url, String json)
    {
        RequestBody body = RequestBody.create(json,JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try(Response response = this.client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return "Request failed";
        }
    }
    public CompletableFuture<String> joinQueue(String clinicUID, String patientUID) throws JsonProcessingException {
        Map<String,String> payload = new HashMap<>();
        payload.put("clinicUID",clinicUID);
        payload.put("patientUID",patientUID);
        String json = new ObjectMapper().writeValueAsString(payload);
        CompletableFuture<String> completableFuture =
                CompletableFuture.supplyAsync(() ->post("https://asia-southeast1-clinicaldatabase-49662.cloudfunctions.net/joinQueue",json));
        return completableFuture;
    }
    public CompletableFuture<String> checkQueuePos(String clinicUID, String patientUID) throws JsonProcessingException{
        Map<String,String> payload = new HashMap<>();
        payload.put("clinicUID",clinicUID);
        payload.put("patientUID",patientUID);
        String json = new ObjectMapper().writeValueAsString(payload);
        CompletableFuture<String> completableFuture =
                CompletableFuture.supplyAsync(() ->post("https://asia-southeast1-clinicaldatabase-49662.cloudfunctions.net/checkQueuePos",json));
        return completableFuture;
    }
    public CompletableFuture<String> deQueue(String clinicUID) throws JsonProcessingException{
        Map<String,String> payload = new HashMap<>();
        payload.put("clinicUID",clinicUID);
        String json = new ObjectMapper().writeValueAsString(payload);
        CompletableFuture<String> completableFuture =
                CompletableFuture.supplyAsync(() ->post("https://asia-southeast1-clinicaldatabase-49662.cloudfunctions.net/deQueue",json));
        return completableFuture;
    }



}
