package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        UserHandler userHandler = new UserHandler();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Keans delivery service");
        boolean running = true;
        while (running) {
            System.out.println("1. Register an account");
            System.out.println("2. Login");
            System.out.println("3. Exit the program");
            int choice = scanner.nextInt();
            scanner.nextLine();



            if (choice == 1) {
                System.out.println("First and last name ");
                String name = scanner.nextLine();
                System.out.println("Email ");
                String email = scanner.nextLine();
                System.out.println("Password ");
                String password = scanner.nextLine();

                boolean registered = userHandler.registerUser(name, email, password);
                if (registered) {
                    System.out.println("Registered succesfully");
                } else {
                    System.out.println("Registration failed");
                }


            } else if (choice == 2) {
                System.out.println("email ");
                String email = scanner.nextLine();
                System.out.println("password ");
                String password = scanner.nextLine();


                User user = userHandler.loginUser (email, password);
                if (user != null ) {
                    System.out.println("Hello, " + user.getName());
                    boolean loggedIn = true;

                    while (loggedIn) {
                        System.out.println("1. View account information");
                        System.out.println("2. Log out");
                        System.out.println("3. Delete account");
                        int choiceAction = scanner.nextInt();
                        scanner.nextLine();

                        if (choiceAction == 1){
                            System.out.println("User id: " + user.getId());
                            System.out.println("Name: " + user.getName());
                            System.out.println("Email: " + user.getEmail());

                        } else if (choiceAction == 2) {
                            System.out.println("Logged out");
                            loggedIn = false;

                        } else if (choiceAction == 3) {
                            boolean deleted = userHandler.deleteUser(user.getId());
                            if (deleted) {
                                System.out.println("Account deleted successfully");
                                loggedIn = false;
                            } else {
                                System.out.println("failed to delete account");
                            }

                        }else {
                            System.out.println("invalid choice, choose a number between 1-3");
                        }

                    }
                } else {
                    System.out.println("invalid email or password");
                }

            } else if (choice == 3) {
                System.out.println("see you next time");
                running = false;

            }else {
                System.out.println("invalid choice");
            }

        }
        scanner.close();


    }
}