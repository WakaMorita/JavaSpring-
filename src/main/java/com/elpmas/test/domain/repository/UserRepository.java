package com.elpmas.test.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.elpmas.test.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	User findByUsernameEquals(String username);
}
