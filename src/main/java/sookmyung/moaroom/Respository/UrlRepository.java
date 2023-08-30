package sookmyung.moaroom.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sookmyung.moaroom.Model.Step;
import sookmyung.moaroom.Model.Url;

import java.util.List;
import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<Url, UUID>{
    public Url findByUserIdAndLectureId(UUID userId, UUID lectureId);
    public List<Url> findByUserId(UUID userId);
}
