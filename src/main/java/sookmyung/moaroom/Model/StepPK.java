package sookmyung.moaroom.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StepPK implements Serializable {
    private UUID assignmentId;
    private UUID lectureId;
    private UUID userId;
}
