package com.somnus.server.backend.feedback.repository;

import com.somnus.server.backend.feedback.domain.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {



}
