package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.SavingsAccount;

public interface SavingsAccountDAO extends JpaRepository<SavingsAccount, Integer> {

}
