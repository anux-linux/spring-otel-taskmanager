package fairsource.demo.taskmanagement.controllers;

import fairsource.demo.taskmanagement.models.Task;
import fairsource.demo.taskmanagement.repositories.TaskRepository;
import fairsource.demo.taskmanagement.services.impl.TaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class TaskController {

    @Autowired
    private final TaskServiceImpl taskService;
    @Autowired
    private TaskRepository taskRepository;

    public TaskController(TaskServiceImpl taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getAllTasks(Model model) {
        List<Task> tasks = taskService.findAllTasks();
        model.addAttribute("tasks", tasks);
        return "list";
    }

    @GetMapping("/{id}")
    public String getTaskById(@PathVariable Long id, Model model) throws Throwable {
        Task task = (Task) taskService.findTaskById(id);
        model.addAttribute("task", task);
        return "details";
    }

    @GetMapping("/tasks/create")
    public String showCreateForm(Model model) {
        model.addAttribute("task", new Task());
        return "create";
    }

    @PostMapping("/tasks/markOpen")
    public String openTask(@RequestParam Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setDone(false);
        taskRepository.save(task);
        return "redirect:/";

    }

    @PostMapping("/tasks/markDone")
    public String closeTask(@RequestParam Long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setDone(true);
        taskRepository.save(task);
        return "redirect:/";

    }

    //@PostMapping
    //public String createTask(@ModelAttribute("task") Task task) {
    //    taskService.saveTask(task);
    //    return "redirect:/";
    //}

    @PostMapping
    public String createTask(@ModelAttribute Task task) {
        taskService.saveTask(task);
        // Redirect to the task details page for the newly created task
        return "redirect:/" + task.getId(); // Assuming the task details page URL includes the task ID
    }


    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) throws Throwable {
        Task task = (Task) taskService.findTaskById(id);
        model.addAttribute("task", task);
        return "edit";
    }

    @PostMapping("/{id}")
    public String updateTask(@PathVariable Long id, @ModelAttribute("task") Task taskDetails) throws Throwable {

        Task task = (Task) taskService.findTaskById(id);

        task.setName(taskDetails.getName());
        task.setDone(taskDetails.isDone());
        taskService.saveTask(task);
        return "redirect:/";
    }

    @PostMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }
}