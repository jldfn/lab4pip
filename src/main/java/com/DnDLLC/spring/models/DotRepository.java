package com.DnDLLC.spring.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DotRepository extends JpaRepository<Dot, Long> {
    Collection<Dot> findByUserUsername(String username);
}
//jst cument