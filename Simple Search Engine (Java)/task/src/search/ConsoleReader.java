package search;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleReader {
    private final List<String> people;

    public ConsoleReader() {
        people = new ArrayList<>();
    }

    public void startConsoleReader() {
        try (Scanner scanner = new Scanner(System.in)) {

            //Number of people
            int peopleCount = readPeopleCount(scanner);

            //Enter people
            addPeople(scanner, peopleCount);

            menuLoop(scanner);
        }
    }

    private void menuLoop(Scanner scanner) {
        printMenu();
        boolean inMenu = true;


        while (inMenu) {
            int option = scanner.nextInt();

            switch (option) {
                case 0 -> {
                    inMenu = false;
                    System.out.println("Bye!");
                }
                case 1 -> searchByNameOrEmail(scanner);
                case 2 -> printAllPeople();
                default -> {
                    System.out.println("Incorrect option! Try again.");
                    printMenu();
                }
            }
        }
    }

    private void searchByNameOrEmail(Scanner scanner) {
        System.out.println("Enter a name or email to search all suitable people.");
        scanner.nextLine();
        String input = scanner.nextLine();

        printFoundPeople(input);

        printMenu();
    }

    private void printAllPeople() {
        System.out.print("=== List of people ===");

        people.forEach(System.out::println);

        printMenu();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }


    private void processSearchData(Scanner scanner, int searchQueriesAmount) {
        for (int i = 0; i < searchQueriesAmount; i++) {
            System.out.println("Enter data to search people:");
            String dataToSearchFor = scanner.nextLine();

            printFoundPeople(dataToSearchFor);
        }
    }

    private int readSearchQueriesAmount(Scanner scanner) {
        System.out.println("Enter the number of search queries:");
        int searchQueriesAmount = scanner.nextInt();

        scanner.nextLine();
        return searchQueriesAmount;
    }

    private void addPeople(Scanner scanner, int peopleCount) {
        System.out.println("Enter all people:");
        for (int i = 0; i <= peopleCount; i++) {
            String input = scanner.nextLine();
            people.add(input);
        }
    }

    private int readPeopleCount(Scanner scanner) {
        System.out.println("Enter the number of people:");
        return scanner.nextInt();
    }

    private void printFoundPeople(String dataToSearchFor) {
        System.out.println("Found people:");
        List<String> foundPeople = people.stream()
                .filter(s -> s.toLowerCase().contains(dataToSearchFor.toLowerCase())).toList();

        if (foundPeople.isEmpty()) {
            System.out.println("No matching people found.");
            return;
        }

        foundPeople.forEach(System.out::println);
    }
}
