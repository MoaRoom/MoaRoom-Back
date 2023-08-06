package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class requestScoreDto {
    @NotNull
    UUID lectureId;
    @NotNull
    UUID assignmentId;

    @NotNull
    UUID userId;

    @NotNull
    Integer score;
}
