package com.artisan.springsocialloginjwt.repository;

import com.artisan.springsocialloginjwt.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUserId(final String userId);

}
