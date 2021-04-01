package com.revature.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Account;
import com.revature.models.CheckingsAccount;
import com.revature.models.Customer;
import com.revature.models.Employee;
import com.revature.models.SavingsAccount;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDAO implements IUserDAO {

	private static final Logger log = LoggerFactory.getLogger(UserDAO.class);

	@Override
	public List<User> findAllCustomers() {
		List<User> allUsers = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();
			String sql = "SELECT project0.users.id, project0.users.username, project0.users.password,"
					+ "project0.accounts.id AS accounts_id, project0.accounts.balance,project0.accounts.savings,project0.accounts.intrest_rate"
					+ ",project0.customer_data.id as customer_id, project0.customer_data.first_name,project0.customer_data.last_name,project0.customer_data.zipcode,project0.customer_data.city,project0.customer_data.state,project0.customer_data.street_address,project0.customer_data.date_joined "
					+ "FROM project0.users LEFT JOIN project0.accounts_users_jt ON project0.users.id = project0.accounts_users_jt.users_id "
					+ "LEFT JOIN project0.accounts ON project0.accounts.id = project0.accounts_users_jt.account_id "
					+ "LEFT JOIN project0.customer_data_users_jt ON project0.users.id = project0.customer_data_users_jt.user_id "
					+ "LEFT JOIN project0.customer_data ON project0.customer_data.id = project0.customer_data_users_jt.customer_data_id "
					+ "WHERE project0.users.employee = 'f'";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				int accountId = rs.getInt("accounts_id");
				double balance = rs.getDouble("balance");
				boolean savings = rs.getBoolean("savings");
				double intrestrate = rs.getDouble("intrest_rate");
				String firstname = rs.getString("first_name");
				String lastname = rs.getString("last_name");
				String zipcode = rs.getString("zipcode");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String streetadress = rs.getString("street_address");
				Date date_joined = rs.getDate("date_joined");
				int custId=rs.getInt("customer_id");

				if (accountId == 0) {
					allUsers.add(new User(id, username, password, new ArrayList<>(), false,
							new Customer(custId,date_joined, firstname, lastname, zipcode, city, streetadress, state)));
				} else {
					Account a;
					if (savings)
						a = new SavingsAccount(accountId, balance, intrestrate);
					else
						a = new CheckingsAccount(accountId, balance);
					List<User> potentialOwners = allUsers.stream().filter((u) -> u.getId() == id)
							.collect(Collectors.toList());
					if (potentialOwners.isEmpty()) {
						List<Account> ownedAccounts = new ArrayList<>();
						ownedAccounts.add(a);
						User u = new User(id, username, password, ownedAccounts, false,
								new Customer(custId,date_joined, firstname, lastname, zipcode, city, streetadress, state));
						allUsers.add(u);
					} else {
						User u = potentialOwners.get(0);
						u.addAccount(a);
					}
				}

			}

		} catch (SQLException e) {
			log.error("failed to find all customers", e);
			return new ArrayList<>();
		}
		log.info("Found all Customers!");
		return allUsers;
	}

	public List<User> findAllEmployees() {
		List<User> allUsers = new ArrayList<>();
		try (Connection conn = ConnectionUtil.getConnection()) {

			Statement stmt = conn.createStatement();

			String sql = "SELECT project0.users.id, project0.users.username, project0.users.password,"
					+ "project0.accounts.id as account_id, project0.accounts.balance,project0.accounts.savings,project0.accounts.intrest_rate"
					+ ",project0.employee_data.id as employeeid, project0.employee_data.first_name,project0.employee_data.last_name,project0.employee_data.zipcode,project0.employee_data.city,project0.employee_data.state,project0.employee_data.street_address,project0.employee_data.date_hired ,project0.employee_data.salary"
					+ " FROM project0.users "
					+ "LEFT JOIN project0.accounts_users_jt ON project0.users.id = project0.accounts_users_jt.users_id "
					+ "LEFT JOIN project0.accounts ON project0.accounts.id = project0.accounts_users_jt.account_id "
					+ "LEFT JOIN project0.employee_data_users_jt ON project0.users.id = project0.employee_data_users_jt.user_id "
					+ "LEFT JOIN project0.employee_data ON project0.employee_data.id = project0.employee_data_users_jt.employee_data_id "
					+ "WHERE project0.users.employee = 't'";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				int accountId = rs.getInt("account_id");
				double balance = rs.getDouble("balance");
				boolean savings = rs.getBoolean("savings");
				double intrestrate = rs.getDouble("intrest_rate");
				String firstname = rs.getString("first_name");
				String lastname = rs.getString("last_name");
				String zipcode = rs.getString("zipcode");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String streetadress = rs.getString("street_address");
				int salary = rs.getInt("salary");
				Date date_hired = rs.getDate("date_hired");
				int empId=rs.getInt("employeeid");
				if (accountId == 0) {
					allUsers.add(new User(id, username, password, new ArrayList<>(), true,
							new Employee(empId, date_hired, salary, firstname, lastname, zipcode, city, streetadress, state)));
				} else {
					Account a;
					if (savings)
						a = new SavingsAccount(accountId, balance, intrestrate);
					else
						a = new CheckingsAccount(accountId, balance);
					List<User> potentialOwners = allUsers.stream().filter((u) -> u.getId() == id)
							.collect(Collectors.toList());
					if (potentialOwners.isEmpty()) {
						List<Account> ownedAccounts = new ArrayList<>();
						ownedAccounts.add(a);
						User u = new User(id, username, password, ownedAccounts, true, new Employee(empId,date_hired, salary,
								firstname, lastname, zipcode, city, streetadress, state));
						allUsers.add(u);
					} else {
						User u = potentialOwners.get(0);
						u.addAccount(a);
					}
				}

			}

		} catch (SQLException e) {
			log.error("failed to find all employees", e);
			return new ArrayList<>();
		}
		log.info("Found all Employees!");
		return allUsers;
	}

	@Override
	public int insert(User u) {

		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "INSERT INTO project0.users (username, password, employee) VALUES (?, ?, ?) RETURNING project0.users.id";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setBoolean(3, u.isEmployee());

			ResultSet rs;

			if ((rs = stmt.executeQuery()) != null) {
				rs.next();
				
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			log.error("We failed to insert a new user", e);
			return -1;
		}
		return -1;
	}

	@Override
	public boolean delete(int userId) {
		try (Connection conn = ConnectionUtil.getConnection()) {

			String sql = "Delete From project0.employee_data_users_jt Where project0.employee_data_users_jt.user_id=? RETURNING project0.employee_data_users_jt.employee_data_id";
			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, userId);

			ResultSet rs=stmt.executeQuery();
			if(rs.next()) {
			int employeeid=rs.getInt(1);
			
			
			sql = "Delete From project0.employee_data Where project0.employee_data.id=?";
		    stmt = conn.prepareStatement(sql);

			stmt.setInt(1, employeeid);

			stmt.execute();
			}
			
			 sql = "Delete From project0.customer_data_users_jt Where project0.customer_data_users_jt.user_id=? RETURNING project0.customer_data_users_jt.customer_data_id";
			 stmt = conn.prepareStatement(sql);

			stmt.setInt(1, userId);

			rs=stmt.executeQuery();
			if(rs.next()) {
			int customerid=rs.getInt(1);
			
			sql = "Delete From project0.customer_data Where project0.customer_data.id=?";
		    stmt = conn.prepareStatement(sql);

			stmt.setInt(1, customerid);

			stmt.execute();
			
			}
			
			sql = "Delete From project0.accounts_users_jt Where project0.accounts_users_jt.users_id=?";
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, userId);

			stmt.execute();

			sql = "Delete From project0.users Where project0.users.id=?";
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, userId);

			stmt.execute();
			log.info("	User all Customers!");
			return true;
		} catch (SQLException e) {
			log.error("We failed to delete the data", e);
			return false;
		}

	}

	@Override
	public User findUserById(int userId) {
		User u = new User();
		try (Connection conn = ConnectionUtil.getConnection()) {
			System.out.println("w\n\n\n\n\n\n\n\n\n");
			String sql = "SELECT project0.users.id, project0.users.username, project0.users.password,project0.users.employee,"
					+ "project0.accounts.id as account_id, project0.accounts.balance,project0.accounts.savings,project0.accounts.intrest_rate"
					+ ",project0.customer_data.id as customer_id, project0.customer_data.first_name,project0.customer_data.last_name,project0.customer_data.zipcode,project0.customer_data.city,project0.customer_data.state,project0.customer_data.street_address,project0.customer_data.date_joined "
					+ ", project0.employee_data.id as employeeid,project0.employee_data.first_name as efirstname,project0.employee_data.last_name as elastname,project0.employee_data.zipcode as ezipcode,project0.employee_data.city as ecity,project0.employee_data.state as estate,project0.employee_data.street_address as estreetaddress,project0.employee_data.date_hired,project0.employee_data.salary"
					+ " FROM project0.users "
					+ "LEFT JOIN project0.accounts_users_jt ON project0.users.id = project0.accounts_users_jt.users_id "
					+ "LEFT JOIN project0.accounts ON project0.accounts.id = project0.accounts_users_jt.account_id "
					+ "LEFT JOIN project0.employee_data_users_jt ON project0.users.id = project0.employee_data_users_jt.user_id "
					+ "LEFT JOIN project0.employee_data ON project0.employee_data.id = project0.employee_data_users_jt.employee_data_id "
					+ "LEFT JOIN project0.customer_data_users_jt ON project0.users.id = project0.customer_data_users_jt.user_id "
					+ "LEFT JOIN project0.customer_data ON project0.customer_data.id = project0.customer_data_users_jt.customer_data_id "
					+ "WHERE project0.users.id=?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int id = rs.getInt("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			if (rs.getBoolean("employee")) {
				String firstname = rs.getString("efirstname");
				String lastname = rs.getString("elastname");
				String zipcode = rs.getString("ezipcode");
				String city = rs.getString("ecity");
				String state = rs.getString("estate");
				String streetadress = rs.getString("estreetaddress");
				double salary = rs.getInt("salary");
				Date date_hired = rs.getDate("date_hired");
				int employeeid=rs.getInt("employeeid");
				u = new User(id, username, password, new ArrayList<>(), true,
						new Employee(employeeid,date_hired, salary, firstname, lastname, zipcode, city, streetadress, state));

			} else {
				String firstname = rs.getString("first_name");
				String lastname = rs.getString("last_name");
				String zipcode = rs.getString("zipcode");
				String city = rs.getString("city");
				String state = rs.getString("state");
				String streetadress = rs.getString("street_address");
				Date date_joined = rs.getDate("date_hired");
				int custId =rs.getInt("customer_id");
				u = new User(id, username, password, new ArrayList<>(), false,
						new Customer(custId,date_joined, firstname, lastname, zipcode, city, streetadress, state));

			}
			int accountId = rs.getInt("account_id");
			double balance = rs.getDouble("balance");
			boolean savings = rs.getBoolean("savings");
			double intrestrate = rs.getDouble("intrest_rate");
			List<Account> ownedAccounts = new ArrayList<>();
			Account a;
			if (savings)
				a = new SavingsAccount(accountId, balance, intrestrate);
			else
				a = new CheckingsAccount(accountId, balance);
			ownedAccounts.add(a);
			while (rs.next()) {
				accountId = rs.getInt("account_id");
				balance = rs.getDouble("balance");
				savings = rs.getBoolean("savings");
				intrestrate = rs.getDouble("intrest_rate");
				if (savings)
					a = new SavingsAccount(accountId, balance, intrestrate);
				else
					a = new CheckingsAccount(accountId, balance);
				ownedAccounts.add(a);
			}
			u.setAccounts(ownedAccounts);

		} catch (SQLException e) {
			log.error("failed to find all employees", e);
			return new User();
		}
		log.info("Found employee "+userId);
		return u;
	}

	@Override
	public boolean paySalary() {
		List<User> allUsers = this.findAllEmployees();
		for (User u : allUsers) {
			if (u.getAccounts().size() > 0) {

				int id = u.getAccounts().get(0).getId();
				double newbalance = u.getAccounts().get(0).getBalance();
				Employee e = (Employee) u.getPerson();
				double salary = e.getSalary();
				newbalance = newbalance + (salary / 24);
				try (Connection conn = ConnectionUtil.getConnection()) {
					String sql = "UPDATE project0.accounts " + "SET balance = ? " + "WHERE" + " project0.accounts.id=?";
					PreparedStatement stmt = conn.prepareStatement(sql);
					stmt.setDouble(1, newbalance);
					stmt.setInt(2, id);
					stmt.executeUpdate();

				} catch (SQLException r) {
					log.error("failed to pay salary", r);
					return false;
				}

			} else
				log.warn("user id " + u.getId() + " didnt have an account to deposit their salary!");
		}
		log.info("Salary Paid ");
		return true;
	}

	@Override
	public List<User> findAllUsers() {
		List<User> allUsers = this.findAllEmployees();
		allUsers.addAll(findAllCustomers());
		log.info("Found all Users! ");
		return allUsers;
	}

}
