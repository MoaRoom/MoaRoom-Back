package sookmyung.moaroom.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sookmyung.moaroom.Dto.requestAssignmentDto;
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

    public String save(requestAssignmentDto data) {
            Assignment newAssignment = new Assignment();
            newAssignment.setAssignmentId(UUID.randomUUID());
            newAssignment.setTitle(data.getTitle());
            newAssignment.setLectureId(data.getLectureId());

            if (data.getStartDate()==null) {
                LocalDateTime now = LocalDateTime.now();
                newAssignment.setStartDate(now);
            } else{
                newAssignment.setStartDate(data.getStartDate());
            }

            if (data.getDueDate()!=null) {
                newAssignment.setDueDate(data.getDueDate());
            } else{
                newAssignment.setDueDate(null);
            }

            if (data.getDescription()!=null) {
                newAssignment.setDescription(data.getDescription());
            } else{
                newAssignment.setDescription(null);
            }

            assignmentRepository.save(newAssignment);
            return "새로운 과제 등록 완료";
    }

    public Assignment modify(String assignment_id, requestAssignmentDto data){
        try{
            AssignmentPK assignmentPK = new AssignmentPK();
            assignmentPK.setAssignmentId(UUID.fromString(assignment_id));
            assignmentPK.setLectureId(data.getLectureId());
            System.out.println(assignmentPK);
            Assignment existAssignment = assignmentRepository.findById(assignmentPK).get();
            System.out.println(existAssignment);
            if (assignmentRepository.findById(assignmentPK).isEmpty()){
                throw new Exception("존재하지 않는 과제");
            }

            existAssignment.setTitle(data.getTitle());

            if (data.getStartDate()!=null) {
                existAssignment.setStartDate(data.getStartDate());
            }
            if (data.getDueDate()!=null) {
                existAssignment.setDueDate(data.getDueDate());
            }
            if (data.getDescription()!=null) {
                existAssignment.setDescription(data.getDescription());
            }

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
}
