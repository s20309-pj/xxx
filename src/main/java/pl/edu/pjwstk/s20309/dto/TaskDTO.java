package pl.edu.pjwstk.s20309.dto;

import java.time.LocalDate;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.edu.pjwstk.s20309.entity.Client;
import pl.edu.pjwstk.s20309.entity.User;
import pl.edu.pjwstk.s20309.enums.TaskStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long id;

    private User assignedEmployee;

    private String taskDescription;

    private String note;

    private TaskStatus taskStatus;

    @Timestamp
    private LocalDate modificationDate;

    private Client client;

    private User creator;

}
