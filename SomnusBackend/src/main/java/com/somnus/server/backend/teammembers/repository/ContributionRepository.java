
package com.somnus.server.backend.teammembers.repository;

import com.somnus.server.backend.teammembers.domain.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ContributionRepository extends JpaRepository<Contribution, Integer> {
}