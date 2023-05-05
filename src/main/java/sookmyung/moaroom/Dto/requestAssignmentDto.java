package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class requestAssignmentDto {
    @NotNull
    private UUID lectureId;
    @NotNull
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private String description;
}
