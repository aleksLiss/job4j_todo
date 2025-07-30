package ru.job4j.todo.repository;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.advice.GlobalExceptionHandler;
import ru.job4j.todo.exception.*;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@ThreadSafe
public class SimpleTaskRepository implements TaskStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTaskRepository.class);
    private final SessionFactory sessionFactory;

    public SimpleTaskRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Task save(Task task) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            LOGGER.error("Ошибка сохранения задачи в базу");
            throw new SaveTaskException("Ошибка сохранения задачи в базу");
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public List<Task> getAll() {
        Session session = sessionFactory.openSession();
        List<Task> tasks;
        try {
            session.beginTransaction();
            tasks = session.createQuery("FROM Task", Task.class)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            LOGGER.error("Ошибка извлечения всех задач");
            throw new GetAllTasksException("Ошибка извлечения всех задач");
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public List<Task> getNewTasks() {
        Session session = sessionFactory.openSession();
        List<Task> tasks;
        LocalDateTime maxCreated = LocalDateTime.now();
        LocalDateTime minCreated = LocalDateTime.now().minusDays(1);
        try {
            session.beginTransaction();
            tasks = session.createQuery("FROM Task as t WHERE t.created BETWEEN :min AND :max", Task.class)
                    .setParameter("min", minCreated)
                    .setParameter("max", maxCreated)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            LOGGER.error("Ошибка извлечения новых задач");
            throw new GetNewTasksException("Ошибка извлечения новых задач");
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public List<Task> getDoneTasks() {
        Session session = sessionFactory.openSession();
        List<Task> tasks;
        try {
            session.beginTransaction();
            tasks = session.createQuery("FROM Task as t WHERE t.done = :isDone", Task.class)
                    .setParameter("isDone", true)
                    .getResultList();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            LOGGER.error("Ошибка извлечения выполненных задач");
            throw new GetDoneTasksException("Ошибка извлечения выполненных задач");
        } finally {
            session.close();
        }
        return tasks;
    }

    @Override
    public void update(Task task) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session
                    .createQuery(
                            "UPDATE Task SET title = :tsTitle, description = :tsDescription, created = :tsCreated, done = :tsDone WHERE id = :tId")
                    .setParameter("tsTitle", task.getTitle())
                    .setParameter("tsDescription", task.getDescription())
                    .setParameter("tsCreated", LocalDateTime.now())
                    .setParameter("tsDone", task.isDone())
                    .setParameter("tId", task.getId())
                    .executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            LOGGER.error("Ошибка обновления задачи в базе");
            throw new UpdateTaskException("Ошибка обновления задачи");
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            Optional<Task> userOptional = Optional.ofNullable(session.get(Task.class, id));
            userOptional.ifPresent(session::delete);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            LOGGER.error("Ошибка удаления задачи из базы");
            throw new DeleteTaskException("Ошибка удаления задачи из базы");
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Task> getById(int taskId) {
        Session session = sessionFactory.openSession();
        Optional<Task> foundTask;
        try {
            session.beginTransaction();
            foundTask = Optional.ofNullable(
                    session.createQuery("FROM Task WHERE id = :tId", Task.class)
                            .setParameter("tId", taskId)
                            .getSingleResult()
            );
        } catch (Exception ex) {
            session.getTransaction().rollback();
            LOGGER.error("Ошибка извлечения задачи из базы");
            throw new GetTaskByIdException("Ошибка извлечения задачи из базы");
        } finally {
            session.close();
        }
        return foundTask;
    }
}
