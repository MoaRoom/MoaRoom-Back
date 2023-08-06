package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.UUID;
@Data
public class requestLectureDto {
    @NotNull
    private String title;
    @NotNull
    private UUID professorId;
    @NotNull
    private Integer room;
    private Integer roomCount;

}
