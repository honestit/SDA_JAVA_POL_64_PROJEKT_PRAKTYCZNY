package projects.todolist.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projects.todolist.config.DbUtil;
import projects.todolist.model.Todos;

import java.util.Collection;
import java.util.List;

public class TodosService {

    private static final Logger logger = LoggerFactory.getLogger(TodosService.class);

    public Todos save(Todos todos) {
        logger.debug("Lista zadań do zapisu: {}", todos);
        try (Session session = DbUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(todos);
            logger.debug("Zapisano listę zadań pod id = {}", todos.getId());
            transaction.commit();
            logger.debug("Zatwierdzono zapis");
            return todos;
        }
    }

    public Collection<Todos> findAll() {
        try (Session session = DbUtil.getSession()) {
            return session.createQuery("SELECT DISTINCT ts FROM Todos ts LEFT JOIN FETCH ts.tasks", Todos.class).getResultList();
        }
    }

    public Todos getTodos(Long id) {
        logger.debug("Pobieranie listy zadań dla id = {}", id);
        try (Session session = DbUtil.getSession()) {
            Todos todos = session.find(Todos.class, id);
            logger.debug("Pobrana lista zadań: {}", todos);
            return todos;
        }
    }

    public Todos getTodos(String name) {
        logger.debug("Pobieranie listy zadań o nazwie = '{}'", name);
        try (Session session = DbUtil.getSession()) {
//            Todos todos = session.byNaturalId(Todos.class).load();
            List<Todos> todosList = session.createQuery("SELECT t FROM Todos t WHERE t.name = :name", Todos.class)
                    .setParameter("name", name)
                    .getResultList();
            if (todosList.isEmpty()) {
                return null;
            } else {
                return todosList.get(0);
            }
        }
    }

    public void remove(Todos todos) {
        logger.info("Usuwanie listy zadań: {}", todos);
        try (Session session = DbUtil.getSession()) {
            Transaction transaction = session.beginTransaction();
            // UWAGA: Kiedy ta operacja się nie uda?
            todos = (Todos) session.merge(todos);
            if (!todos.getTasks().isEmpty()) {
                todos.getTasks().forEach(session::remove);
            }
            session.remove(todos);
            transaction.commit();
            logger.info("Usunięcio listę zadań o id = {}", todos.getId());
        }
    }
}
