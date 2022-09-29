package org.tukorea.atm.util;
import org.tukorea.atm.bank.Account;

public class Statistics {
	public static int sum(Account [] account, int size) {
		int sum = 0;
		for(int i=0;i<size;i++) {
			if(account[i] == null)
				break;
			sum+=account[i].getBalance();
		}
		return sum;
	}
	public static double average(Account [] account, int size) {
		int avg;
		avg = sum(account,size)/size;
		return avg;
	}
	public static int max(Account [] account, int size) {
		int max = account[0].getBalance();
		for(int i=0;i<size;i++) {
			if(max<account[i].getBalance())
				max = account[i].getBalance();
		}
		return max;
	}
	public static Account [] sort(Account [] account, int size) {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < (size - 1) - i; j++) {
				if (account[j].getBalance() < account[j + 1].getBalance()) {	// 버블 정렬 사용
					Account temp = account[j];
					account[j] = account[j + 1];
					account[j + 1] = temp;
				}
			}
		}
		return account;
	}
}
