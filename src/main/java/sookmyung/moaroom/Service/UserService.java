package sookmyung.moaroom.Service;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sookmyung.moaroom.Model.Role;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Respository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public String save(JsonObject data) {
        try {
            if (data.get("id").isJsonNull() || data.get("pwd").isJsonNull() || data.get("name").isJsonNull() || data.get("role").isJsonNull()) {
                throw new Exception("new user 필수 정보 미입력");
            }

            Users newUser = new Users();
            newUser.setUser_id(UUID.randomUUID());
            newUser.setId(String.valueOf(data.get("id")));
            newUser.setPassword(String.valueOf(data.get("pwd")));
            newUser.setName(String.valueOf(data.get("name")));

            if (data.get("role").getAsInt() == Role.STUDENT.getRole()) {
                newUser.setRole(0);
            } else if (data.get("role").getAsInt() == Role.ASSISTANT.getRole()) {
                newUser.setRole(1);
            } else {
                newUser.setRole(2);
            }

            if (data.has("num")) {
                newUser.setUser_num(data.get("num").getAsInt());
            } else{
                System.out.println(newUser);
                newUser.setUser_num(null);
            }
            userRepository.save(newUser);
            return "새로운 사용자 등록 완료";
        } catch (Exception e) {
            return "err: "+e.getMessage();
        }
    }

    public Users modify(String user_id, JsonObject data){
        try{

            Users existUser = userRepository.findById(UUID.fromString(user_id)).get();
            if (userRepository.findById(UUID.fromString(user_id)).isEmpty()){
                throw new Exception("존재하지 않는 사용자");
            }

            existUser.setId(String.valueOf(data.get("id")));
            existUser.setPassword(String.valueOf(data.get("pwd")));
            existUser.setName(String.valueOf(data.get("name")));

            if (data.get("role").getAsInt() == Role.STUDENT.getRole()) {
                existUser.setRole(0);
            } else if (data.get("role").getAsInt() == Role.ASSISTANT.getRole()) {
                existUser.setRole(1);
            } else {
                existUser.setRole(2);
            }

            if(data.has("num")){
                existUser.setUser_num(data.get("num").getAsInt());
            }else{
                existUser.setUser_num(null);
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
