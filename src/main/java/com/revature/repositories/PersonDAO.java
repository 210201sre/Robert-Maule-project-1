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
import com.revature.models.Person;
import com.revature.models.SavingsAccount;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class PersonDAO implements IPersonDAO {

	private static final Logger log = LoggerFactory.getLogger(PersonDAO.class);
	@Override
	public int insertEmployee(Employee e, int id) {
			int Employeeid=-1;
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO project0.employee_data (first_name , last_name , city, state,zipcode,street_address, date_hired, salary)"
					+ " VALUES (?,?,?,?,?,?,?,?) RETURNING project0.employee_data.id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, e.getFirstName());
			stmt.setString(2, e.getLastName());
			stmt.setString(3, e.getCity());
			stmt.setString(4, e.getState());
			stmt.setString(5, e.getZipcode());
			stmt.setString(6, e.getStreetAdress());
			stmt.setDate(7, e.getDateHired());
			stmt.setDouble(8, e.getSalary());

			ResultSet rs;

			if ((rs = stmt.executeQuery()) != null) {
				rs.next();
				Employeeid= rs.getInt(1);
			}
			else {
				return -1;
			}
			
			sql= "INSERT INTO project0.employee_data_users_jt (employee_data_id, user_id) VALUES (?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,Employeeid);
			stmt.setInt(2,id);
			
			stmt.executeUpdate();
			
			return Employeeid;
			
			
		} catch (SQLException r) {
			log.error("We failed to insert a new Employee", r);
			return -1;
		}
	}

	@Override
	public int insertCustomer(Customer c, int id) {
		int customerId=-1;
		try (Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "INSERT INTO project0.customer_data (first_name , last_name , city, state,zipcode,street_address, date_joined)"
					+ " VALUES (?,?,?,?,?,?,?) RETURNING project0.customer_data.id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, c.getFirstName());
			stmt.setString(2, c.getLastName());
			stmt.setString(3, c.getCity());
			stmt.setString(4, c.getState());
			stmt.setString(5, c.getZipcode());
			stmt.setString(6, c.getStreetAdress());
			stmt.setDate(7, c.getDateJoined());

			ResultSet rs;

			if ((rs = stmt.executeQuery()) != null) {
				rs.next();
				customerId= rs.getInt(1);
			}
			else {
				return -1;
			}
			
			sql= "INSERT INTO project0.customer_data_users_jt (customer_data_id, user_id) VALUES (?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1,customerId);
			stmt.setInt(2,id);
			
			stmt.executeUpdate();
			
			return customerId;
			
			
		} catch (SQLException r) {
			log.error("We failed to insert a new Employee", r);
			return -1;
		}
	}
	@Override
	public boolean SwitchRole(int id,char c) {
		/*
		 * IUserDAO userDao=new UserDAO(); try (Connection conn =
		 * ConnectionUtil.getConnection()) { PreparedStatement stmt ; if(c=='e') {
		 * 
		 * Employee e=new Employee((Customer)userDao.findUserById(id).getPerson());
		 * this.insertEmployee(e,id);
		 * 
		 * String sql =
		 * "Delete From project0.customer_data_users_jt Where project0.customer_data_users_jt.user_id=? RETURNING project0.customer_data_users_jt.customer_data_id"
		 * ; stmt = conn.prepareStatement(sql);
		 * 
		 * stmt.setInt(1, id);
		 * 
		 * ResultSet rs=stmt.executeQuery(); if(rs.next()) { int
		 * customerid=rs.getInt(1);
		 * 
		 * sql = "Delete From project0.customer_data Where project0.customer_data.id=?";
		 * stmt = conn.prepareStatement(sql);
		 * 
		 * stmt.setInt(1, customerid);
		 * 
		 * stmt.execute(); return true; }
		 * 
		 * } else if(c=='c') { Customer cust=new
		 * Customer((Employee)userDao.findUserById(id).getPerson());
		 * 
		 * this.insertCustomer(cust,id);
		 * 
		 * String sql =
		 * "Delete From project0.employee_data_users_jt Where project0.employee_data_users_jt.user_id=? RETURNING project0.employee_data_users_jt.employee_data_id"
		 * ; stmt = conn.prepareStatement(sql);
		 * 
		 * stmt.setInt(1, id);
		 * 
		 * ResultSet rs=stmt.executeQuery(); if(rs.next()) { int
		 * employeeid=rs.getInt(1);
		 * 
		 * 
		 * sql = "Delete From project0.employee_data Where project0.employee_data.id=?";
		 * stmt = conn.prepareStatement(sql);
		 * 
		 * stmt.setInt(1, employeeid);
		 * 
		 * stmt.execute(); return true; }
		 * 
		 * 
		 * }else return false;
		 * 
		 * } catch (SQLException r) { log.error("failed to switch roles", r); return
		 * false; }
		 */
		return false;
	}
}


