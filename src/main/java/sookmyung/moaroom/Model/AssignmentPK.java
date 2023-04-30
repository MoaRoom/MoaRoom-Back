package sookmyung.moaroom.Model;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class AssignmentPK implements Serializable {
    private UUID assignmentId;
    private UUID lectureId;
}
