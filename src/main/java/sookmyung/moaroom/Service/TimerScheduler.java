package sookmyung.moaroom.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TimerScheduler {
    @Autowired
    TaskScheduler scheduler;

    @Autowired
    StepService stepService;

    public void setScheduler(int Case, UUID lectureId, UUID assignmentId, LocalDateTime date) {
        if(Case ==0){

            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    stepService.setStartDate(lectureId, assignmentId);
                }
            }, Timestamp.valueOf(date));
        } else {
            scheduler.schedule(new Runnable() {
                @Override
                public void run() {
                    stepService.setDueDate(lectureId, assignmentId);
                }
            }, Timestamp.valueOf(date));
        }
        
    }

}
