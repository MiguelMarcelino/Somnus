package com.somnus.server.backend.teamMemberService.teammembers.repository;

import com.somnus.server.backend.teamMemberService.teammembers.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
    List<TeamMember> findByGithubUsername(String githubUsername);

    boolean existsByGithubUsername(String githubUsername);

}
