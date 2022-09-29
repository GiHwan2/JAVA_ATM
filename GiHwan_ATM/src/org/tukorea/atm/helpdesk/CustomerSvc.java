package org.tukorea.atm.helpdesk;
import java.util.Scanner;

import org.tukorea.atm.bank.Account;

public class CustomerSvc {
	private Account [] accountArray;
	private int currentAccountNum;
	
	public CustomerSvc(Account [] acctArray, int currentAcctNum) {
		this.accountArray = acctArray;
		this.currentAccountNum = currentAcctNum;
	}
	
	public void updatePasswdReq() {
		int id;
		String password;
		String newPassword;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("---------암호변경----------");
		System.out.print("계좌번호 입력: ");
		id = scan.nextInt();
		System.out.print("기존 비밀번호 입력: ");
		password = scan.next();
		System.out.print("신규 비밀번호 입력: ");
		newPassword = scan.next();
		
		for(int i=0;i<currentAccountNum;i++) {
			if(accountArray[i].getAccountId()==id) {
				if(accountArray[i].updatePasswd(password, newPassword)) {
					System.out.println("비밀번호를 수정하였습니다.");
					return;
				}
				else {
					System.out.println("기존 비밀번호가 틀렸습니다.");
					return;
				}
			}
		}
		System.out.println("암호변경을 실패하였습니다.");
	}
}
