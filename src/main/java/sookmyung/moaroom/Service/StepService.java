package sookmyung.moaroom.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sookmyung.moaroom.Dto.requestScoreDto;
import sookmyung.moaroom.Model.*;
import sookmyung.moaroom.Model.Process;
import sookmyung.moaroom.Respository.StepRepository;
import sookmyung.moaroom.Respository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class StepService {
    @Autowired
    UserRepository userRepository;
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
        Users student = userRepository.findById(data.getUserId()).get();
        if(student.getClasses()!=null&student.getClasses().contains(data.getLectureId().toString())){
            StepPK stepPK = new StepPK(data.getAssignmentId(), data.getLectureId(), data.getUserId());
            Step existStep = stepRepository.findById(stepPK).get();
            existStep.setStep(Process.DONE.getRole());
            existStep.setScore(data.getScore());
            stepRepository.save(existStep);
        }

    }

    @Transactional
    public void setStartDate(UUID lectureId, UUID assignmentId){
        List<Users> userList = userRepository.findAll();
        for (int i = 0; i<userList.size(); i++){
            Users user = userList.get(i);
            if(user.getClasses()!=null && user.getClasses().contains(lectureId.toString())){
                StepPK stepPK = new StepPK(assignmentId, lectureId, user.getUserId());
                if(!stepRepository.findById(stepPK).isEmpty()){
                    Step existStep = stepRepository.getReferenceById(stepPK);
                    existStep.setStep(Process.PROCEEDING.getRole());
                    stepRepository.save(existStep);
                } else {
                    Step newStep = new Step();
                    newStep.setStep(Process.PROCEEDING.getRole());
                    newStep.setAssignmentId(assignmentId);
                    newStep.setUserId(user.getUserId());
                    newStep.setLectureId(lectureId);

                    stepRepository.save(newStep);
                }
            }
        }
    }
    @Transactional
    public void setDueDate(UUID lectureId, UUID assignmentId){
        List<Users> userList = userRepository.findAll();
        for (int i = 0; i<userList.size(); i++){
            Users user = userList.get(i);
            if(user.getClasses()!=null && user.getClasses().contains(lectureId.toString())){
                StepPK stepPK = new StepPK(assignmentId, lectureId, user.getUserId());
                Step existStep = stepRepository.findById(stepPK).get();
                existStep.setStep(Process.GRADING.getRole());
                stepRepository.save(existStep);
            }
        }
    }

    public List<Step> allStep(){
        return stepRepository.findAll();
    }
}
