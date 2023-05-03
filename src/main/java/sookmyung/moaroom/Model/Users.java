package sookmyung.moaroom.Model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@TypeDef(name="list_str", typeClass = ArrayList.class)
@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @NotNull
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "id")
    @NotNull
    private String id;
    @Column(name = "password")
    @NotNull
    private String password;
    @Column(name = "name")
    @NotNull
    private String name;
    @Column(name = "user_num")
    private Integer userNum;

    @Column(name = "role")
    @NotNull
    private Integer role;

    @Type(type = "list_str")
    @Column(name="class")
    private ArrayList<String> classes;

}
