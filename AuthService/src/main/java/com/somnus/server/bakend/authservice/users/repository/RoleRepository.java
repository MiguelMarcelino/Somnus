package com.somnus.server.bakend.authservice.users.repository;

import com.somnus.server.bakend.authservice.users.domain.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByAuthority(String authority);
}
