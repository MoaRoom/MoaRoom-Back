package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class responseLectureInfoDto {
    @NotNull
    private UUID professor_id;
    @NotNull
    private UUID lecture_id;
    @NotNull
    private String title;
    @NotNull
    private String professor_name;
    @NotNull
    private Integer room;
}
