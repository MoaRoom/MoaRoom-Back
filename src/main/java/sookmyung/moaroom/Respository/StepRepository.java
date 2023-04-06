package sookmyung.moaroom.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sookmyung.moaroom.Model.Step;
import sookmyung.moaroom.Model.StepPK;

@Repository
public interface StepRepository extends JpaRepository<Step, StepPK> {
}
