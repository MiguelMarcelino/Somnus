package com.somnus.server.backend.teammembers;

import com.somnus.server.backend.teammembers.dto.TeamMemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/team-member-api")
public class TeamMemberController {

    @Autowired
    public TeamMemberService teamMemberService;

    @GetMapping(value = "/team-members")
    public List<TeamMemberDto> teamMembers() {
        return teamMemberService.getAllTeamMembers();
    }

    @GetMapping(value = "/team-member/{articleId}")
    public TeamMemberDto teamMember(@PathVariable Integer articleId) {
        return teamMemberService.getTeamMember(articleId);
    }

    @PostMapping(value = "/team-member/create")
    public void createTeamMember(@RequestBody TeamMemberDto teamMemberDto) {
        teamMemberService.createTeamMember(teamMemberDto);
    }

    @GetMapping(value = "/team-member/delete/{teamMemberId}")
    public void createTeamMember(@PathVariable Integer teamMemberId) {
        teamMemberService.deleteTeamMember(teamMemberId);
    }
}
