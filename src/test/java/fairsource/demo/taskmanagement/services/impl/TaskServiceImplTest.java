package fairsource.demo.taskmanagement.services.impl;

import fairsource.demo.taskmanagement.models.Priority;
import fairsource.demo.taskmanagement.models.Task;
import fairsource.demo.taskmanagement.repositories.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setPriority(Priority.NORMAL);
        task.setDone(false);
    }

    @Test
    void testFindAllTasks() {
        Task task2 = new Task();
        task2.setId(2L);
        task2.setName("Test Task 2");
        task2.setPriority(Priority.URGENT);
        task2.setDone(true);

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task, task2));

        List<Task> tasks = taskService.findAllTasks();

        assertThat(tasks).hasSize(2);
        assertThat(tasks.get(0).getName()).isEqualTo("Test Task");
        assertThat(tasks.get(1).getName()).isEqualTo("Test Task 2");
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    void testSaveTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task savedTask = taskService.saveTask(task);

        assertThat(savedTask).isNotNull();
        assertThat(savedTask.getName()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).save(task);
    }

    @Test
    void testFindTaskById() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = taskService.findTaskById(1L);

        assertThat(foundTask).isNotNull();
        assertThat(foundTask.getId()).isEqualTo(1L);
        assertThat(foundTask.getName()).isEqualTo("Test Task");
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testReturnNullWhenTaskNotFound() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        Task foundTask = taskService.findTaskById(99L);

        assertThat(foundTask).isNull();
        verify(taskRepository, times(1)).findById(99L);
    }

    @Test
    void testDeleteTask() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).deleteById(1L);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testThrowExceptionWhenDeleteNonExistentTask() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> taskService.deleteTask(99L));
        verify(taskRepository, times(1)).findById(99L);
        verify(taskRepository, never()).deleteById(anyLong());
    }
}