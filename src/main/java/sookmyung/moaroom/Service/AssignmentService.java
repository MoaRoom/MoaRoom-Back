package sookmyung.moaroom.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sookmyung.moaroom.Dto.requestAssignmentDto;
import sookmyung.moaroom.Dto.responseAssignmentDto;
import sookmyung.moaroom.Model.*;
import sookmyung.moaroom.Respository.AssignmentRepository;
import sookmyung.moaroom.Respository.StepRepository;
import sookmyung.moaroom.Respository.UrlRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.UUID;

@Service
public class AssignmentService {
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    StepRepository stepRepository;
    @Autowired
    UrlRepository urlRepository;
    @Autowired
    StepService stepService;
    @Autowired
    EnrollService enrollService;

    public String save(requestAssignmentDto data) {
        int step = -1;
        Assignment newAssignment = new Assignment();
        newAssignment.setAssignmentId(UUID.randomUUID());
        newAssignment.setTitle(data.getTitle().toString());
        newAssignment.setLectureId(data.getLecture_id());
        if (data.getStart_date()==null) {
            LocalDateTime now = LocalDateTime.now();
            newAssignment.setStartDate(now);
            step = 0;
        } else{
            newAssignment.setStartDate(data.getStart_date());
            step = 1;
        }
        if (data.getDue_date()!=null) {
            newAssignment.setDueDate(data.getDue_date());
        } else{
            newAssignment.setDueDate(null);
        }
        if (data.getDescription()!=null) {
            newAssignment.setDescription(data.getDescription());
        } else{
            newAssignment.setDescription(null);
        }

        assignmentRepository.save(newAssignment);

        Url professorUrl = urlRepository.findById(data.getProfessor_id()).get();
        final String user_url = professorUrl.getApiEndpoint();

        // infra측에 req: users model, res: url model
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HashMap<String, Object> reqBody = new HashMap<>();
        reqBody.put("assignment_id", newAssignment.getAssignmentId().toString());
        reqBody.put("lecture_id", newAssignment.getLectureId().toString());
        reqBody.put("title", newAssignment.getTitle());
        reqBody.put("start_date", newAssignment.getStartDate());
        reqBody.put("due_date", newAssignment.getDueDate());
        reqBody.put("description", newAssignment.getDescription());
        HttpEntity<?> request = new HttpEntity<>(reqBody, headers);
        ResponseEntity<Boolean> response = restTemplate.postForEntity(
                user_url+"/assignments/",
                request,
                Boolean.class
        );

        // 진행상황 반영
        switch (step){
            case 0: // 등록 시간 = 과제 시작 시간
                stepService.startAssignmentNow(newAssignment.getLectureId(), newAssignment.getAssignmentId(), newAssignment.getDueDate());
                break;
            case 1:
                stepService.startAssignmentLater(newAssignment.getLectureId(), newAssignment.getAssignmentId(), newAssignment.getStartDate(), newAssignment.getDueDate());
                break;
            default:
                break;
        }

            return "새로운 과제 등록 완료";
    }

    public Assignment modify(String assignment_id, requestAssignmentDto data){
        try{
            AssignmentPK assignmentPK = new AssignmentPK();
            assignmentPK.setAssignmentId(UUID.fromString(assignment_id));
            assignmentPK.setLectureId(data.getLecture_id());
            Assignment existAssignment = assignmentRepository.findById(assignmentPK).get();
            if (assignmentRepository.findById(assignmentPK).isEmpty()){
                throw new Exception("존재하지 않는 과제");
            }

            existAssignment.setTitle(data.getTitle());

            if (data.getStart_date()!=null) {
                existAssignment.setStartDate(data.getStart_date());
            }
            if (data.getDue_date()!=null) {
                existAssignment.setDueDate(data.getDue_date());
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

    public List<responseAssignmentDto> findAll(String user_id){
        try{
            if(assignmentRepository.findAll().isEmpty()){
                throw new Exception("과제 없음");
            }

            List<Step> stepList = stepRepository.findByUserId(UUID.fromString(user_id));
            if(stepList != null){
                List<responseAssignmentDto> responseAssignmentDtoList = new ArrayList<>();
                for(int i=0; i<stepList.size(); i++){
                    Step step = stepList.get(i);
                    responseAssignmentDto assignmentDto = new responseAssignmentDto();
                    Assignment assignment = assignmentRepository.findByAssignmentId(step.getAssignmentId());
                    assignmentDto.setLecture_id(step.getLectureId());
                    assignmentDto.setAssignment_id(step.getAssignmentId());
                    assignmentDto.setStep(step.getStep());
                    assignmentDto.setTitle(assignment.getTitle());
                    assignmentDto.setScore(step.getScore());

                    responseAssignmentDtoList.add(assignmentDto);
                }
                return responseAssignmentDtoList;
            }
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

    public String delete(String title){
        try {
            assignmentRepository.deleteByTitle(title);
            return "삭제 성공";
        } catch (Exception e){
            return "err: "+e.getMessage();
        }
    }

    public List<Url> findStudentUrlList(String id){
        UUID lectureId = assignmentRepository.findByAssignmentId(UUID.fromString(id)).getLectureId();
        List<Users> usersList = enrollService.findStudentList(lectureId.toString());
        List<Url> studentUrlList = new ArrayList<>();
        for (int i=0; i<usersList.size();i++){
            Users student = usersList.get(i);
            Url existUrl = urlRepository.findById(student.getUserId()).get();
            if (existUrl != null){
                studentUrlList.add(existUrl);
            }
        }
        return studentUrlList;
    }
}
