package sookmyung.moaroom.Service;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sookmyung.moaroom.Dto.requestUserDto;
import sookmyung.moaroom.Model.Role;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Respository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public String save(requestUserDto data) {
            Users newUser = new Users();
            newUser.setUserId(UUID.randomUUID());
            newUser.setId(data.getId());
            newUser.setPassword(data.getPassword());
            newUser.setName(data.getName());

            if (data.getRole() == Role.STUDENT.getRole()) {
                newUser.setRole(0);
            } else if (data.getRole() == Role.ASSISTANT.getRole()) {
                newUser.setRole(1);
            } else {
                newUser.setRole(2);
            }

            if (data.getUserNum() != null) {
                newUser.setUserNum(data.getUserNum());
            } else{
                newUser.setUserNum(null);
            }
            userRepository.save(newUser);
            return "새로운 사용자 등록 완료";
    }

    public Users modify(String user_id, requestUserDto data){
        try{
            Users existUser = userRepository.findById(UUID.fromString(user_id)).get();
            if (existUser == null){
                throw new Exception("존재하지 않는 사용자");
            }

            existUser.setId(data.getId());
            existUser.setPassword(data.getPassword());
            existUser.setName(data.getName());

            if (data.getRole() == Role.STUDENT.getRole()) {
                existUser.setRole(0);
            } else if (data.getRole() == Role.ASSISTANT.getRole()) {
                existUser.setRole(1);
            } else {
                existUser.setRole(2);
            }

            if(data.getUserNum() != null){
                existUser.setUserNum(data.getUserNum());
            }
            return userRepository.save(existUser);
        } catch (Exception e){
            System.out.println("err: "+e.getMessage());
        }
        return null;
    }

    public List<Users> findAll(){
        try{
            if(userRepository.findAll().isEmpty()){
                throw new Exception("사용자 없음");
            }
            return userRepository.findAll();

        } catch (Exception e){
            System.out.println("err: "+e.getMessage());
        }
        return null;
    }

    public Users findOne(String id){
        try {
            Users user = userRepository.findById(UUID.fromString(id)).get();
            if (userRepository.findById(UUID.fromString(id)).isEmpty()){
                throw new Exception("찾는 사용자 없음");
            }
            return user;
        } catch (Exception e){
            System.out.println("err: "+e.getMessage());
        }
        return null;
    }

    public String delete(String id){
        try {
            if(userRepository.findById(UUID.fromString(id)).isEmpty()){
                throw new Exception("존재하지 않는 사용자");
            }
            userRepository.deleteById(UUID.fromString(id));
            return "삭제 성공";
        } catch (Exception e){
            return "err: "+e.getMessage();
        }
    }
}
