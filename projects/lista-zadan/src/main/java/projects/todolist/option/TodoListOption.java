package projects.todolist.option;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.Set;

public class TodoListOption implements Option {

    private static final Logger logger = LoggerFactory.getLogger(TodoListOption.class);

    @Override
    public void showWelcomeMessage() {
        System.out.println("\nWitamy w Liście Zadań");
    }

    @Override
    public void showOptions() {
        System.out.println("\nMenu:");
        System.out.println("\t1. Listy zadań");
        System.out.println("\t2. Zadania");
        System.out.println("\t3. Pokaż najbliższe zadania do realizacji");
        System.out.println("\t0. Zakończ");
    }

    @Override
    public String getUserOption() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nTwór wybór: ");
        return scanner.nextLine();
    }

    @Override
    public boolean validateOption(String option) {
        return Set.of("1", "2", "3", "0").contains(option);
    }

    @Override
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

    @Override
    public boolean checkIsExitOption(String option) {
        return "0".equals(option);
    }

    @Override
    public void executeInvalidOption(String option) {
        System.out.println("\nNiestety '" + option + "' jest niepoprawnym wyborem");
    }

    @Override
    public void showExitMessage() {
        System.out.println("\nDo zobaczenia!");
    }
}
