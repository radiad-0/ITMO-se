import basic.Program;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        String fileName = System.getenv("FILENAME");
        Program lab5 = new Program(fileName, new Scanner(System.in));

        lab5.run();
    }

}