package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.UUID;

@Data
@NoArgsConstructor
public class responseStepDto {
    @NotNull
    private String name;
    @NotNull
    private UUID id;
    @NotNull
    private Integer step;
    private Integer score;
}
