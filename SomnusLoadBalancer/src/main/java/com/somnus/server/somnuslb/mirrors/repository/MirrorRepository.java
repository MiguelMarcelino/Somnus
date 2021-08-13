package com.somnus.server.somnuslb.mirrors.repository;

import com.somnus.server.somnuslb.mirrors.domain.Mirror;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface MirrorRepository extends JpaRepository<Mirror, Integer> {

    @Query("SELECT m FROM Mirror m WHERE m.ipAddress = :ipAddress")
    Optional<Mirror> getMirrorWithIpAddress(String ipAddress);
}
