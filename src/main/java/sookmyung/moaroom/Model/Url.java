package sookmyung.moaroom.Model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Url {
    @Id
    @NotNull
    @Column(name = "id")
    private UUID id;
    @Column(name = "lecture_id")
    private UUID lectureId;

    @NotNull
    @Column(name = "container_address")
    private String containerAddress;
}
