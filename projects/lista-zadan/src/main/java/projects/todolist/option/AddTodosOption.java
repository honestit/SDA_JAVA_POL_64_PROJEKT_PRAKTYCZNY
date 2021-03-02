package projects.todolist.option;

import projects.todolist.model.Todos;
import projects.todolist.service.TodosService;

import java.util.Scanner;

public class AddTodosOption implements Option {

    private final TodosService todosService = new TodosService();

    @Override
    public void showWelcomeMessage() {
        // Nie
    }

    @Override
    public void showOptions() {
        // Nie
    }

    @Override
    public String getUserOption() {
        return "";
    }

    @Override
    public boolean validateOption(String option) {
        return true;
    }

    @Override
    public void executeOption(String option) {
        // ??
        System.out.print("Podaj nazwę dla nowej listy zadań: ");
        Scanner scanner = new Scanner(System.in);
        String todosName = scanner.nextLine();

        Todos todos = new Todos();
        todos.setName(todosName);

        todosService.save(todos);

        System.out.println("Zapisano podaną listę zadań");
    }

    @Override
    public boolean checkIsExitOption(String option) {
        return true;
    }

    @Override
    public void executeInvalidOption(String option) {
        // Nie
    }

    @Override
    public void showExitMessage() {
        // Nie
    }
}
