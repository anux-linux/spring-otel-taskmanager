package fairsource.demo.taskmanagement.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Entity class representing a task.
 */
@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private boolean done;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Column(nullable = false, updatable = false)
    private Instant created;

    /**
     * Creation timestamp for the task is automatically set to the current time when
     * the task is created and stored to the database.
     */
    @PrePersist
    protected void onCreate() {
        this.created = Instant.now();
    }
}
