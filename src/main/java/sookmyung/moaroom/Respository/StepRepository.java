package sookmyung.moaroom.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sookmyung.moaroom.Model.Step;
import sookmyung.moaroom.Model.StepPK;

import java.util.List;
import java.util.UUID;


@Repository
public interface StepRepository extends JpaRepository<Step, StepPK> {

    public List<Step> findByUserId(UUID userId);
}
