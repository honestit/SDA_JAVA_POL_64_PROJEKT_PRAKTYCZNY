package projects.todolist.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Todos todos;

    @Column(nullable = false)
    private String name;
    @Column(name = "created_on", insertable = false)
    private LocalDateTime createdOn;
    @Column(name = "expected_completed_on")
    private LocalDateTime expectedCompletedOn;
    @Column(name = "completed_on")
    private LocalDateTime completedOn;

    public Task() {
    }

    public Task(String name, LocalDateTime createdOn, LocalDateTime expectedCompletedOn) {
        this.name = name;
        this.createdOn = createdOn;
        this.expectedCompletedOn = expectedCompletedOn;
    }

    public Task(String name, LocalDateTime createdOn, LocalDateTime expectedCompletedOn, LocalDateTime completedOn) {
        this.name = name;
        this.createdOn = createdOn;
        this.expectedCompletedOn = expectedCompletedOn;
        this.completedOn = completedOn;
    }

    @PrePersist
    void prePersist() {
        createdOn = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (name != null ? !name.equals(task.name) : task.name != null) return false;
        if (createdOn != null ? !createdOn.equals(task.createdOn) : task.createdOn != null)
            return false;
        return expectedCompletedOn != null ? expectedCompletedOn.equals(task.expectedCompletedOn) : task.expectedCompletedOn == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0);
        result = 31 * result + (expectedCompletedOn != null ? expectedCompletedOn.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", createdOn=" + createdOn +
                ", expectedCompletedOn=" + expectedCompletedOn +
                ", completedOn=" + completedOn +
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

    public LocalDateTime getExpectedCompletedOn() {
        return expectedCompletedOn;
    }

    public void setExpectedCompletedOn(LocalDateTime expectedCompletedOn) {
        this.expectedCompletedOn = expectedCompletedOn;
    }

    public LocalDateTime getCompletedOn() {
        return completedOn;
    }

    public void setCompletedOn(LocalDateTime completedOn) {
        this.completedOn = completedOn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Todos getTodos() {
        return todos;
    }

    public void setTodos(Todos todos) {
        this.todos = todos;
    }
}
