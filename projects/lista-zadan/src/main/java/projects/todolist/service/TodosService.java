package projects.todolist.service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projects.todolist.config.DbUtil;
import projects.todolist.model.Todos;

public class TodosService {

    private static final Logger logger = LoggerFactory.getLogger(TodosService.class);

    public Todos save(Todos todos) {
        logger.debug("Lista zadań do zapisu: {}", todos);
        Session session = DbUtil.getSession();
        Transaction transaction = session.beginTransaction();
        session.save(todos);
        logger.debug("Zapisano listę zadań pod id = {}", todos.getId());
        transaction.commit();
        logger.debug("Zatwierdzono zapis");
        return todos;
    }
}
