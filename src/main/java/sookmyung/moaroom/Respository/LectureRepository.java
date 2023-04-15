package sookmyung.moaroom.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sookmyung.moaroom.Model.Lecture;

import java.util.UUID;

@Repository
public interface LectureRepository extends JpaRepository <Lecture, UUID> {
}
