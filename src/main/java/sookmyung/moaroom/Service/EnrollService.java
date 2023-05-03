package sookmyung.moaroom.Service;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sookmyung.moaroom.Model.Role;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Respository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EnrollService {
    @Autowired
    UserRepository userRepository;

    public String save(JsonObject data){
        try {

            Users existUser = userRepository.findById(UUID.fromString(String.valueOf(data.get("student_id")).substring(1,37))).get();
            if (existUser.equals(null)){
                throw new Exception("존재하지 않는 사용자");
            }
            System.out.println(existUser.getClasses());
            if(existUser.getClasses()==null){
                ArrayList<String> newClass = new ArrayList<>();
                newClass.add(String.valueOf(data.get("lecture_id")).substring(1,37));
                existUser.setClasses(newClass);
            } else{
               ArrayList<String> classList = existUser.getClasses();
               classList.add(String.valueOf(data.get("lecture_id")).substring(1,37));
               existUser.setClasses(classList);
            }
            System.out.println(existUser);
            userRepository.save(existUser);
            return "강의 신청 완료";
        } catch (Exception e){
            return "err: "+e.getMessage();
        }
    }

    public String findStudentList(String lecture_id){
        List<Users> studentList = userRepository.findByRole(Role.STUDENT.getRole());
        JsonObject studentListInLecture = new JsonObject();
        for (int i = 0; i<studentList.size(); i++){
            Users student = studentList.get(i);
            if(student.getClasses()!=null && student.getClasses().contains(lecture_id)){
                JsonObject studentData = new JsonObject();
                studentData.addProperty("name",student.getName());
                studentData.addProperty("user_id", student.getId());
                studentListInLecture.add(student.getUserId().toString(), studentData);
            }
        }

        return studentListInLecture.toString();
    }
}
