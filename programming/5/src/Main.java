import tools.Program;

import java.util.Arrays;
import java.util.Scanner;


public class Main {

    /**
     * Создание класса программы {@link Program lab5}
     * передача имя файла для загрузки коллекции, способа ввода и списка доступных комманд;
     * запуск программы на выполнение
     */
    public static void main(String[] args) {

        String fileName = System.getenv("FILENAME");
        Program lab5 = new Program(fileName, new Scanner(System.in), Arrays.asList("help", "info", "show", "add", "update", "remove_by_id", "clear", "save", "execute_script", "exit", "remove_greater", "remove_lower", "history", "min_by_id", "count_less_than_genre", "filter_starts_with_name"));

        lab5.run();

    }
}