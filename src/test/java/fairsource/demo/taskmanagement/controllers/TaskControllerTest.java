package fairsource.demo.taskmanagement.controllers;

import fairsource.demo.taskmanagement.models.Priority;
import fairsource.demo.taskmanagement.models.Task;
import fairsource.demo.taskmanagement.services.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskServiceImpl taskService;

    @Mock
    private Model model;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;
    private Task task;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();

        task = new Task();
        task.setId(1L);
        task.setName("Test Task");
        task.setPriority(Priority.NORMAL);
        task.setDone(false);
    }

    @Test
    void testReturnListViewWithAllTasks() throws Exception {
        List<Task> tasks = Arrays.asList(task);
        when(taskService.findAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("list"))
                .andExpect(model().attributeExists("tasks"))
                .andExpect(model().attribute("tasks", tasks));

        verify(taskService, times(1)).findAllTasks();
    }

    @Test
    void testReturnEditViewWithTask() throws Exception {
        when(taskService.findTaskById(1L)).thenReturn(task);

        mockMvc.perform(get("/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("task"))
                .andExpect(model().attribute("task", task));

        verify(taskService, times(1)).findTaskById(1L);
    }

    @Test
    void testReturnCreateFormWithNewTask() throws Exception {
        mockMvc.perform(get("/tasks/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("create"))
                .andExpect(model().attributeExists("task"));
    }

    @Test
    void testMarkTaskAsOpen() throws Exception {
        when(taskService.findTaskById(1L)).thenReturn(task);

        mockMvc.perform(post("/tasks/markOpen")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(taskService, times(1)).findTaskById(1L);

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskService, times(1)).saveTask(taskCaptor.capture());
        assertThat(taskCaptor.getValue().isDone()).isFalse();
    }

    @Test
    void testMarkTaskAsDone() throws Exception {
        when(taskService.findTaskById(1L)).thenReturn(task);

        mockMvc.perform(post("/tasks/markDone")
                        .param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(taskService, times(1)).findTaskById(1L);

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskService, times(1)).saveTask(taskCaptor.capture());
        assertThat(taskCaptor.getValue().isDone()).isTrue();
    }

    @Test
    void testCreateNewTask() throws Exception {
        when(taskService.saveTask(any(Task.class))).thenReturn(task);

        mockMvc.perform(post("/")
                        .param("name", "New Task")
                        .param("priority", "NORMAL"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskService, times(1)).saveTask(taskCaptor.capture());
        assertThat(taskCaptor.getValue().getName()).isEqualTo("New Task");
    }

    @Test
    void testUpdateExistingTask() throws Exception {
        when(taskService.findTaskById(1L)).thenReturn(task);

        mockMvc.perform(post("/{id}", 1L)
                        .param("name", "Updated Task")
                        .param("priority", "URGENT")
                        .param("done", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(taskService, times(1)).findTaskById(1L);

        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskService, times(1)).saveTask(taskCaptor.capture());
        Task updatedTask = taskCaptor.getValue();
        assertThat(updatedTask.getName()).isEqualTo("Updated Task");
        assertThat(updatedTask.getPriority()).isEqualTo(Priority.URGENT);
        assertThat(updatedTask.isDone()).isTrue();
    }

    @Test
    void testDeleteTask() throws Exception {
        mockMvc.perform(post("/{id}/delete", 1L))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(taskService, times(1)).deleteTask(1L);
    }
}