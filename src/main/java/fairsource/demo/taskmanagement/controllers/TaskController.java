package fairsource.demo.taskmanagement.controllers;

import fairsource.demo.taskmanagement.models.Task;
import fairsource.demo.taskmanagement.services.impl.TaskServiceImpl;
import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to manage web requests.
 */
@Controller
@RequestMapping("/")
public class TaskController {

    Logger logger = LoggerFactory.getLogger(TaskController.class);

    @Autowired
    private final TaskServiceImpl taskService;

    /**
     * Instantiates TaskController instances.
     *
     * @param taskService task service to manage tasks.
     */
    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    /**
     * Handles the root URL and redirects to the list of tasks.
     *
     * @return redirect to the list of tasks.
     */
    @WithSpan(value = "all tasks")
    @GetMapping
    public String getAllTasks(Model model) {
        logger.info("Getting all tasks");
        List<Task> tasks = taskService.findAllTasks();
        model.addAttribute("tasks", tasks);
        return "list";
    }

    /**
     * Handles the request to get a task by its ID.
     *
     * @param id    the ID of the task to retrieve.
     * @param model the model to add attributes to.
     * @return the view name for editing a task.
     */
    @WithSpan(value = "get task")
    @GetMapping("/{id}")
    public String getTaskById(@PathVariable Long id, Model model) {
        logger.info("Getting task with the ID {}", id);
        Task task = (Task) taskService.findTaskById(id);
        model.addAttribute("task", task);
        return "edit";
    }

    /**
     * Handles the request to show the form for creating a new task.
     *
     * @param model the model to add attributes to.
     * @return the view name for creating a task.
     */
    @WithSpan(value = "show task create")
    @GetMapping("/tasks/new")
    public String showCreateForm(Model model) {
        logger.info("Show create form for task");
        model.addAttribute("task", new Task());
        return "create";
    }

    /**
     * Handles the request to mark a task as open.
     *
     * @param id the ID of the task to mark as open.
     * @return redirect to the list of tasks.
     */
    @WithSpan(value = "open task")
    @PostMapping("/tasks/markOpen")
    public String openTask(@SpanAttribute("TaskID") @RequestParam Long id) {
        logger.info("Set task status to open");
        Task task = taskService.findTaskById(id);
        task.setDone(false);
        taskService.saveTask(task);
        return "redirect:/";

    }

    /**
     * Handles the request to mark a task as done.
     *
     * @param id the ID of the task to mark as done.
     * @return redirect to the list of tasks.
     */
    @WithSpan(value = "close task")
    @PostMapping("/tasks/markDone")
    public String closeTask(@SpanAttribute("TaskID") @RequestParam Long id) {
        logger.info("Set task status to done");
        Task task = taskService.findTaskById(id);
        task.setDone(true);
        taskService.saveTask(task);
        return "redirect:/";

    }

    /**
     * Handles the request to create a new task.
     *
     * @param task the task to create.
     * @return redirect to the list of tasks.
     */
    @WithSpan(value = "create task")
    @PostMapping
    public String createTask(@ModelAttribute Task task) {
        logger.info("Creating new task");
        taskService.saveTask(task);
        return "redirect:/";
    }

    /**
     * Handles the request to update an existing task.
     *
     * @param id          the ID of the task to update.
     * @param taskDetails the updated task details.
     * @return redirect to the list of tasks.
     */
    @WithSpan(value = "update task")
    @PostMapping("/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute("task") Task taskDetails) {

        logger.info("Updating task with with ID {} ", id);
        Task task = taskService.findTaskById(id);

        task.setName(taskDetails.getName());
        task.setDone(taskDetails.isDone());
        task.setPriority(taskDetails.getPriority());
        taskService.saveTask(task);
        return "redirect:/";
    }

    /**
     * Handles the request to delete a task.
     *
     * @param id the ID of the task to delete.
     * @return redirect to the list of tasks.
     */
    @WithSpan(value = "delete task")
    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        logger.info("Deleting task with with ID {} ", id);
        taskService.deleteTask(id);
        return "redirect:/";
    }
}