package com.revature.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.revature.models.User;
import com.revature.models.Employee;

public interface UserDAO extends JpaRepository<User, Integer> {
	public Optional<User> findByUsername(String username);
}