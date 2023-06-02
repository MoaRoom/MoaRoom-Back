package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class requestAssignmentDto {
    @NotNull
    private UUID lecture_id;
    @NotNull
    private UUID user_id;
    @NotNull
    private String title;
    private LocalDateTime start_date;
    private LocalDateTime due_date;
    private String description;
}
