package com.example.cpbot;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApiService {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String CLIST_USERNAME = "YOUR_CLIST_USERNAME";
   private static final String CLIST_API_KEY= "YOUR_API_KEY";
// API URL
    private static final String API_URL= "https://clist.by/api/v4/contest/?upcoming=true&order_by=start&limit=20";
    public List<DataModel> getAllUpcomingContests() {
        try {
            // The Fix: create a disguise for bot
            HttpHeaders headers= new HttpHeaders();
            headers.set("Authorization", "ApiKey " + CLIST_USERNAME + ":" + CLIST_API_KEY);
            HttpEntity<String> entity= new HttpEntity<>(headers);
            // send request wearing the disguise
            ClistResponse response=  restTemplate.exchange(
                    API_URL,
                    HttpMethod.GET,
                    entity,
                    ClistResponse.class
            ).getBody();
            return response != null? response.objects(): List.of();
        } catch (Exception e) {
            // if fails to print actual reason to console instead of hiding it
            System.out.println("API ERROR " + e.getMessage());
            return List.of();
        }
    }

    public List<DataModel> getContestsTomorrow() {
        List<DataModel> allContests = getAllUpcomingContests();
        if (allContests == null|| allContests.isEmpty()) return List.of();
      LocalDateTime tomorrow= LocalDateTime.now(ZoneId.of("UTC")).plusHours(24);

        return allContests.stream()
                .filter(c -> {
                    try {
                        LocalDateTime startTime = LocalDateTime.parse(c.startTime());
                        return startTime.isBefore(tomorrow);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }
}