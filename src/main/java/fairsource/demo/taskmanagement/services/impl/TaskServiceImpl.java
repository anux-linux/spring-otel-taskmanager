package fairsource.demo.taskmanagement.services.impl;

import fairsource.demo.taskmanagement.services.TaskService;
import fairsource.demo.taskmanagement.models.Task;
import fairsource.demo.taskmanagement.repositories.TaskRepository;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task findTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
            taskRepository.deleteById(id);
    }
}
