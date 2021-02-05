package com.somnus.server.backend.users.repository;

import com.somnus.server.backend.users.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {

    // @Query("SELECT u FROM User u WHERE u.username = :username")
    User findByUserName(String username);
}
