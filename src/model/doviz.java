package model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class doviz {

    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/USD";  // API URL

    
    public double getUsdToTryRate() throws Exception {
        return getRate("TRY"); 
    }

   
    public double getEurToUsdRate() throws Exception {
        return getRate("EUR");
    }

   
    public double getUsdToTryRateFromAPI() throws Exception {
        return getRate("TRY"); 
    }

   
    private double getRate(String currencyCode) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(response.body());

        JsonNode rates = jsonNode.get("rates");

        
        if (rates.has(currencyCode)) {
            return rates.get(currencyCode).asDouble();
        } else {
            throw new Exception("Currency not found in response: " + currencyCode);
        }
    }

    
    public double getEurToTryRate() throws Exception {
        double eurToUsd = getEurToUsdRate();  // Euro'yu USD'ye çevir
        double usdToTry = getUsdToTryRate();  // USD'yi TL'ye çevir
        return eurToUsd * usdToTry;  // Euro'yu TL'ye çevir
    }

    
    public String getUsdToTryRateText() throws Exception {
        double rate = getUsdToTryRate();
        return "1 USD = " + rate + " TRY"; 
    }

    
    public String getEurToTryRateText() throws Exception {
        double rate = getEurToTryRate();
        return "1 EUR = " + rate + " TRY"; 
    }
}
