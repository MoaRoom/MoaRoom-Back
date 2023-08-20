package sookmyung.moaroom.Dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class requestUserDto {
    @NotNull
    private String id;
    @NotNull
    private String password;
    @NotNull
    private String name;
    private Integer user_num;
    @NotNull
    private Integer role;

}
