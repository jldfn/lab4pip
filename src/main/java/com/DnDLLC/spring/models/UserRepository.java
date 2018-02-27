package com.DnDLLC.spring.models;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Collection<User> findByUsername(String username);

    User findDistinctByUsername(String username);
}
