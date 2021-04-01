package com.revature.models;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

	private static final long serialVersionUID = 7382850360365866259L;

	private int id;
	private String username;
	private String password;
	private boolean employee;
	private List<Account> accounts;
	private Person person;

	public User() {
		super();
	}

	public User(int id, String username, String password, List<Account> accounts, boolean employee, Person person) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.accounts = accounts;
		this.employee = employee;
		this.person=person;
	}
	

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public boolean isEmployee() {
		return employee;
	}

	public void setEmployee(boolean employee) {
		this.employee = employee;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	public boolean removeAccount(Account account) {
		return this.accounts.remove(account);
	}

	@Override
	public int hashCode() {
		return Objects.hash(accounts, id, password, employee, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(accounts, other.accounts) && id == other.id && Objects.equals(password, other.password)
				&& employee == other.employee && Objects.equals(username, other.username);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", employee=" + employee + ", accounts="
				+ accounts + "Details=" + person+ "]";
	}
}
