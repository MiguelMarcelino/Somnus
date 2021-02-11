package com.somnus.server.backend.teammembers;

import org.springframework.beans.factory.annotation.Value;
import com.somnus.server.backend.teammembers.domain.Contribution;
import com.somnus.server.backend.teammembers.domain.TeamMember;
import com.somnus.server.backend.teammembers.dto.ContributionDto;
import com.somnus.server.backend.teammembers.repository.ContributionRepository;
import com.somnus.server.backend.teammembers.repository.TeamMemberRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Value("${github.auth.token}")
    private String auth_token;

    private WebClient webClient;

    // Fetch all the Commits associated to this repository's main branch and return them as Contributions
    public ContributionDto[] fetchContributions(){

        // Create a Web Client pointing to the repository
        this.webClient = WebClient.create("https://api.github.com/repos/MiguelMarcelino/SomnusWebsite/");
        // this.webClient.get()
        //         .uri(uriBuilder -> uriBuilder
        //         .path("/commits")
        //         .header("Authorization", auth_token)
        //         .build());

        // Retrieve the Commit information from Github's API
        String response = this.webClient.get()  
            .uri(uriBuilder -> uriBuilder // Build the URL for the GET, passing the Authorization Token
                .path("/commits")
                .queryParam("access_token", auth_token)
                .build())
            .retrieve().bodyToMono(String.class).log().block(); // Get the response and convert it to a String object

        // Parse the response string into a JSON format, in this case an array
        JSONArray data = new JSONArray(response); 
        // An array to store the new contributions
        ContributionDto[] contributions = new ContributionDto[data.length()];

        // A DateTimeFormatter to format the Date attribute from the API
        DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'");

        // For each row(commit) in the response, get the author's name, date and Commit message and
        // create a new Contribution with it
        for(int i = 0; i< data.length(); i++){
            JSONObject row = data.getJSONObject(i);
            String rowAuthor = row.getJSONObject("commit").getJSONObject("author").getString("name");
            String rowDateStr = row.getJSONObject("commit").getJSONObject("author").getString("date");
            LocalDateTime rowDate =LocalDateTime.parse(rowDateStr, date_formatter);
            String rowDescription = row.getJSONObject("commit").getString("message");
            contributions[i] = new ContributionDto(rowDescription, rowAuthor, rowDate);
        }

        return contributions;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<ContributionDto> getAllContributions() {
        List<ContributionDto> contributionDtos = new ArrayList<>();
        contributionRepository.findAll().forEach(contribution -> contributionDtos.add(
                new ContributionDto(
                        contribution.getTitle(),
                        contribution.getAuthor(),
                        contribution.getDateAdded()
                        )));
        return contributionDtos;
    }
    
    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createContribution(ContributionDto contributionDto) {
        String authorName = contributionDto.getAuthor();
        TeamMember author;

        // Try to find the Teammember who made the contribution
        if(teamMemberRepository.existsByGithubUsername(authorName)){
            // If succesful, assign him to the object
            author = teamMemberRepository.findByGithubUsername(authorName).get(0);
        }
        else{
            // If not, create a new one
            author = teamMemberRepository.save(new TeamMember(authorName));
        }
        
        // Insert the contribution into the DB
        Contribution contribution = new Contribution(
            contributionDto.getTitle(),
            contributionDto.getAuthor(),
            contributionDto.getDateAdded(),
            author
        );
        contributionRepository.save(contribution);
    }

    // Method to get all contributions from github and store them in DB
    private void initializeContributionRepo(){
        // Fetch all the contributions
        ContributionDto[] contributionDtos = fetchContributions();

        // One by one, save them into the database
        for(ContributionDto curr_contribution : contributionDtos){
            createContribution(curr_contribution);
        }
    }

    public void updateContributionRepo(){
        if(contributionRepository.count() == 0){
            initializeContributionRepo();
        }
        else{
            addNewContributions();
        }
    }

    public ContributionDto[] addNewContributions() {
        Contribution newestContribution = contributionRepository.findFirstByOrderByDateAddedDesc();
        DateTimeFormatter date_formatter = DateTimeFormatter.ofPattern("yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'");
        String sinceDate = newestContribution.getDateAdded().plusSeconds(1).format(date_formatter);
        // Create a Web Client pointing to the repository
        this.webClient = WebClient.create("https://api.github.com/repos/MiguelMarcelino/SomnusWebsite");
        String response = this.webClient.get()
                 .uri(uriBuilder -> uriBuilder
                    .path("/commits")
                    .queryParam("since", sinceDate)
                    .queryParam("access_token", auth_token)
                    .build())
                // .header("since", "1999-01-01T00:00:00Z")
            .retrieve().bodyToMono(String.class).log().block(); // Get the response and convert it to a String object

        // Parse the response string into a JSON format, in this case an array
        JSONArray data = new JSONArray(response); 
        // An array to store the new contributions
        ContributionDto[] contributions = new ContributionDto[data.length()];

        // For each row(commit) in the response, get the author's name, date and Commit message and
        // create a new Contribution with it
        for(int i = 0; i< data.length(); i++){
            JSONObject row = data.getJSONObject(i);
            String rowAuthor = row.getJSONObject("commit").getJSONObject("author").getString("name");
            String rowDateStr = row.getJSONObject("commit").getJSONObject("author").getString("date");
            LocalDateTime rowDate =LocalDateTime.parse(rowDateStr, date_formatter);
            String rowDescription = row.getJSONObject("commit").getString("message");
            contributions[i] = new ContributionDto(rowDescription, rowAuthor, rowDate);
        }

        return contributions;
    }


}