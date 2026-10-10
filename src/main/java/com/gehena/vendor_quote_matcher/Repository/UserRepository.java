package com.gehena.vendor_quote_matcher.Repository;

import com.gehena.vendor_quote_matcher.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}