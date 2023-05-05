package sookmyung.moaroom.Service;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sookmyung.moaroom.Dto.requestEnrollDto;
import sookmyung.moaroom.Dto.responseEnrollDto;
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
            return "강의 신청 완료";
        } catch (Exception e){
            return "err: "+e.getMessage();
        }
    }

    public List<responseEnrollDto> findStudentList(String lecture_id){
        List<Users> studentList = userRepository.findByRole(Role.STUDENT.getRole());
        List<responseEnrollDto> studentListInLecture = new ArrayList<>();
        for (int i = 0; i<studentList.size(); i++){
            Users student = studentList.get(i);
            if(student.getClasses()!=null && student.getClasses().contains(lecture_id)){
                responseEnrollDto studentData = new responseEnrollDto();
                studentData.setStudentId(student.getUserId());
                studentData.setId(student.getId());
                studentData.setName(student.getName());
                studentListInLecture.add(studentData);
            }
        }

        return studentListInLecture;
    }
}
