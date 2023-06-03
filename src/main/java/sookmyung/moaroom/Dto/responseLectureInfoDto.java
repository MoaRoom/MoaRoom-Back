package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class responseLectureInfoDto {
    @NotNull
    private String title;
    @NotNull
    private String professor;
    @NotNull
    private Integer room;
}
