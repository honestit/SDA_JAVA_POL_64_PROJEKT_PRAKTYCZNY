package projects.todos.option;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.Set;

public class TodoListOption {

    private static final Logger log = LoggerFactory.getLogger(TodoListOption.class);

    public void run(Object... args) {
        showWelcomeMessage();
        while (true) {
            showOptions();
            String option = getUserOption();
            if (validateOption(option)) {
                executeOption(option);
                if (checkIsExitOption(option)) {
                    break;
                }
            } else {
                executeInvalidOption(option);
            }
        }
        showExitMessage();
    }

    public void showWelcomeMessage() {
        System.out.println("\nWitamy w Liście Zadań");
    }

    public void showOptions() {
        System.out.println("\nMenu:");
        System.out.println("\t1. Listy zadań");
        System.out.println("\t2. Zadania");
        System.out.println("\t3. Pokaż najbliższe zadania do realizacji");
        System.out.println("\t0. Zakończ");
    }

    public String getUserOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nTwór wybór: ");
        return scanner.nextLine();
    }

    public boolean validateOption(String option) {
        return Set.of("1", "2", "3", "0").contains(option);
    }

    public void executeOption(String option) {
        switch (option) {
            case "1":
                new ManageTodosOption().run();
                break;
            case "2":
            case "3":
                break;
        }
    }

    public boolean checkIsExitOption(String option) {
        return "0".equals(option);
    }

    public void executeInvalidOption(String option) {
        System.out.println("\nNiestety '" + option + "' jest niepoprawnym wyborem");
    }

    public void showExitMessage() {
        System.out.println("\nDo zobaczenia!");
    }
}
