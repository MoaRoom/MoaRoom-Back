package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class requestEnrollDto {
    @NotNull
    private UUID lectureId;
    @NotNull
    private UUID studentId;
}
