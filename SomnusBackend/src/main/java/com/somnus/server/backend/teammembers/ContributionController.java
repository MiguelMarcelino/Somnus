package com.somnus.server.backend.teammembers;

import com.somnus.server.backend.teammembers.dto.ContributionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/contributions-api")
public class ContributionController{
    @Autowired
    private ContributionService contributionService;

    @GetMapping(value = "/contributions")
    public String[] getAllContributions(){
        return contributionService.getAllContributions();
    }

}