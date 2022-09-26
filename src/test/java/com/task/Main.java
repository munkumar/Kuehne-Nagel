package com.task;

import com.task.controlller.CryptoCurrencyRestClientImpl;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("please enter currency ");
        Scanner in = new Scanner(System.in);

        String curr = in.nextLine();
        System.out.println("You entered string " + curr);
        System.out.println("------------------------------");
        System.out.println("");
        CryptoCurrencyRestClientImpl cryptoCurrencyRestClientImpl = new CryptoCurrencyRestClientImpl();
        cryptoCurrencyRestClientImpl.getBitcoinRateInformation(curr);
    }
}
