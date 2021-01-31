package com.somnus.server.backend.teammembers;

import com.somnus.server.backend.teammembers.database.TeamMemberDatabaseConnection;
import com.somnus.server.backend.teammembers.domain.TeamMember;
import com.somnus.server.backend.teammembers.dto.TeamMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberDatabaseConnection teamMemberDatabaseConnection;

    public List<TeamMemberDto> getAllTeamMembers() {
        List<TeamMemberDto> teamMemberDtos = new ArrayList<>();
        for(TeamMember teamMember : teamMemberDatabaseConnection.getAllObject()) {
            teamMemberDtos.add(new TeamMemberDto());
        }
        return teamMemberDtos;
    }

    public TeamMemberDto getTeamMember(String id) {
        TeamMember teamMember = teamMemberDatabaseConnection.getObject(id);
        return new TeamMemberDto(teamMember.getTeamMemberName(), teamMember.getPhotoPath(),
                teamMember.getDateJoined());
    }

    public void createTeamMember(TeamMemberDto teamMemberDto) {
        teamMemberDatabaseConnection.createObject(new TeamMember(teamMemberDto.getTeamMemberName(),
                teamMemberDto.getPhotoPath()));
    }

    public void deleteTeamMember(String teamMemberId) {
        teamMemberDatabaseConnection.deleteObject(teamMemberId);
    }
}
