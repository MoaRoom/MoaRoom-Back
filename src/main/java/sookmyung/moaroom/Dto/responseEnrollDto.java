package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class responseEnrollDto {
    @NotNull
    private UUID studentId;
    @NotNull
    private String id;
    @NotNull
    private String name;
}
