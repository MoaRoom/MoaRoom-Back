package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class responseAssignmentDto {
    @NotNull
    private UUID lecture_id;
    @NotNull
    private UUID assignment_id;
    @NotNull
    private String title;
    @NotNull
    private Integer step;
    private Integer score;
}
