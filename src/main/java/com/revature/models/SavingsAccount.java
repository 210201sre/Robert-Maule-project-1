package com.revature.models;

public class SavingsAccount extends Account {
	private double intrest;

	public SavingsAccount(int id, double balance, double intrest) {
		super();
		super.id = id;
		super.balance = balance;
		this.intrest = intrest;
	}

	public SavingsAccount() {
		super();
	}
	
	public double getIntrest() {
		return intrest;
	}

	public void setIntrest(double intrest) {
		this.intrest = intrest;
	}

	@Override
	public String toString() {
		return "SavingsAccount [id=" + super.id + ", super.balance=" + balance + ", intrest=" + intrest + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + id;
		temp = Double.doubleToLongBits(intrest);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SavingsAccount other = (SavingsAccount) obj;
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance))
			return false;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(intrest) != Double.doubleToLongBits(other.intrest))
			return false;
		return true;
	}

}
