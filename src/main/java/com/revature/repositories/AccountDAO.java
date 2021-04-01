package com.revature.repositories;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.revature.models.Account;
import com.revature.models.CheckingsAccount;
import com.revature.models.SavingsAccount;
import com.revature.util.ConnectionUtil;

public class AccountDAO implements IAccountDAO {

	private static final Logger log = LoggerFactory.getLogger(UserDAO.class);

	@Override
	public List<Account> findAll() {
		List<Account> allAccounts = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();
			String sql = "SELECT project0.accounts.id, project0.accounts.balance,project0.accounts.savings,project0.accounts.intrest_rate "
					+ "FROM project0.accounts";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				boolean savings = rs.getBoolean("savings");
				double intrestrate = rs.getDouble("intrest_rate");
				Account a;
				if (savings)
					a = new SavingsAccount(id, balance, intrestrate);
				else
					a = new CheckingsAccount(id, balance);

				allAccounts.add(a);
			}

		} catch (SQLException e) {
			log.error("failed to find all Accounts", e);
			return new ArrayList<>();
		}
		return allAccounts;
	}
	@Override
	public List<CheckingsAccount> findAllCheckings() {
		List<CheckingsAccount> allAccounts = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();
			String sql = "SELECT project0.accounts.id, project0.accounts.balance,project0.accounts.savings,project0.accounts.intrest_rate "
					+ "FROM project0.accounts WHERE project0.accounts.savings='f'";

			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				CheckingsAccount a = new CheckingsAccount(id, balance);
				allAccounts.add(a);
			}

		} catch (SQLException e) {
			log.error("failed to find all Accounts", e);
			return new ArrayList<>();
		}
		log.info("found all checkings accounts");
		return allAccounts;
	}
	@Override
	public List<SavingsAccount> findAllSavings() {
		List<SavingsAccount> allAccounts = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();
			String sql = "SELECT project0.accounts.id, project0.accounts.balance,project0.accounts.savings,project0.accounts.intrest_rate "
					+ "FROM project0.accounts WHERE project0.accounts.savings='t'";

			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				double intrestrate = rs.getDouble("intrest_rate");
				SavingsAccount a = new SavingsAccount(id, balance, intrestrate);
				allAccounts.add(a);
			}

		} catch (SQLException e) {
			log.error("failed to find all Savings Accounts", e);
			return new ArrayList<>();
		}
		log.info("found all savings accounts");
		return allAccounts;
	}
	@Override
	public Account findById(int id) {
		Account a;
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "SELECT project0.accounts.id, project0.accounts.balance,project0.accounts.savings,project0.accounts.intrest_rate "
					+ "FROM project0.accounts Where project0.accounts.id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			double balance = rs.getDouble("balance");
			boolean savings = rs.getBoolean("savings");
			double intrestrate = rs.getDouble("intrest_rate");
			if (savings)
				a = new SavingsAccount(id, balance, intrestrate);
			else
				a = new CheckingsAccount(id, balance);
			log.info("found accounts belonging to  +id");
			return a;
		
		} catch (SQLException e) {
			log.error("failed to find all Accounts", e);
			return null;
		}
	}
	
	@Override
	public boolean grantAccess(int id, int userId) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO project0.accounts_users_jt (users_id, account_id) VALUES (?,?)";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(2, id);
			stmt.setInt(1, userId);
			stmt.executeUpdate();
			log.info("granted access to  "+userId+" for account"+id);
			return true;
		} catch (SQLException e) {
			log.error("failed to find all Accounts", e);
			return false;
		}
	}


	@Override
	public int insert(Account a, int id) {
		int accountId = -1;
		boolean savings;
		double intrest = 0;
		if (a instanceof SavingsAccount) {
			savings = true;
			SavingsAccount sa = (SavingsAccount) a;
			intrest = sa.getIntrest();
		} else
			savings = false;
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO project0.accounts (balance,intrest_rate,savings) values (?,?,?)"
					+ " RETURNING project0.accounts.id";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setDouble(1, a.getBalance());
			stmt.setDouble(2, intrest);
			stmt.setBoolean(3, savings);
			ResultSet rs;

			if ((rs = stmt.executeQuery()) != null) {
				rs.next();
				accountId = rs.getInt(1);
			} else {
				return -1;
			}

			sql = "INSERT INTO project0.accounts_users_jt (users_id, account_id) VALUES (?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setInt(2, accountId);

			stmt.executeUpdate();
			
			return accountId;

		} catch (SQLException r) {
			log.error("We failed to insert a new Account", r);
			return -1;
		}
	}

	@Override
	public boolean transfer(int id1, int id2,double amount) {
		Account a=findById(id1);
		Account b=findById(id2);
		if(a.getBalance()<=amount)
		{
			log.warn("Account "+id1+" did not have enough funds to complete the transfer");
			return false;
		}
		double aBalance=a.getBalance()-amount;
		double bBalance=b.getBalance()+amount;
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "UPDATE project0.accounts " + "SET balance = ? " + "WHERE" + " project0.accounts.id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, aBalance);
			stmt.setInt(2, id1);
			stmt.executeUpdate();
			sql = "UPDATE project0.accounts " + "SET balance = ? " + "WHERE" + " project0.accounts.id=?";
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, bBalance);
			stmt.setInt(2, id2);
			stmt.executeUpdate();
			log.info("transfered "+amount+" from account"+id1+" to account "+id2);
			return true;
		} catch (SQLException r) {
			log.error("failed to complete the transfer", r);
			return false;
		}
	}
	@Override
	public List<Account> findByUserId(int id) {
		List<Account> allAccounts = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {
			String sql = "SELECT project0.accounts.id, project0.accounts.balance,project0.accounts.savings,project0.accounts.intrest_rate "
					+ "FROM project0.accounts"
					+ " LEFT JOIN project0.accounts_users_jt ON project0.accounts.id = project0.accounts_users_jt.account_id "
					+ "LEFT JOIN project0.users ON project0.users.id = project0.accounts_users_jt.users_id "
					+ "Where project0.users.id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int accountid = rs.getInt("id");
				double balance = rs.getDouble("balance");
				boolean savings = rs.getBoolean("savings");
				double intrestrate = rs.getDouble("intrest_rate");
				Account a;
				if (savings)
					a = new SavingsAccount(accountid, balance, intrestrate);
				else
					a = new CheckingsAccount(accountid, balance);

				allAccounts.add(a);
			}

		} catch (SQLException e) {
			log.error("failed to find all Accounts", e);
			return new ArrayList<>();
		}
		log.info("found all accounts belonging to  "+id);
		return allAccounts;
	}
	
	public boolean Close(int id) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			
			String sql = "Delete From project0.accounts_users_jt Where project0.accounts_users_jt.account_id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1,id);
			stmt.executeUpdate();
			
			 sql = "Delete From project0.accounts Where project0.accounts.id=?";
			 stmt = conn.prepareStatement(sql);

			stmt.setInt(1,id);
			stmt.executeUpdate();
			
			
			log.info("closed account "+id);
			return true;
		} catch (SQLException e) {
			log.error("We failed to delete the data", e);
			return false;
		}
	}

}
