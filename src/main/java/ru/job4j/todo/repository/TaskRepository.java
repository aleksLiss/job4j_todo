package ru.job4j.todo.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
@Data
@ThreadSafe
public class TaskRepository implements Store {

    private final SessionFactory sessionFactory;

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
        List<Task> tasks = new ArrayList<>();
        try {
            session.beginTransaction();
            for (Task task : getAll()) {
                if (task.getCreated() >= LocalDateTime.now()) {
                    tasks.add(task);
                }
            }
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
        List<Task> tasks = new ArrayList<>();
        try {
            session.beginTransaction();
            for (Task task : getAll()) {
                if (task.isDone()) {
                    tasks.add(task);
                }
            }
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
            session.update(task);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException("Error update task");
        } finally {
            session.close();
        }
    }

    @Override
    public void delete(Task task) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(task.getId(), task);
            session.getTransaction().commit();
        } catch (Exception ex) {
            session.getTransaction().rollback();
            throw new RuntimeException("Error delete task");
        } finally {
            session.close();
        }
    }
}
