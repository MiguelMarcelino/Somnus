package com.somnus.server.backend.teammembers;

import com.somnus.server.backend.teammembers.dto.ContributionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/newcontributions")
    public ContributionDto[] addNewContributions(){
        return contributionService.addNewContributions();
    }




}