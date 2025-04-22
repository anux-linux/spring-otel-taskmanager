package fairsource.demo.taskmanagement.repositories;

import fairsource.demo.taskmanagement.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
