package org.test;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SecurityTesting {
	private final HttpClient httpClient = HttpClientBuilder.create().build();
    private final String baseUrl = "https://petstore.swagger.io/v2";
	 @Test
	    public void testSqlInjection() throws IOException {
	        String vulnerableEndpoint = "/pet/findByStatus";
	        String status = "available'OR'1'='1";
	        String url = baseUrl + vulnerableEndpoint + "?status=" + status;
	        HttpGet httpGet = new HttpGet(url);
	        HttpResponse response = httpClient.execute(httpGet);
	        int statusCode = response.getStatusLine().getStatusCode();
	        Assert.assertNotEquals(statusCode, 200, "SQL Injection vulnerability found in " + vulnerableEndpoint);
	    }

	    @Test
	    public void testAuthentication() throws IOException {
	        String secureEndpoint = "/user/login";
	        String requestBody = "{\"username\": \"testuser\", \"password\": \"testpassword\"}";
	        HttpPost httpPost = new HttpPost(baseUrl + secureEndpoint);
	        httpPost.setHeader("Content-Type", "application/json");
	        httpPost.setEntity(new StringEntity(requestBody));
	        HttpResponse response = httpClient.execute(httpPost);
	        int statusCode = response.getStatusLine().getStatusCode();
	        // A well-secured API should return a proper authentication response, such as a token
	        Assert.assertEquals(statusCode, 200, "Authentication failed for " + secureEndpoint);
	        String responseBody = getResponseBody(response);
	       
	    }

	    //  to read the response body
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


