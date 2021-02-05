package com.somnus.server.backend.teammembers;

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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContributionService {

    public List<ContributionDto> getAllContributions(){
        List<ContributionDto> contributionDtos = new ArrayList<>();
        return contributionDtos;
    }

}