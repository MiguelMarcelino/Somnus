package com.somnus.server.backend.users.repository;

import com.somnus.server.backend.users.domain.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    RoleEntity findByAuthority(String authority);
}
