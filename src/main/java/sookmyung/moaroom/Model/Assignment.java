package sookmyung.moaroom.Model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Data
@IdClass(AssignmentPK.class)
public class Assignment {
    @Id
    @Column(name = "assignment_id")
    @NotNull
    private UUID assignment_id;
    @Id
    @Column(name = "lecture_id")
    @NotNull
    private int lecture_id;
    @Column(name = "title")
    @NotNull
    private String title;
    @Column(name = "start_date")
    private Timestamp start_date;
    @Column(name = "due_date")
    private Timestamp due_date;
    @Column(name = "description")
    private String description;
}
