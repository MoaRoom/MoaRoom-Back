package sookmyung.moaroom.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sookmyung.moaroom.Dto.requestAutoScoreDto;
import sookmyung.moaroom.Dto.requestScoreDto;
import sookmyung.moaroom.Dto.responseStepDto;
import sookmyung.moaroom.Model.*;
import sookmyung.moaroom.Model.Process;
import sookmyung.moaroom.Respository.AssignmentRepository;
import sookmyung.moaroom.Respository.StepRepository;
import sookmyung.moaroom.Respository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StepService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AssignmentRepository assignmentRepository;
    @Autowired
    StepRepository stepRepository;
    @Autowired
    TimerScheduler timer;

    // 진행중(0)
    public void startAssignmentNow(UUID lectureId, UUID assignmentId, LocalDateTime dueDate){
        List<Users> userList = userRepository.findAll();
        for (int i = 0; i<userList.size(); i++){
            Users user = userList.get(i);
            if(user.getClasses()!=null && user.getClasses().contains(lectureId.toString())){
                Step newStep = new Step();
                newStep.setStep(Process.PROCEEDING.getRole());
                newStep.setAssignmentId(assignmentId);
                newStep.setUserId(user.getUserId());
                newStep.setLectureId(lectureId);

                stepRepository.save(newStep);
            }
        }
        if (dueDate != null){
            timer.setScheduler(1,lectureId, assignmentId, dueDate);

        }
    }

    // 진행 대기중(1)
    public void startAssignmentLater(UUID lectureId, UUID assignmentId, LocalDateTime startDate, LocalDateTime dueDate){
        List<Users> professorList = userRepository.findByRole(Role.PROFESSOR.getRole());
        for (int i = 0; i<professorList.size(); i++){
            Users user = professorList.get(i);
            if(user.getClasses()!=null && user.getClasses().contains(lectureId.toString())){
                Step newStep = new Step();
                newStep.setStep(Process.WAITING.getRole()) ;
                newStep.setAssignmentId(assignmentId);
                newStep.setUserId(user.getUserId());
                newStep.setLectureId(lectureId);
                stepRepository.save(newStep);
            }
        }

        // 해당 시간에 실행하도록
        timer.setScheduler(0,lectureId, assignmentId, startDate);

        if(dueDate != null){
            timer.setScheduler(1,lectureId, assignmentId, dueDate);
        }

    }

    public void scoring(requestScoreDto data){
        Users student = userRepository.findById(data.getUser_id()).get();
        if(student.getClasses()!=null&student.getClasses().contains(data.getLecture_id().toString())){
            StepPK stepPK = new StepPK(data.getAssignment_id(), data.getLecture_id(), data.getUser_id());
            Step existStep = stepRepository.findById(stepPK).get();
            existStep.setStep(Process.DONE.getRole());
            existStep.setScore(data.getScore());
            stepRepository.save(existStep);
        }

    }

    public void autoScoring(String id, requestAutoScoreDto data){
        Step step = stepRepository.findByAssignmentIdAndUserId(UUID.fromString(id),data.getUser_id());
        //if (step.getStep() != Process.DONE.getRole()){
            // 과제 아이디로 정답과 런타임 불러오기
            Assignment assignment = assignmentRepository.findByAssignmentId(UUID.fromString(id));

            // 데이터 안에 있는 답과 런타임과 비교
            int score = 0;
            if(assignment.getAnswer().equals(data.getAnswer()) && assignment.getRuntime() >= data.getRuntime()){
                score = 100;
            }

            // 진행상황과 점수 반영 - step table , 점수는 0 또는 100
            step.setStep(3);
            step.setScore(score);
            stepRepository.save(step);
        //}
    }

    public List<responseStepDto> findStepList(String assignment_id){
        List<Step> stepList = stepRepository.findByAssignmentId(UUID.fromString(assignment_id));
        List<responseStepDto> stepDtoList = new ArrayList<>();
        for (int i=0; i<stepList.size(); i++){
            Step tempStep = stepList.get(i);
            Users tempUser = userRepository.findById(tempStep.getUserId()).get();
            if(tempUser != null && tempUser.getRole() == Role.STUDENT.getRole()){
                responseStepDto step = new responseStepDto();
                step.setStep(tempStep.getStep());
                step.setId(tempUser.getUserId());
                step.setScore(tempStep.getScore());
                step.setName(tempUser.getName());

                stepDtoList.add(step);
            }
        }
        return stepDtoList;
    }

    public List<Step> allStep(){
        return stepRepository.findAll();
    }
}
