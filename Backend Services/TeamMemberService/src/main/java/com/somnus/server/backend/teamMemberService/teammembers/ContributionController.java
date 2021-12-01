package com.somnus.server.backend.teamMemberService.teammembers;

import com.somnus.server.backend.teamMemberService.teammembers.dto.ContributionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contributions-api")
public class ContributionController{

    @Autowired
    private ContributionService contributionService;

    @GetMapping(value = "/contributions")
    public List<ContributionDto> getAllContributions(){
        return contributionService.getAllContributions();
    }

}