package ru.job4j.todo.repository;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@ThreadSafe
public class TaskRepository implements Store {

    private final SessionFactory sessionFactory;

    public TaskRepository(SessionFactory sessionFactory) {
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
            throw new RuntimeException("Error save task");
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
            throw new RuntimeException("Error get all tasks");
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
            throw new RuntimeException("Error get new tasks");
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
            throw new RuntimeException("Error get done tasks");
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
//            session.update(task);
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
            throw new RuntimeException("Error update task");
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
            throw new RuntimeException("Error delete task");
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
            throw new RuntimeException("Error get task by id");
        } finally {
            session.close();
        }
        return foundTask;
    }
}
