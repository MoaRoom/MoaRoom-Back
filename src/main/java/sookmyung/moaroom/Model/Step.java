package sookmyung.moaroom.Model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@IdClass(StepPK.class)
@NoArgsConstructor
public class Step {
    @Id
    @Column(name = "assignment_id")
    @NotNull
    private UUID assignmentId;
    @Id
    @Column(name = "lecture_id")
    @NotNull
    private UUID lectureId;
    @Id
    @Column(name = "user_id")
    @NotNull
    private UUID userId;
    @NotNull
    @Column(name = "step")
    private Integer step;
    @Column(name = "score")
    private Integer score;
}
