package com.softwarehouse.serviceorder.security.repository;

import com.softwarehouse.serviceorder.security.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
