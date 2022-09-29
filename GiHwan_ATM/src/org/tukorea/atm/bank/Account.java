package org.tukorea.atm.bank;

public class Account {
	private int accountId;	// 계좌 번호
	private int balance;	// 계좌 잔고
	private String accountName;	// 고객 명
	private String password;	// 계좌 비밀번호
	
	public Account(int id, int money, String name, String pwd) { // 생성자 
		this.accountId = id;
		this.balance = money;
		this.accountName = name;
		this.password = pwd;
	}

	boolean authenticate(int id, String passwd) { // 계정 확인
		if((this.accountId == id)&&(this.password.equals(passwd)))
			return true;
		return false;
	}
	public int getAccountId() {
		return accountId; 
	}
	public int getBalance() {
		return balance; 
	}
	
	public int deposit(int money) {
		this.balance += money;
		return balance;
	}
	public int widraw(int money) {
		this.balance -= money;
		return balance;
	}
	public boolean updatePasswd(String oldPasswd, String newPasswd) {
		if(this.password.equals(oldPasswd)) {
			this.password = newPasswd;
			return true;
		}
		return false;
	}
	public String getAccountName() {
		return accountName;
	}
}
