package sookmyung.moaroom.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sookmyung.moaroom.Model.Assignment;
import sookmyung.moaroom.Model.AssignmentPK;

import java.util.UUID;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, AssignmentPK> {
    public Assignment findByAssignmentId(UUID assignmentId);
    @Transactional
    public void deleteByAssignmentId(UUID assignmentId);
}
