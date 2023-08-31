package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.Column;
import java.util.UUID;

@Data
public class requestAutoScoreDto {
    @NotNull
    UUID user_id;

    @NotNull
    String answer;

    @NotNull
    Double runtime;

}
