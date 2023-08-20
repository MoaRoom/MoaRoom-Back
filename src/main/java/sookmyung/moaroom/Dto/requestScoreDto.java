package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class requestScoreDto {
    @NotNull
    UUID lecture_id;
    @NotNull
    UUID assignment_id;

    @NotNull
    UUID user_id;

    @NotNull
    Integer score;
}
