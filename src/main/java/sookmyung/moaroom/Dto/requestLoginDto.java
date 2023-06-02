package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class requestLoginDto {
    @NotNull
    private String id;
    @NotNull
    private String password;
}
