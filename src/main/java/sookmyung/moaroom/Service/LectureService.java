package sookmyung.moaroom.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sookmyung.moaroom.Dto.requestLectureDto;
import sookmyung.moaroom.Dto.responseLectureDto;
import sookmyung.moaroom.Model.Lecture;
import sookmyung.moaroom.Model.Role;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Respository.LectureRepository;
import sookmyung.moaroom.Respository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LectureService {
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EnrollService enrollService;

    public String save(requestLectureDto data) {
        try {
            Lecture newLecture = new Lecture();
            newLecture.setLectureId(UUID.randomUUID());
            newLecture.setTitle(data.getTitle());
            newLecture.setProfessorId(data.getProfessorId());
            newLecture.setRoom(data.getRoom());

            if (data.getRoomCount()!= null){
                newLecture.setRoomCount(data.getRoomCount());
            } else {
                newLecture.setRoomCount(30);
            }

            lectureRepository.save(newLecture);
            enrollService.enroll(newLecture.getProfessorId(), newLecture.getLectureId());


            return "새로운 강의 등록 완료";
        } catch (Exception e) {
            e.printStackTrace();
            return "err: "+e.getMessage();
        }
    }

    public Lecture modify(String lecture_id, requestLectureDto data){
        try{

            Lecture existLecture = lectureRepository.findById(UUID.fromString(lecture_id)).get();
            if (lectureRepository.findById(UUID.fromString(lecture_id)).isEmpty()){
                throw new Exception("존재하지 않는 강의");
            }
            if (!data.getProfessorId().equals(existLecture.getProfessorId())){
                throw new Exception("수정 권한이 없는 사용자");
            }

            existLecture.setTitle(data.getTitle());
            existLecture.setRoom(data.getRoom());
            existLecture.setRoomCount(data.getRoomCount());

            return lectureRepository.save(existLecture);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("err: "+e.getMessage());
        }
        return null;
    }

    public List<responseLectureDto> findAll(String user_id){
        List<responseLectureDto> lectureList = new ArrayList<>();
        try{
            if(lectureRepository.findAll().isEmpty()){
                throw new Exception("강의 없음");
            }
            Users loginUser = userRepository.getById(UUID.fromString(user_id));
            if(loginUser.getRole() == Role.PROFESSOR.getRole()){
                List<String> classes = loginUser.getClasses();
                for(int i=0; i<classes.size(); i++){
                    String lecture_id = classes.get(i);
                    responseLectureDto lectureDto = new responseLectureDto();
                    Lecture lecture = lectureRepository.findById(UUID.fromString(lecture_id)).get();

                    lectureDto.setTitle(lecture.getTitle());
                    lectureDto.setRoom(lecture.getRoom());
                    lectureDto.setEnroll(Boolean.TRUE);
                    lectureDto.setProfessor_name(loginUser.getName());

                    lectureList.add(lectureDto);
                }
            } else if (loginUser.getRole() == Role.STUDENT.getRole()) {
                List<Lecture> lectures = lectureRepository.findAll();
                for(int i=0; i<lectures.size(); i++){
                    responseLectureDto lectureDto = new responseLectureDto();
                    Lecture lecture = lectures.get(i);

                    if(loginUser.getClasses().contains(lecture.getLectureId().toString())){
                        lectureDto.setEnroll(Boolean.TRUE);
                    } else{
                        lectureDto.setEnroll(Boolean.FALSE);
                    }

                    lectureDto.setTitle(lecture.getTitle());
                    lectureDto.setRoom(lecture.getRoom());

                    Users professor = userRepository.findById(lecture.getProfessorId()).get();
                    lectureDto.setProfessor_name(professor.getName());

                    lectureList.add(lectureDto);
                }
            }
            return lectureList;

        } catch (Exception e){
            System.out.println("err: "+e.getMessage());
        }
        return null;
    }

    public Lecture findOne(String id){
        try {
            Lecture lecture = lectureRepository.findById(UUID.fromString(id)).get();
            if (lectureRepository.findById(UUID.fromString(id)).isEmpty()){
                throw new Exception("찾는 강의 없음");
            }
            return lecture;
        } catch (Exception e){
            System.out.println("err: "+e.getMessage());
        }
        return null;
    }

    public String delete(String id, String professor_id){
        try {
            if(lectureRepository.findById(UUID.fromString(id)).isEmpty()){
                throw new Exception("존재하지 않는 강의");
            }
            if (!lectureRepository.findById(UUID.fromString(id)).get().getProfessorId().toString().equals(professor_id)){
                throw new Exception("삭제 권한이 없는 사용자");
            }

            lectureRepository.deleteById(UUID.fromString(id));
            return "삭제 성공";
        } catch (Exception e){
            return "err: "+e.getMessage();
        }
    }
}
