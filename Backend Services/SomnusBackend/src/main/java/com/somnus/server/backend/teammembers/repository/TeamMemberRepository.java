package com.somnus.server.backend.teammembers.repository;

import java.util.List;

import com.somnus.server.backend.teammembers.domain.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface TeamMemberRepository extends JpaRepository<TeamMember, Integer> {
    List<TeamMember> findByGithubUsername(String githubUsername);

    boolean existsByGithubUsername(String githubUsername);

}
