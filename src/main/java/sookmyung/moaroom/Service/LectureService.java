package sookmyung.moaroom.Service;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sookmyung.moaroom.Model.Lecture;
import sookmyung.moaroom.Respository.LectureRepository;

import java.util.List;
import java.util.UUID;

@Service
public class LectureService {
    @Autowired
    LectureRepository lectureRepository;

    public String save(JsonObject data) {
        try {
            if (data.get("title").isJsonNull() || data.get("professor_id").isJsonNull() || data.get("room").isJsonNull()) {
                throw new Exception("new Lecture 필수 정보 미입력");
            }

            System.out.println("Save");
            System.out.println(data);
            Lecture newLecture = new Lecture();
            newLecture.setLectureId(UUID.randomUUID());
            newLecture.setTitle(String.valueOf(data.get("title")));

            // professor_id에 따옴표도 포함되어 있으므로, 슬라이싱으로 제거 후 UUID로 변환해야함
            String professor_id = data.get("professor_id").toString();
            newLecture.setProfessorId(UUID.fromString(professor_id.substring(1,37)));
            newLecture.setRoom(data.get("room").getAsInt());

            lectureRepository.save(newLecture);
            return "새로운 강의 등록 완료";
        } catch (Exception e) {
            e.printStackTrace();
            return "err: "+e.getMessage();
        }
    }

    public Lecture modify(String lecture_id, JsonObject data){
        try{

            Lecture existLecture = lectureRepository.findById(UUID.fromString(lecture_id)).get();
            if (lectureRepository.findById(UUID.fromString(lecture_id)).isEmpty()){
                throw new Exception("존재하지 않는 강의");
            }
            if (data.get("professor_id").equals(existLecture.getProfessorId().toString())){
                throw new Exception("수정 권한이 없는 사용자");
            }

            existLecture.setTitle(String.valueOf(data.get("title")));
            existLecture.setRoom(data.get("room").getAsInt());

            return lectureRepository.save(existLecture);
        } catch (Exception e){
            System.out.println("err: "+e.getMessage());
        }
        return null;
    }

    public List<Lecture> findAll(){
        try{
            if(lectureRepository.findAll().isEmpty()){
                throw new Exception("강의 없음");
            }
            return lectureRepository.findAll();

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
