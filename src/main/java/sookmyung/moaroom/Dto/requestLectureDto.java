package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.UUID;
@Data
public class requestLectureDto {
    @NotNull
    private String title;
    @NotNull
    private UUID professor_id;
    @NotNull
    private Integer room;
    private Integer room_count;

}
