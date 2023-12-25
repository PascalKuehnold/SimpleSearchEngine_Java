package search;

import search.searchStrategy.SearchAll;
import search.searchStrategy.SearchAny;
import search.searchStrategy.SearchNone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

enum MatchingStrategy {
    ALL,
    ANY,
    NONE
}

public class CustomFileReader {

    /**
     * Stores the inverted index, where each key is a word and its corresponding value is a Set of document IDs where the word appears.
     */
    private final HashMap<String, Set<Integer>> peoples;

    /**
     * Stores all the people information from the file.
     */
    private final List<String> listOfPeople;

    /**
     * The path to the file containing the people information.
     */
    private final String filePath;

    /**
     * Initializes the attributes and reads the data from the file.
     *
     * @param args Command-line arguments, where the first argument is the path to the file.
     * @throws IOException If an I/O error occurs.
     */
    public CustomFileReader(String[] args) throws IOException {
        peoples = new HashMap<>();
        listOfPeople = new ArrayList<>();

        filePath = args[1];
    }

    /**
     * Starts the main application loop, which consists of the menu and the search functionality.
     *
     * @throws IOException If an I/O error occurs.
     */
    public void startCustomFileReader() throws IOException {
        startFileReader();
        menuLoop();
    }


    /**
     * Reads the data from the file and populates the inverted index and the list of people.
     *
     * @throws IOException If an I/O error occurs.
     */
    private void startFileReader() throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath))) {

            String currentLine;
            int index = 0;

            while ((currentLine = fileReader.readLine()) != null) {
                String[] words = currentLine.split(" ");

                listOfPeople.add(currentLine);

                for (String word : words) {
                    word = word.toLowerCase().replaceAll("\\s", "");

                    if (peoples.containsKey(word)) {
                        peoples.get(word).add(index);
                    } else {
                        Set<Integer> indexes = peoples.get(word);
                        if (indexes == null) {
                            indexes = new HashSet<>();
                        }

                        indexes.add(index);
                        peoples.put(word, indexes);
                    }
                }

                index++;
            }
                System.out.println(peoples);
        }
    }

    /**
     * Displays the menu and handles user input.
     */
    private void menuLoop() {
        printMenu();
        boolean inMenu = true;


        try (Scanner scanner = new Scanner(System.in)) {
            while (inMenu) {
                int option = scanner.nextInt();

                switch (option) {
                    case 0 -> {
                        inMenu = false;
                        System.out.println("Bye!");
                    }
                    case 1 -> {
                        MatchingStrategy strategy = getSelectedMatchingStrategy(scanner);
                        searchByNameOrEmail(scanner, strategy);
                    }
                    case 2 -> printAllPeople();
                    default -> {
                        System.out.println("Incorrect option! Try again.");
                        printMenu();
                    }
                }
            }
        }
    }

    /**
     * Gets the selected matching strategy from the user.
     *
     * @param scanner Scanner object for reading user input.
     * @return The selected matching strategy.
     */
    private MatchingStrategy getSelectedMatchingStrategy(Scanner scanner) {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");

        MatchingStrategy result = null;

        do {
            String strategy = scanner.nextLine().toUpperCase();
            for (MatchingStrategy matchingStrategy : MatchingStrategy.values()) {
                if (matchingStrategy.name().equalsIgnoreCase(strategy)) {
                    result = matchingStrategy;
                    break;
                }
            }
        } while (result == null);

        return result;
    }

    /**
     * Prompts the user for a search term and performs the search based on the selected matching strategy.
     *
     * @param scanner Scanner object for reading user input.
     */
    private void searchByNameOrEmail(Scanner scanner, MatchingStrategy strategy) {
        System.out.println("Enter a name or email to search all suitable people.");

        String input = scanner.nextLine();
        trySearchPeople(input, strategy);

        printMenu();
    }

    /**
     * Prints all the people information from the list.
     */
    private void printAllPeople() {
        System.out.println();
        System.out.println("=== List of people ===");

        listOfPeople.forEach(System.out::println);

        printMenu();
    }

    /**
     * Displays the menu options.
     */
    private void printMenu() {
        System.out.println();
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    /**
     * Performs the actual search for the given search term.
     *
     * @param dataToSearchFor The search term to search for.
     */
    private void trySearchPeople(String dataToSearchFor, MatchingStrategy strategy) {
        if (strategy == null) {
            System.out.println("No matching Strategy found");
            return;
        }

        if (dataToSearchFor.trim().equalsIgnoreCase("@")) {
            System.out.println("No matching people found.");
            return;
        }

        switch (strategy) {
            case NONE -> new SearchNone(peoples, listOfPeople).startSearch(dataToSearchFor);
            case ANY -> new SearchAny(peoples, listOfPeople).startSearch(dataToSearchFor);
            case ALL -> new SearchAll(peoples, listOfPeople).startSearch(dataToSearchFor);
        }
    }

}
