package org.test;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class StressTesting {
	   private final HttpClient httpClient = HttpClientBuilder.create().build();
	    private final String baseUrl = "https://petstore.swagger.io/v2";

	    @Test(threadPoolSize = 10, invocationCount = 100)
	    public void testApiStress() throws Exception {
	        String endpoint = "/pet/findByStatus";
	        String status = "available";
	        String url = baseUrl + endpoint + "?status=" + status;
	        HttpGet httpGet = new HttpGet(url);
	        HttpResponse response = httpClient.execute(httpGet);
	        int statusCode = response.getStatusLine().getStatusCode();
	        if (statusCode != 200) {
	            throw new RuntimeException("API request failed with status code: " + statusCode);
	        }
	        String responseBody = getResponseBody(response);
	        boolean res = responseBody.contains(status);
	        System.out.println("Success Response");

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


