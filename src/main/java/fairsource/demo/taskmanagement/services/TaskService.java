package fairsource.demo.taskmanagement.services;

import fairsource.demo.taskmanagement.models.Task;

import java.util.List;

/**
 * Service interface for managing tasks.
 * It provides methods to perform CRUD operations on tasks.
 */
public interface TaskService {
    /**
     * Retrieves all tasks.
     *
     * @return a list of all tasks.
     */
    List<Task> findAllTasks();

    /**
     * Saves a task.
     *
     * @param task the task to save.
     * @return the saved task.
     */
    Task saveTask(Task task);

    /**
     * Finds a specific task by its ID.
     *
     * @param id the ID of the task to find.
     * @return the found task, or null if not found.
     */
    Task findTaskById(Long id);

    /**
     * Deletes a specific task by its ID.
     *
     * @param id the ID of the task to delete.
     */
    void deleteTask(Long id);
}
