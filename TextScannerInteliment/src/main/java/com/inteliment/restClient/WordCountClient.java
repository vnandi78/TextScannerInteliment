package com.inteliment.restClient;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.apache.commons.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.inteliment.domain.AuthTokenInfo;
import com.inteliment.domain.WordsWithCount;
import com.inteliment.request.SearchInput;


public class WordCountClient {
	
	public static final String REST_SERVICE_URI = "http://localhost:8080/counter-api";
    
    public static final String AUTH_SERVER_URI = "http://localhost:8080/counter-api/oauth/token";
     
    public static final String QPM_PASSWORD_GRANT = "?grant_type=password&username=vnandi&password=vnandi";
     
    public static final String QPM_ACCESS_TOKEN = "?access_token=";
 
    /*
     * Prepare HTTP Headers.
     */
    private static HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }
     
    /*
     * Add HTTP Authorization header, using Basic-Authentication to send client-credentials.
     */
    private static HttpHeaders getHeadersWithClientCredentials(){
        String plainClientCredentials="my-trusted-client:secret";
        String base64ClientCredentials = new String(Base64.encodeBase64(plainClientCredentials.getBytes()));
         
        HttpHeaders headers = getHeaders();
        headers.add("Authorization", "Basic " + base64ClientCredentials);
        return headers;
    }    
     
    /*
     * Send a POST request [on /oauth/token] to get an access-token, which will then be send with each request.
     */
    @SuppressWarnings({ "unchecked"})
    private static AuthTokenInfo sendTokenRequest(){
        RestTemplate restTemplate = new RestTemplate(); 
         
        HttpEntity<String> request = new HttpEntity<String>(getHeadersWithClientCredentials());
        ResponseEntity<Object> response = restTemplate.exchange(AUTH_SERVER_URI+QPM_PASSWORD_GRANT, HttpMethod.POST, request, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>)response.getBody();
        AuthTokenInfo tokenInfo = null;
         
        if(map!=null){
            tokenInfo = new AuthTokenInfo();
            tokenInfo.setAccess_token((String)map.get("access_token"));
            tokenInfo.setToken_type((String)map.get("token_type"));
            tokenInfo.setRefresh_token((String)map.get("refresh_token"));
            tokenInfo.setExpires_in((int)map.get("expires_in"));
            tokenInfo.setScope((String)map.get("scope"));
            System.out.println(tokenInfo);
            //System.out.println("access_token ="+map.get("access_token")+", token_type="+map.get("token_type")+", refresh_token="+map.get("refresh_token")
            //+", expires_in="+map.get("expires_in")+", scope="+map.get("scope"));;
        }else{
            System.out.println("No user exist----------");
             
        }
        return tokenInfo;
    }
    
    
    /*
     * Send a POST request to create a new user.
     */
    private static void searchForGivenWordsAndReportCounts(AuthTokenInfo tokenInfo) {
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting search API----------");
        RestTemplate restTemplate = new RestTemplate();
        SearchInput searchInput = new SearchInput();
        ArrayList<String> searchText = new ArrayList<String>();
		/*
		 * ”:[“Duis”, “Sed”, “Donec”, “Augue”, “Pellentesque”, “123”
		 */
        
        searchText.add("Duis");
        searchText.add("Sed");
        searchText.add("Donec");
        searchText.add("Augue");
        searchText.add("Pellentesque");
        searchText.add("123");
        HttpEntity<Object> request = new HttpEntity<Object>(searchInput, getHeaders());
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI+"/search/"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
                request, new ParameterizedTypeReference<ArrayList<WordsWithCount>>() {
                });
        System.out.println("Location : "+uri.toASCIIString());
    }
	
    /*
     * Send a GET request to get a specific user.
     */
    private static void getTopWordRankings(AuthTokenInfo tokenInfo){
        Assert.notNull(tokenInfo, "Authenticate first please......");
        System.out.println("\nTesting top word counts API----------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<String>(getHeaders());
        ResponseEntity<String> response = restTemplate.exchange(REST_SERVICE_URI+"/top/10"+QPM_ACCESS_TOKEN+tokenInfo.getAccess_token(),
                HttpMethod.GET, request, String.class);
        String user = response.getBody();
        System.out.println(user);
    }
    
    public static void main(String args[]){
        AuthTokenInfo tokenInfo = sendTokenRequest();
        searchForGivenWordsAndReportCounts(tokenInfo);
         
        getTopWordRankings(tokenInfo);
         
    }

}
