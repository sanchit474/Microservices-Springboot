package com.sb.user.microservices.repositories;

import com.sb.user.microservices.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
