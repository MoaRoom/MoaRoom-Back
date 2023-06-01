package sookmyung.moaroom.Service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sookmyung.moaroom.Dto.requestEnrollDto;
import sookmyung.moaroom.Model.Role;
import sookmyung.moaroom.Model.Url;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Respository.UrlRepository;
import sookmyung.moaroom.Respository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.UUID;

@Service
public class EnrollService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UrlRepository urlRepository;

    public void enroll(UUID professorId, UUID lectureId){
        Users professor = userRepository.findById(professorId).get();
        if (professor.equals(null)){
            try {
                throw new Exception("존재하지 않는 사용자");
            } catch (Exception e) {
                System.out.println("err: "+e.getMessage());
            }
        }
        if(professor.getClasses()==null){
            ArrayList<String> newClass = new ArrayList<>();
            newClass.add(lectureId.toString());
            professor.setClasses(newClass);
        } else{
            ArrayList<String> classList = professor.getClasses();
            classList.add(lectureId.toString());
            professor.setClasses(classList);
        }
        userRepository.save(professor);
    }

    public String save(requestEnrollDto data){
        try {

            Users existUser = userRepository.findById(data.getStudentId()).get();
            if (existUser.equals(null)){
                throw new Exception("존재하지 않는 사용자");
            }
            if(existUser.getClasses()==null){
                ArrayList<String> newClass = new ArrayList<>();
                newClass.add(data.getLectureId().toString());
                existUser.setClasses(newClass);
            } else{
               ArrayList<String> classList = existUser.getClasses();
               classList.add(data.getLectureId().toString());
               existUser.setClasses(classList);
            }
            userRepository.save(existUser);

            // infra측에 req: users model, res: url model
            RestTemplate restTemplate = new RestTemplate();
            
            HashMap<String, Object> student_info = new HashMap<>();
            student_info.put("user_id", existUser.getUserId().toString());
            student_info.put("id", existUser.getId());
            student_info.put("password", existUser.getPassword());
            student_info.put("name", existUser.getName());
            student_info.put("user_num", existUser.getUserNum());
            student_info.put("role", existUser.getRole());

            HashMap<String, Object> reqBody = new HashMap<>();
            reqBody.put("student_info", student_info);
            reqBody.put("lecture_id",data.getLectureId());
            ResponseEntity<Url> response = restTemplate.postForEntity(
                    "http://59.15.113.146:8003/student/",
                    reqBody,
                    Url.class
            );
            System.out.println(response);

            // url 테이블에 저장
            Url newUrl = response.getBody();
            urlRepository.save(newUrl);

            return "강의 신청 완료";
        } catch (Exception e){
            return "err: "+e.getMessage();
        }
    }

    public List<Users> findStudentList(String lecture_id){
        List<Users> studentList = userRepository.findByRole(Role.STUDENT.getRole());
        List<Users> studentListInLecture = new ArrayList<>();
        for (int i = 0; i<studentList.size(); i++){
            Users student = studentList.get(i);
            if(student.getClasses()!=null && student.getClasses().contains(lecture_id)){
                studentListInLecture.add(student);
            }
        }
        return studentListInLecture;
    }
}
