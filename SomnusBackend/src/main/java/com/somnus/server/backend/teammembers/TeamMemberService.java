package com.somnus.server.backend.teammembers;

import com.somnus.server.backend.exceptions.ErrorMessage;
import com.somnus.server.backend.exceptions.SomnusException;
import com.somnus.server.backend.teammembers.database.ContributionRepository;
import com.somnus.server.backend.teammembers.database.TeamMemberRepository;
import com.somnus.server.backend.teammembers.domain.Contribution;
import com.somnus.server.backend.teammembers.domain.TeamMember;
import com.somnus.server.backend.teammembers.dto.ContributionDto;
import com.somnus.server.backend.teammembers.dto.TeamMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private ContributionRepository contributionRepository;

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public List<TeamMemberDto> getAllTeamMembers() {
        List<TeamMemberDto> teamMemberDtos = new ArrayList<>();
        for(TeamMember teamMember : teamMemberRepository.findAll()) {
            teamMemberDtos.add(new TeamMemberDto(teamMember.getTeamMemberName(), teamMember.getPhotoPath(),
                    teamMember.getDateJoined(), teamMember.getNumContributions()));
        }
        return teamMemberDtos;
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TeamMemberDto getTeamMember(Integer id) {
        Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(id);
        if(optionalTeamMember == null || !optionalTeamMember.isPresent()) {
            throw new SomnusException(ErrorMessage.NO_TEAMMEMBER_FOUND);
        }

        TeamMember teamMember = optionalTeamMember.get();
        return new TeamMemberDto(teamMember.getTeamMemberName(), teamMember.getPhotoPath(),
                teamMember.getDateJoined(), teamMember.getNumContributions());
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void createTeamMember(TeamMemberDto teamMemberDto) {
        teamMemberRepository.save(new TeamMember(teamMemberDto.getTeamMemberName(),
                teamMemberDto.getPhotoPath()));
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteTeamMember(Integer teamMemberId) {
        teamMemberRepository.deleteById(teamMemberId);
    }

    @Retryable(
            value = {SQLException.class},
            backoff = @Backoff(delay = 5000))
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void addContribution(ContributionDto contributionDto) {
        Optional<TeamMember> optionalTeamMember = teamMemberRepository.findById(contributionDto.getTeamMemberId());
        if(!optionalTeamMember.isPresent()) {
            throw new SomnusException(ErrorMessage.NO_TEAMMEMBER_FOUND);
        }

        TeamMember teamMember = optionalTeamMember.get();
        contributionRepository.save(new Contribution(contributionDto.getTitle(), teamMember,
                contributionDto.getDescription()));

        teamMember.addContribution();
        teamMemberRepository.save(teamMember);
    }
}
