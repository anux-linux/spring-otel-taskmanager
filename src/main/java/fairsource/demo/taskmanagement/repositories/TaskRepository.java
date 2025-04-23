package fairsource.demo.taskmanagement.repositories;

import fairsource.demo.taskmanagement.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Task entities.
 * It extends JpaRepository to provide CRUD operations.
 */
public interface TaskRepository extends JpaRepository<Task, Long> {
}
