package sookmyung.moaroom.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sookmyung.moaroom.Model.Url;

import java.util.UUID;

@Repository
public interface UrlRepository extends JpaRepository<Url, UUID>{
}
