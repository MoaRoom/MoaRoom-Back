package sookmyung.moaroom.Model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Lecture {
    @Id
    @Column(name = "lecture_id")
    @NotNull
    private Integer lecture_id;
    @Column(name = "title")
    @NotNull
    private String title;
    @Column(name = "professor_id")
    @NotNull
    private UUID professor_id;
}