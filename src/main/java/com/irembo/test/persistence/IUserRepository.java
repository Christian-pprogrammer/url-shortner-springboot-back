package com.irembo.test.persistence;

import com.irembo.test.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User,Integer> {

    Boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);


}


