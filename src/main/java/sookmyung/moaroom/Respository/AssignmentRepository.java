package sookmyung.moaroom.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sookmyung.moaroom.Model.Assignment;
import sookmyung.moaroom.Model.AssignmentPK;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, AssignmentPK> {
}
