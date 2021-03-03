package projects.todolist;

import org.hibernate.Session;
import org.hibernate.Transaction;
import projects.todolist.config.DbUtil;
import projects.todolist.model.Task;
import projects.todolist.model.Todos;
import projects.todolist.option.TodoListOption;

import java.time.LocalDateTime;
import java.util.List;

public class TodoListApp {

    public static void main(String[] args) {
        laodData();
        TodoListOption todoListOption = new TodoListOption();
        todoListOption.run();
    }

    private static void laodData() {

        Session session = DbUtil.getSession();
        Transaction transaction = session.beginTransaction();

        Todos todos = new Todos();
        todos.setName("Sprzątanie");
        session.save(todos);

        List<Task> tasks = List.of(
                createTask("Zrobić obiad", true, 10),
                createTask("Spacer z psem", true, 5),
                createTask("Wykąpać dzieci", true, 3),
                createTask("Odkurzyć", false, 1),
                createTask("Powiesić pranie", false, 2),
                createTask("Zatankować auto", false, 7)
        );

        tasks.stream()
                .peek(task -> task.setTodos(todos))
                .forEach(task -> session.save(task));

        transaction.commit();
    }

    private static Task createTask(String name, boolean completed, int hours) {
        Task task = new Task();
        task.setName(name);
        if (completed) {
            task.setCompletedOn(LocalDateTime.now().minusHours(hours));
        } else {
            task.setExpectedCompletedOn(LocalDateTime.now().plusHours(hours));
        }
        return task;
    }


}
