package com.somnus.server.backend.teammembers;

import org.springframework.beans.factory.annotation.Value;
import com.somnus.server.backend.teammembers.domain.Contribution;
import com.somnus.server.backend.teammembers.dto.ContributionDto;
import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import org.json.*;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ContributionService {

    @Value("${github.auth.token}")
    private String auth_token;

    private WebClient webClient;

    public String[] getAllContributions(){
        List<ContributionDto> contributionDtos = new LinkedList<>();
        WebClient webClient = WebClient.create("https://api.github.com/repos/MiguelMarcelino/SomnusWebsite/");

        String response = webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/commits")
                .queryParam("access_token", auth_token)
                .build())
            .retrieve().bodyToMono(String.class).log().block();

        JSONArray data = new JSONArray(response);

        String[] responseString = new String[data.length()];

        for(int i = 0; i< data.length(); i++){
            JSONObject row = data.getJSONObject(i);
            responseString[i] = row.getJSONObject("commit").getJSONObject("author").getString("name");
        }
        return responseString;
    }

}