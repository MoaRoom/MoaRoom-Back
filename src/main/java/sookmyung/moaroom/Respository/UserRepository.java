package sookmyung.moaroom.Respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sookmyung.moaroom.Model.Users;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {
    public List<Users> findByRole(Integer role);
    public Users findByIdAndPassword(String id, String password);
}
