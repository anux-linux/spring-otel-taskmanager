package fairsource.demo.taskmanagement.services;

import fairsource.demo.taskmanagement.models.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAllTasks();
    Task saveTask(Task task);
    Task findTaskById(Long id);
    void deleteTask(Long id);
}
