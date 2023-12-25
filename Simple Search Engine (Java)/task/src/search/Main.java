package search;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
//        //Reads the input from the console
//        ConsoleReader consoleReader = new ConsoleReader();
//        consoleReader.startConsoleReader();

        //Reads the input from a file
        try {
            CustomFileReader customFileReader = new CustomFileReader(args);
            customFileReader.startCustomFileReader();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
