package sookmyung.moaroom.Model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@IdClass(StepPK.class)
public class Step {
    @Id
    @Column(name = "assignment_id")
    @NotNull
    private UUID assignment_id;
    @Id
    @Column(name = "lecture_id")
    @NotNull
    private Integer lecture_id;
    @Id
    @Column(name = "user_id")
    @NotNull
    private UUID user_id;
    @NotNull
    @Column(name = "step")
    private Integer step;
    @Column(name = "score")
    private Integer score;
}
