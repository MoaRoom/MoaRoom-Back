package sookmyung.moaroom.Model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@IdClass(AssignmentPK.class)
public class Assignment {
    @Id
    @Column(name = "assignment_id")
    @NotNull
    private UUID assignmentId;
    @Id
    @Column(name = "lecture_id")
    @NotNull
    private UUID lectureId;
    @Column(name = "title")
    @NotNull
    private String title;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "due_date")
    private LocalDateTime dueDate;
    @Column(name = "description")
    private String description;

    @Column(name = "answer")
    @NotNull
    private String answer;

    @Column(name = "runtime")
    @NotNull
    private Double runtime;
}
