package com.somnus.server.backend.teammembers.repository;

import java.time.LocalDateTime;

import com.somnus.server.backend.teammembers.domain.Contribution;
import com.somnus.server.backend.teammembers.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ContributionRepository extends JpaRepository<Contribution, Integer> {
    Contribution findFirstByOrderByDateAddedDesc();

    Contribution findFirstByTeamMemberOrderByDateAddedAsc(TeamMember teamMember);
}