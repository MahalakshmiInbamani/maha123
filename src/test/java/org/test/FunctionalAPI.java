package org.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FunctionalAPI {

    private final HttpClient httpClient = HttpClientBuilder.create().build();
    private final String baseUrl = "https://petstore.swagger.io/v2";

  
    @Test
    public void testGetPetById() throws IOException {
        int petId = 1;
        String endpoint = "/pet/" + petId;
        HttpGet httpGet = new HttpGet(baseUrl + endpoint);
        HttpResponse response = httpClient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200, "GET request failed for " + endpoint);
      
        
    }

    @Test
    public void testCreatePet() throws IOException {
        String endpoint = "/pet";
        String requestBody = "{\"id\" 1: \"name\": \"cat\", \"status\": \"available\"}";
        HttpPost httpPost = new HttpPost(baseUrl + endpoint);
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setEntity(new StringEntity(requestBody));
        HttpResponse response = httpClient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        Assert.assertEquals(statusCode, 200, "POST request failed for " + endpoint);
       
    }

    private String getResponseBody(HttpResponse response) throws IOException {
        HttpEntity entity = response.getEntity();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()))) {
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        }
    }
   
}





