package ru.otus.service;

import java.util.Scanner;

public class ConsoleServiceImpl implements ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String interString(String label) {
        System.out.println(label);
        return scanner.nextLine();
    }
}
