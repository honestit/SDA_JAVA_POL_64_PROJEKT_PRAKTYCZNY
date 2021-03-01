package projects.todolist.model;

import java.time.LocalDateTime;

public class Todos {

    private String name;
    private LocalDateTime createdOn;

    public Todos() {
    }

    public Todos(String name, LocalDateTime createdOn) {
        this.name = name;
        this.createdOn = createdOn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todos todos = (Todos) o;

        return name != null ? name.equals(todos.name) : todos.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Todos{" +
                "name='" + name + '\'' +
                ", createdOn=" + createdOn +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
