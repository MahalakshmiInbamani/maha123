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


    public class UseCaseTest {

        private final HttpClient httpClient = HttpClientBuilder.create().build();
        private final String baseUrl = "https://petstore.swagger.io/v2";

        @Test
        public void testUserScenario() throws IOException {
            // Step 1: Create a new pet
            String createEndpoint = "/pet";
            String createRequestBody = "{\"id\": 10, \"name\": \"rabbit\", \"status\": \"available\"}";
            HttpPost httpPost = new HttpPost(baseUrl + createEndpoint);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(createRequestBody));
            HttpResponse createResponse = httpClient.execute(httpPost);
            int createStatusCode = createResponse.getStatusLine().getStatusCode();
            Assert.assertEquals(createStatusCode, 200, "POST request failed for " + createEndpoint);
            String createResponseBody = getResponseBody(createResponse);
            

            // Step 2: Get the newly created pet by ID
            int petId = 0;
            String getEndpoint = "/pet/" + petId;
            HttpGet httpGet = new HttpGet(baseUrl + getEndpoint);
            HttpResponse getResponse = httpClient.execute(httpGet);
            int getStatusCode = getResponse.getStatusLine().getStatusCode();
            Assert.assertEquals(getStatusCode, 200, "GET request failed for " + getEndpoint);
            String getResponseBody = getResponseBody(getResponse);
                       
            
            // Step 3: Update the pet's status
            String updateEndpoint = "/pet";
            String updateRequestBody = "{\"id\": 10, \"name\": \"rabbit\", \"status\": \"sold\"}";
            HttpPost httpPostUpdate = new HttpPost(baseUrl + updateEndpoint);
            httpPostUpdate.setHeader("Content-Type", "application/json");
            httpPostUpdate.setEntity(new StringEntity(updateRequestBody));
            HttpResponse updateResponse = httpClient.execute(httpPostUpdate);
            int updateStatusCode = updateResponse.getStatusLine().getStatusCode();
            Assert.assertEquals(updateStatusCode, 200, "POST request failed for " + updateEndpoint);
            String updateResponseBody = getResponseBody(updateResponse);
          
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
   

   






