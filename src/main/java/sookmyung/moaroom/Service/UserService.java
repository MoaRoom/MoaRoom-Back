package sookmyung.moaroom.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sookmyung.moaroom.Dto.requestLoginDto;
import sookmyung.moaroom.Dto.requestUserDto;
import sookmyung.moaroom.Model.Role;
import sookmyung.moaroom.Model.Url;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Respository.UrlRepository;
import sookmyung.moaroom.Respository.UserRepository;

import java.util.List;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UrlRepository urlRepository;

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

            if (data.getUser_num() != null) {
                newUser.setUserNum(data.getUser_num());
            } else{
                newUser.setUserNum(null);
            }
            userRepository.save(newUser);

            if(newUser.getRole() == Role.PROFESSOR.getRole()){

                // infra측에 req: users model, res: url model
                RestTemplate restTemplate = new RestTemplate();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                HashMap<String, Object> reqBody = new HashMap<>();
                reqBody.put("user_id", newUser.getUserId().toString());
                reqBody.put("id", newUser.getId());
                reqBody.put("password", newUser.getPassword());
                reqBody.put("name", newUser.getName());
                reqBody.put("user_num", newUser.getUserNum());
                reqBody.put("role", newUser.getRole());
                HttpEntity<?> request = new HttpEntity<>(reqBody, headers);
                // TODO: response var들을 카멜에서 스네이크로 변환하는 작업 필요(현재는 Infra에서 처리)
                ResponseEntity<Url> response = restTemplate.postForEntity(
                        "http://59.15.113.146:8003/professor/",
                        request,
                        Url.class
                );

                // url 테이블에 저장
                Url newUrl = response.getBody();
                urlRepository.save(newUrl);
            }
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

            if(data.getUser_num() != null){
                existUser.setUserNum(data.getUser_num());
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

    public Url getUrl(String id){
        if(urlRepository.findById(UUID.fromString(id)).get() != null){
               return urlRepository.findById(UUID.fromString(id)).get();
        }
        return null;
    }

    public UUID login(requestLoginDto data){
        Users existUser = userRepository.findByIdAndPassword(data.getId(), data.getPassword());
        if(existUser == null){
            return null;
        } else {
            return existUser.getUserId();
        }
    }
}
