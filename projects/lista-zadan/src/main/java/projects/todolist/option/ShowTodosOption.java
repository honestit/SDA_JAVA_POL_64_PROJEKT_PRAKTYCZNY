package projects.todolist.option;

import projects.todolist.model.Task;
import projects.todolist.model.Todos;
import projects.todolist.service.TodosService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;

public class ShowTodosOption implements Option{

    private TodosService todosService = new TodosService();

    @Override
    public void showWelcomeMessage() {
        System.out.println("\nZapisane listy zadań:\n");
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
        Collection<Todos> todosCollection = todosService.findAll();
        for (Todos todos : todosCollection) {
            System.out.printf("%3d. %s (%d)%n",
                    todos.getId(),
                    todos.getName(),
                    todos.getTasks().size()
                    );

//            boolean seen = false;
//            Task best = null;
//            Comparator<Task> comparator = Comparator.comparing(Task::getCompletedOn);
//            for (Task task1 : todos.getTasks()) {
//                if (task1.getCompletedOn() != null) {
//                    if (!seen || comparator.compare(task1, best) > 0) {
//                        seen = true;
//                        best = task1;
//                    }
//                }
//            }
//            Task lastCompletedTask = (seen ? Optional.of(best) : Optional.<Task>empty())
//                    .get();

            Optional<Task> lastCompletedTask = todos.getTasks().stream()
                    .filter(task -> task.getCompletedOn() != null)
                    .max(Comparator.comparing(Task::getCompletedOn));

            // DbUtil.getSession().createQuery("SELECT t FROM Task t WHERE t.completedOn IS NOT NULL AND t.todos = :todos ORDER BY t.completedOn DESC", Task.class).setParameter("todos", todos).setMaxResults(1).getResultsList();

            Optional<Task> nextToBeCompletedTask = todos.getTasks().stream()
                    .filter(task -> task.getCompletedOn() == null)
                    .min(Comparator.comparing(Task::getExpectedCompletedOn));

            System.out.printf("\t- ostatnio zakończone: %s (%s)%n",
                    lastCompletedTask.map(Task::getName).orElse("brak"),
                    lastCompletedTask.map(Task::getCompletedOn)
                            .map(date -> date.format(DateTimeFormatter.ofPattern("EEE, dd MMMM, HH:mm"))).orElse("-"));
            System.out.printf("\t- nabliższe do zrobienia: %s (%s)%n",
                    nextToBeCompletedTask.map(Task::getName).orElse("brak"),
                    nextToBeCompletedTask.map(Task::getExpectedCompletedOn)
                            .map(date -> date.format(DateTimeFormatter.ofPattern("EEE, dd MMMM, HH:mm"))).orElse("-"));
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
