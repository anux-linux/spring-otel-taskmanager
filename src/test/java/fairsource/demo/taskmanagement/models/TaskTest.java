package fairsource.demo.taskmanagement.models;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class TaskTest {

    @Test
    void testInitializeCreatedDateOnPrePersist() {
        Task task = new Task();

        task.onCreate();

        assertThat(task.getCreated()).isNotNull();
        assertThat(task.getCreated()).isCloseTo(Instant.now(), within(5, ChronoUnit.SECONDS));
    }

    @Test
    void testSetAndGetProperties() {
        Task task = new Task();

        task.setId(1L);
        task.setName("Test Task");
        task.setPriority(Priority.URGENT);
        task.setDone(true);
        Instant now = Instant.now();
        task.setCreated(now);

        assertThat(task.getId()).isEqualTo(1L);
        assertThat(task.getName()).isEqualTo("Test Task");
        assertThat(task.getPriority()).isEqualTo(Priority.URGENT);
        assertThat(task.isDone()).isTrue();
        assertThat(task.getCreated()).isEqualTo(now);
    }
}