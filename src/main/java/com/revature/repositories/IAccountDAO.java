package com.revature.repositories;

import java.util.List;

import com.revature.models.Account;
import com.revature.models.CheckingsAccount;
import com.revature.models.SavingsAccount;

public interface IAccountDAO {

	public List<Account> findAll();
	public List<Account>findByUserId(int id);
	public Account findById(int id);
	public int insert(Account a, int id);
	public boolean transfer(int id1,int id2,double amount);
	public List<SavingsAccount> findAllSavings();
	public List<CheckingsAccount> findAllCheckings();
	public boolean grantAccess(int id, int userId); 
	public boolean Close(int id); 
}
