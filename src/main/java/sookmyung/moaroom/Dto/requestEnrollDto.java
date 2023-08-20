package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class requestEnrollDto {
    @NotNull
    private UUID lecture_id;
    @NotNull
    private UUID student_id;
}
