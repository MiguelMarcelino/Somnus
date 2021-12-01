package com.somnus.server.backend.teammembers;

import com.somnus.server.backend.teammembers.domain.Contribution;
import com.somnus.server.backend.teammembers.domain.TeamMember;
import com.somnus.server.backend.teammembers.dto.ContributionDto;
import com.somnus.server.backend.teammembers.repository.ContributionRepository;
import com.somnus.server.backend.teammembers.repository.TeamMemberRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContributionService {

    @Autowired
    private ContributionRepository contributionRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Value("${github.auth.token}")
    private String auth_token;

    // A DateTimeFormatter to format the Date attribute from the API
    private static final DateTimeFormatter date_formatter =
            DateTimeFormatter.ofPattern("yyyy'-'MM'-'dd'T'HH':'mm':'ss'Z'");

    @Value("${github.api.url}")
    private static String githubApiUrl;

    // Create a Web Client pointing to the repository
    private static final WebClient webClient =
            WebClient.create("https://api.github.com/repos/MiguelMarcelino/SomnusWebsite");

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

        author.setNumContributions(author.getNumContributions() + 1);
        teamMemberRepository.save(author);

        contributionRepository.save(contribution);
    }

    public void updateContributionRepo(){
        if(contributionRepository.count() == 0){
            initializeContributionRepo();
        }
        else{
            addNewContributions();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////// Private Methods /////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Fetch all the Commits associated to this repository's main
     * branch and return them as Contributions
     * @return - an array containing all contributions
     */
    private void initializeContributionRepo(){
        // Retrieve the Commit information from Github's API
        // and convert it to a String object
        JSONArray data = new JSONArray("[1]");
        for(int page = 1; data.length() != 0; page++){
            final int currPage = page;
            String response = this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/commits")
                            .queryParam("access_token", auth_token)
                            .queryParam("page", currPage)
                            .build())
                    .retrieve().bodyToMono(String.class).block();

            // Parse the response string into a JSON format, in this case an array
            data = new JSONArray(response);
            if(data.length() == 0){
                break;
            }
            // An array to store the new contributions
            ContributionDto[] contributionDtos = new ContributionDto[data.length()];
            populateContributions(contributionDtos, data);

            // One by one, save them into the database
            for(ContributionDto curr_contribution : contributionDtos){
                createContribution(curr_contribution);
            }
        }
    }

    /**
     * Fetch new contributions from repository
     * @return - an array containing new contributions
     */
    private void addNewContributions() {
        Contribution newestContribution = contributionRepository.findFirstByOrderByDateAddedDesc();
        String sinceDate = newestContribution.getDateAdded().plusSeconds(1).format(date_formatter);

        JSONArray data = new JSONArray("[1]");
        for(int page = 1; data.length() != 0; page++){
            final int currPage = page;
            String response = this.webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/commits")
                            .queryParam("since", sinceDate)
                            .queryParam("access_token", auth_token)
                            .queryParam("page", currPage)
                            .build())
                    .retrieve().bodyToMono(String.class).block(); // Get the response and convert it to a String object

            // Parse the response string into a JSON format, in this case an array
            data = new JSONArray(response);
            if(data.length() == 0){
                break;
            }
            // An array to store the new contributions
            ContributionDto[] contributions = new ContributionDto[data.length()];
            populateContributions(contributions, data);

            // One by one, save them into the database
            for(ContributionDto curr_contribution : contributions){
                createContribution(curr_contribution);
            }
        }
    }

    private void populateContributions(ContributionDto[] contributions, JSONArray data) {
        // For each row(commit) in the response, get the author's name, date and Commit message and
        // create a new Contribution with it
        for(int i = 0; i< data.length(); i++){
            JSONObject row = data.getJSONObject(i);
            String rowAuthor = row.getJSONObject("author").getString("login");
            String rowDateStr = row.getJSONObject("commit").getJSONObject("author").getString("date");
            LocalDateTime rowDate =LocalDateTime.parse(rowDateStr, date_formatter);
            String rowDescription = row.getJSONObject("commit").getString("message");
            contributions[i] = new ContributionDto(rowDescription, rowAuthor, rowDate);
        }
    }

}