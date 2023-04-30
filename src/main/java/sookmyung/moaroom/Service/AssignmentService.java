package sookmyung.moaroom.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sookmyung.moaroom.Model.Assignment;
import sookmyung.moaroom.Model.AssignmentPK;
import sookmyung.moaroom.Respository.AssignmentRepository;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class AssignmentService {
    @Autowired
    AssignmentRepository assignmentRepository;

    public String save(JsonObject data) {
        try {
            if (data.get("title").isJsonNull() || data.get("lecture_id").isJsonNull()) {
                throw new Exception("new Assignment 필수 정보 미입력");
            }

            Assignment newAssignment = new Assignment();
            newAssignment.setAssignmentId(UUID.randomUUID());
            newAssignment.setTitle(String.valueOf(data.get("title")));

            // professor_id에 따옴표도 포함되어 있으므로, 슬라이싱으로 제거 후 UUID로 변환해야함
            String lecture_id = data.get("lecture_id").toString();
            newAssignment.setLectureId(UUID.fromString(lecture_id.substring(1,37)));

            if (data.has("start_date")) {
                LocalDateTime startDate = stringToDate(String.valueOf(data.get("start_date")));
                newAssignment.setStartDate(startDate);
            } else{
                LocalDateTime now = LocalDateTime.now();
                newAssignment.setStartDate(now);
            }

            if (data.has("due_date")) {
                LocalDateTime dueDate = stringToDate(String.valueOf(data.get("due_date")));
                newAssignment.setDueDate(dueDate);
            } else{
                newAssignment.setDueDate(null);
            }

            if (data.has("description")) {
                newAssignment.setDescription(String.valueOf(data.get("description")));
            } else{
                newAssignment.setDescription(null);
            }

            assignmentRepository.save(newAssignment);
            return "새로운 과제 등록 완료";
        } catch (Exception e) {
            e.printStackTrace();
            return "err: "+e.getMessage();
        }
    }

    public Assignment modify(String assignment_id, JsonObject data){
        try{
            AssignmentPK assignmentPK = new AssignmentPK();
            assignmentPK.setAssignmentId(UUID.fromString(assignment_id));
            // lecture_id에 따옴표도 포함되어 있으므로, 슬라이싱으로 제거 후 UUID로 변환해야함
            String lecture_id = data.get("lecture_id").toString();
            assignmentPK.setLectureId(UUID.fromString(lecture_id.substring(1,37)));
            Assignment existAssignment = assignmentRepository.findById(assignmentPK).get();
            if (assignmentRepository.findById(assignmentPK).isEmpty()){
                throw new Exception("존재하지 않는 과제");
            }

            existAssignment.setTitle(String.valueOf(data.get("title")));

            LocalDateTime startDate = stringToDate(String.valueOf(data.get("start_date")));
            existAssignment.setStartDate(startDate);

            System.out.println(data.get("due_date").getClass().getName());
            if (data.get("due_date").isJsonNull()){
                existAssignment.setDueDate(null);
            } else{
                LocalDateTime dueDate = stringToDate(String.valueOf(data.get("due_date")));
                existAssignment.setDueDate(dueDate);
            }


            existAssignment.setDescription(String.valueOf(data.get("description")));

            return assignmentRepository.save(existAssignment);
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("err: "+e.getMessage());
        }
        return null;
    }

    public List<Assignment> findAll(){
        try{
            if(assignmentRepository.findAll().isEmpty()){
                throw new Exception("과제 없음");
            }
            return assignmentRepository.findAll();

        } catch (Exception e){
            System.out.println("err: "+e.getMessage());
        }
        return null;
    }

    public Assignment findOne(String id){
        try {
            Assignment assignment = assignmentRepository.findByAssignmentId(UUID.fromString(id));
            if (assignment.equals(null)){
                throw new Exception("찾는 과제 없음");
            }
            return assignment;
        } catch (Exception e){
            System.out.println("err: "+e.getMessage());
        }
        return null;
    }

    public String delete(String id){
        try {
            if(assignmentRepository.findByAssignmentId(UUID.fromString(id)).equals(null)){
                throw new Exception("존재하지 않는 강의");
            }

            assignmentRepository.deleteByAssignmentId(UUID.fromString(id));
            return "삭제 성공";
        } catch (Exception e){
            return "err: "+e.getMessage();
        }
    }

    public LocalDateTime stringToDate(String strDate) throws ParseException {
        System.out.println("1: "+strDate);
        strDate = strDate.substring(1,20);
        System.out.println("2: "+strDate);
        LocalDateTime date = LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return date;
    }

    public String dateToString(LocalDateTime date){
        String strDate = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        return strDate;
    }
}
