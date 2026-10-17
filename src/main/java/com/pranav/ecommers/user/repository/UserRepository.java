package com.pranav.ecommers.user.repository;

import com.pranav.ecommers.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
