package sookmyung.moaroom.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sookmyung.moaroom.Dto.requestLectureDto;
import sookmyung.moaroom.Dto.responseLectureDto;
import sookmyung.moaroom.Dto.responseLectureInfoDto;
import sookmyung.moaroom.Model.Assignment;
import sookmyung.moaroom.Model.Lecture;
import sookmyung.moaroom.Model.Role;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Respository.AssignmentRepository;
import sookmyung.moaroom.Respository.LectureRepository;
import sookmyung.moaroom.Respository.UserRepository;

import java.util.*;

@Service
public class LectureService {
    @Autowired
    LectureRepository lectureRepository;
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EnrollService enrollService;

    public String save(requestLectureDto data) {
        try {
            Lecture newLecture = new Lecture();
            newLecture.setLectureId(UUID.randomUUID());
            newLecture.setTitle(data.getTitle());
            newLecture.setProfessorId(data.getProfessor_id());
            newLecture.setRoom(data.getRoom());

            if (data.getRoom_count()!= null){
                newLecture.setRoomCount(data.getRoom_count());
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
            if (!data.getProfessor_id().equals(existLecture.getProfessorId())){
                throw new Exception("수정 권한이 없는 사용자");
            }

            existLecture.setTitle(data.getTitle());
            existLecture.setRoom(data.getRoom());
            existLecture.setRoomCount(data.getRoom_count());

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

                    lectureDto.setLecture_id(lecture.getLectureId().toString());
                    lectureDto.setTitle(lecture.getTitle());
                    lectureDto.setRoom(lecture.getRoom());
                    lectureDto.setProfessor_name(loginUser.getName());

                    lectureList.add(lectureDto);
                }
            } else if (loginUser.getRole() == Role.STUDENT.getRole()) {
                List<Lecture> lectures = lectureRepository.findAll();
                for(int i=0; i<lectures.size(); i++){
                    responseLectureDto lectureDto = new responseLectureDto();
                    Lecture lecture = lectures.get(i);
                    if(loginUser.getClasses() == null){
                        lectureDto.setEnroll(Boolean.FALSE);
                    } else if (loginUser.getClasses().contains(lecture.getLectureId().toString())) {
                        lectureDto.setEnroll(Boolean.TRUE);
                    } else{
                        lectureDto.setEnroll(Boolean.FALSE);
                    }

                    lectureDto.setLecture_id(lecture.getLectureId().toString());
                    lectureDto.setTitle(lecture.getTitle());
                    lectureDto.setRoom(lecture.getRoom());

                    Users professor = userRepository.findById(lecture.getProfessorId()).get();
                    lectureDto.setProfessor_name(professor.getName());
                    System.out.println(lectureDto);
                    lectureList.add(lectureDto);
                }
            }
            return lectureList;

        } catch (Exception e){
            e.printStackTrace();
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

    public String delete(String title, Integer room){
        try {
            lectureRepository.deleteByTitleAndRoom(title, room);
            return "삭제 성공";
        } catch (Exception e){
            return "err: "+e.getMessage();
        }
    }

    public responseLectureInfoDto findLectureInfo(String assignment_id){
        Assignment assignment = assignmentRepository.findByAssignmentId(UUID.fromString(assignment_id));
        Lecture lecture = lectureRepository.findById(assignment.getLectureId()).get();
        Users professor = userRepository.findById(lecture.getProfessorId()).get();
        if(lecture != null && professor != null){
            responseLectureInfoDto lectureInfo = new responseLectureInfoDto();
            lectureInfo.setLecture_id(lecture.getLectureId());
            lectureInfo.setProfessor_id(professor.getUserId());
            lectureInfo.setProfessor_name(professor.getName());
            lectureInfo.setRoom(lecture.getRoom());
            lectureInfo.setTitle(lecture.getTitle());
            return lectureInfo;
        }
        return null;
    }
}
