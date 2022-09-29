package org.tukorea.atm.main;
import org.tukorea.atm.bank.ATMachine;
import java.util.Scanner;
import java.util.InputMismatchException;

public class AtmMain {

	public static void main(String[] args) {
		// 초기 설정 - 개설가능한 최대계좌수(1000계좌),
		// ATM 잔금(50만원), 관리자 암호
		ATMachine atm = new ATMachine(1000, 500000, "admin");
		
		Scanner scan = new Scanner(System.in);
		
		while(true) {
			atm.displayMenu();
			System.out.print(" 메뉴를 선택하세요 >> " ); 
			try {
				int select = scan.nextInt(); 
				switch(select) {
					case 1: // 계좌 개설 
						atm.createAccount();
						break;
					case 2:
						atm.checkMoney();
						break;
					case 3:
						atm.depositMoney();
						break;
					case 4:
						atm.widrawMoney();
						break;
					case 5:
						atm.transfer();
						break;
					case 7:
						atm.requestSvc();
						break;
					case 8:
						atm.managerMode();
						break;
					case 9:
						System.out.print("안녕히 가세요 !");
						System.exit(1);
						
				}
			} catch (InputMismatchException e) {
				System.out.println(" 정확하게 입력해주세요.");
				continue;
			}
		}
	}

}
