package com.example.demo.Splunk;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;

import java.io.IOException;
@Component
public class splunk {

    public void sendLogsToSplunk(String logs,String status) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://127.0.0.1:8088/services/collector/event");
        httpPost.setHeader("Authorization", "Splunk f888c6c1-6502-405c-bcce-3462a3c06a3f");
        httpPost.setHeader("Content-Type", "application/json");
        StringEntity entity = new StringEntity("{\"event\":\"" + logs + "\",\"status\":\"" + status + "\"}");
        httpPost.setEntity(entity);
        HttpResponse response = httpClient.execute(httpPost);
    }

}
