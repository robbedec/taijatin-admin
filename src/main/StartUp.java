package main;

import cui.ConsoleMenu;
import domain.DomainController;

import java.util.Scanner;

public class StartUp {
    public static void main(String[] args) {
        DomainController dc = new DomainController();
        Scanner input = new Scanner(System.in);
        ConsoleMenu menu = new ConsoleMenu(dc, input);
    }
}