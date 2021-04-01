
	package com.revature.services;

	import java.util.List;

	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;
	import org.slf4j.MDC;

import com.revature.models.Account;
import com.revature.models.CheckingsAccount;
import com.revature.models.SavingsAccount;
import com.revature.models.User;
import com.revature.repositories.AccountDAO;
import com.revature.repositories.IAccountDAO;
import com.revature.repositories.IUserDAO;
	import com.revature.repositories.UserDAO;

	public class AccountService {

		private IAccountDAO accountDAO = new AccountDAO();
		
		private static final Logger log = LoggerFactory.getLogger(AccountService.class);
		
		public Account insert(Account a,int id) {
			
			MDC.put("event", "Register");
			
			log.info("inserting new account");
			
			int generatedId = accountDAO.insert(a,id);
			
			if(generatedId != -1 && generatedId != a.getId()) {
				a.setId(generatedId);
			} else {
				log.error("insert was not done correctly");
			}
			MDC.put("AccountID", Integer.toString(a.getId()));
			log.info("Successfully inserted account");
			
			return a;
		}
		public List<Account> findAll() {
			return accountDAO.findAll();
		}
		public List<SavingsAccount> findAllSavings() {
			return accountDAO.findAllSavings();
		}
		public List<CheckingsAccount> findAllCheckings() {
			return accountDAO.findAllCheckings();
		}
		public Account findAccountById(int id) {
			return accountDAO.findById(id);
		}
		public boolean Transfer(int id1,int id2,double amount) {
			return accountDAO.transfer(id1,id2,amount);
		}
		public List<Account> 	findAccountByUserId(int id) {
			return accountDAO.findByUserId(id);
		}
		public boolean grantAccess(int id, int userId) {
			return accountDAO.grantAccess(id, userId);
		}
		public boolean Close(int id) {
			return accountDAO.Close(id); 
		}

	}
