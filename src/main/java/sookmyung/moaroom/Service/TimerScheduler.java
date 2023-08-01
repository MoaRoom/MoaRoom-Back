package sookmyung.moaroom.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sookmyung.moaroom.Model.Process;
import sookmyung.moaroom.Model.Step;
import sookmyung.moaroom.Model.StepPK;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Respository.StepRepository;
import sookmyung.moaroom.Respository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Component
public class TimerScheduler {
    @Autowired
    UserRepository userRepository;
    @Autowired
    StepRepository stepRepository;
    public void setScheduler(int Case, UUID lectureId, UUID assignmentId, LocalDateTime date) {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.setThreadNamePrefix("scheduled-thread-pool-");
        scheduler.initialize();
        if(Case ==0){
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    setStartDate(lectureId, assignmentId);
                }
            }, Timestamp.valueOf(date));
        } else {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    setDueDate(lectureId, assignmentId);
                }
            }, Timestamp.valueOf(date));
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
                    Step existStep = stepRepository.findById(stepPK).get();
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

}
