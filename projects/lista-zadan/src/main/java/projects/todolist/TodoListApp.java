package projects.todolist;

import projects.todolist.config.DbUtil;
import projects.todolist.option.TodoListOption;

public class TodoListApp {

    public static void main(String[] args) {
        DbUtil.getSession();
        TodoListOption todoListOption = new TodoListOption();
        todoListOption.run();
    }
}
