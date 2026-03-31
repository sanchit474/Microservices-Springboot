package com.sb.microservices.user.repositories;

import com.sb.microservices.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
