package sookmyung.moaroom.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sookmyung.moaroom.Model.Lecture;

@Repository
public interface LectureRepository extends JpaRepository <Lecture, Integer> {
}
