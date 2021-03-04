package projects.todolist.option;

import projects.todolist.model.Todos;
import projects.todolist.service.TodosService;

import java.util.Scanner;

public class DeleteTodosOption implements Option{

    private TodosService todosService = new TodosService();

    @Override
    public void showWelcomeMessage() {
        System.out.println("Usuwanie listy zadań");
    }

    @Override
    public void showOptions() {
        // Nic
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
        System.out.print("Podaj id lub nazwę listy: ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        Todos todos = null;

        try {
            Long id = Long.parseLong(input);
            todos = todosService.getTodos(id);
        } catch (NumberFormatException nfe) {
            todos = todosService.getTodos(input);
        }

        if (todos != null) {
            todosService.remove(todos);
            System.out.println("Usunięto listę zadań");
        } else {
            System.out.println("Nie znaleziono wskazanej listy zadań");
        }

    }

    @Override
    public boolean checkIsExitOption(String option) {
        return true;
    }

    @Override
    public void executeInvalidOption(String option) {
        // Nic
    }

    @Override
    public void showExitMessage() {
        // Nic
    }
}
