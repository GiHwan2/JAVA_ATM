package org.tukorea.atm.bank;

import org.tukorea.atm.helpdesk.CustomerSvc;
import org.tukorea.atm.util.Statistics;

import java.util.Scanner;
import java.util.Random;

public class ATMachine {
	private Account [] accountArray;	// 고객계좌배열 참조변수
	private int machineBalance;			// ATM 잔고
	private int maxAccountNum;			// 고객계좌 참조변수 배열크기 
	private int currentAccountNum = 0; 		// 개설된 고객계좌 수
	private String managerPassword;		// 관리자 비밀번호
	
	public static final int BASE_ACCOUNT_ID = 100; 	// 고객계좌 발급 시 최소 번호 
	// 랜덤으로 계좌 번호 발급
	// 범위 : 100 부터 (계좌 발급시 개설가능한 최대 계좌 수 * 2) 번호 까지 부여
	
	public ATMachine(int size, int balance, String password) {	// 생성자
		this.maxAccountNum = size;
		this.accountArray = new Account[maxAccountNum];
		this.machineBalance = balance;
		this.managerPassword = password;
	}
	public boolean checkNum(int num) {			//난수 중복 검사 
		for(int j=0;j<accountArray.length;j++) {
			if(accountArray[j]==null) {
				return true;
			}
			else if(accountArray[j].getAccountId()==num) {
				return false;
			}
		}
		return true;
	}
	public int randomNum() {					//난수 생성 
		int num;
		Random random = new Random();
		while(true) {	
			num = BASE_ACCOUNT_ID + random.nextInt(maxAccountNum*2);
			if(checkNum(num)==false)
				continue;
			return num;
		}
	}
	public void createAccount() { 			// 계좌 개설
		
		int num;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("---------개설----------");
		System.out.print("이름 입력: ");
		String name = scan.next();
		System.out.print("암호 입력: ");
		String password = scan.next();
		if(maxAccountNum<=currentAccountNum) {
			System.out.println("계좌를 더이상 개설할 수 없습니다.");
			return;
		}
		
		for(int i=0;i<currentAccountNum+1;i++) {
			if(accountArray[i] == null) {				
				num = randomNum();
				accountArray[i] = new Account(num,0,name,password);
				currentAccountNum++;
				System.out.println(name + "님" + num + "번 계좌번호가 정상적으로 개설되었읍니다. 감사합니다.");
				return;
			}
		}
	}
	public void checkMoney() { 				// 계좌 조회
		int id;
		String password;
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("---------조회----------");
		System.out.print("계좌번호 입력: ");
		id = scan.nextInt();
		System.out.print("비밀번호 입력: ");
		password = scan.next();
		for(int i=0;i<currentAccountNum;i++) {
			if(accountArray[i].authenticate(id,password)) {
				System.out.println("계좌 잔액 : " + accountArray[i].getBalance());
				return;
			}
		}
		System.out.println("계좌 조회에 실패하였습니다.");
		
	}
	
	public void depositMoney() {
		int id;
		String password;
		int deposit;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("---------입금----------");
		System.out.print("계좌번호 입력: ");
		id = scan.nextInt();
		System.out.print("비밀번호 입력: ");
		password = scan.next();
		for(int i=0;i<currentAccountNum;i++) {
			if(accountArray[i].authenticate(id,password)) {
				System.out.print("입금액 입력: ");
				deposit = scan.nextInt();
				System.out.println("입금 후 잔액 : " + accountArray[i].deposit(deposit));
				this.machineBalance += deposit;
				return;
			}
		}
		System.out.println("입금 실패하였습니다.");
	}
	
	public void widrawMoney() {
		int id;
		String password;
		int widraw;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("---------출금----------");
		System.out.print("계좌번호 입력: ");
		id = scan.nextInt();
		System.out.print("비밀번호 입력: ");
		password = scan.next();
		for(int i=0;i<currentAccountNum;i++) {
			if(accountArray[i].authenticate(id,password)) {
				System.out.print("출금액 입력: ");
				widraw = scan.nextInt();
				if(accountArray[i].getBalance()<widraw) {
					System.out.println("잔액이 부족합니다.");
					return;
				}
				if(this.machineBalance<widraw) {
					System.out.println("ATM 현금 잔고가 부족합니다.");
					return;
				}
				this.machineBalance -= widraw;
				System.out.println("출금 후 잔액 : " + accountArray[i].widraw(widraw));
				return;
			}
		}
		System.out.println("출금 실패하였습니다.");
	}
	public void transfer() {
		int id;
		String password;
		int transfer;
		int transferId;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("---------이체----------");
		System.out.print("계좌번호 입력: ");
		id = scan.nextInt();
		System.out.print("비밀번호 입력: ");
		password = scan.next();
		
		for(int i=0;i<currentAccountNum;i++) {
			if(accountArray[i].authenticate(id,password)) {
				System.out.print("이체계좌 입력: ");
				transferId = scan.nextInt();
				System.out.print("이체금액 입력: ");
				transfer = scan.nextInt();
				
				if(accountArray[i].getBalance()<transfer) {
					System.out.println("잔액이 부족합니다.");
					return;
				}
				
				for(int j=0;j<currentAccountNum;j++) {
					if(accountArray[j].getAccountId()==transferId) {
						System.out.println("이체 후 잔액 : " + accountArray[i].widraw(transfer));
						accountArray[j].deposit(transfer);
						System.out.println("계좌 이체를 완료하였습니다.");
						return;
					}
				}
				System.out.println("이체 계좌를 다시 확인하세요.");
				return;
			}
			
		}
		System.out.println("계좌 이체를 실패하였습니다.");
	}
	
	public void requestSvc() {
		CustomerSvc center = new CustomerSvc(accountArray, currentAccountNum);
		center.updatePasswdReq();
		
	}
	public void managerMode() {
		Scanner scan = new Scanner(System.in);
		String password;
		System.out.println("---------고객관리----------");
		System.out.print("관리자 비밀번호 입력: ");
		password = scan.next();
		if(this.managerPassword.equals(password)) {
			System.out.println("ATM 현금 잔고: "+ this.machineBalance);
			System.out.println("고객 잔고 총액: "+Statistics.sum(accountArray,currentAccountNum)+"원("+currentAccountNum+"명)");
			System.out.println("고객 잔고 평균: "+(int)Statistics.average(accountArray,currentAccountNum)+"원");
			System.out.println("고객 잔고 최고: "+Statistics.max(accountArray,currentAccountNum)+"원");
			System.out.println("고객 계좌 현황(고객 잔고 내림차순 정렬)");
			accountArray = Statistics.sort(accountArray,currentAccountNum);
			for(int i=0;i<currentAccountNum;i++) {
				System.out.println(accountArray[i].getAccountName() + "    "+accountArray[i].getAccountId()+"\t"+accountArray[i].getBalance()+"원");
			}
		}
	}
	public void displayMenu() {				// 메인 메뉴 표시
		System.out.println("----------------------");
		System.out.println("-    TUKOREA BANK    -");
		System.out.println("----------------------");
		System.out.println("1. 계좌 개설");
		System.out.println("2. 계좌 조회");
		System.out.println("3. 계좌 입금");
		System.out.println("4. 계좌 출금");
		System.out.println("5. 계좌 이체");
		System.out.println("7. 고객 센터");
		System.out.println("8. 고객 관리");
		System.out.println("9. 업무 종료");
	}
}
