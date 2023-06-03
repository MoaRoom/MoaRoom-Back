package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class responseLectureDto {
    @NotNull
    private String lecture_id;
    @NotNull
    private String title;
    @NotNull
    private Integer room;
    @NotNull
    private String professor_name;

    private Boolean enroll;
}
