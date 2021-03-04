package projects.todolist.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "todos")
public class Todos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "todos")
    private List<Task> tasks;

    @Column(unique = true, nullable = false)
//    @NaturalId // (patrz TodosService.getTodos(String name) co nam to daje)
    private String name;
    @Column(name = "created_on", updatable = false)
    private LocalDateTime createdOn;

    // UPDATE todos SET name = ? WHERE id = ?
    // INSERT INTO todos (id, name, created_on) VALUES (?,?,?)

    public Todos() {
    }

    public Todos(String name, LocalDateTime createdOn) {
        this.name = name;
        this.createdOn = createdOn;
    }

    @PrePersist
    void prePersist() {
        createdOn = LocalDateTime.now();
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
                "id='" + id + '\'' +
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
