package sookmyung.moaroom.Controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sookmyung.moaroom.Dto.requestEnrollDto;
import sookmyung.moaroom.Dto.requestLectureDto;
import sookmyung.moaroom.Dto.responseEnrollDto;
import sookmyung.moaroom.Model.Lecture;
import sookmyung.moaroom.Service.EnrollService;
import sookmyung.moaroom.Service.LectureService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class LectureController {

    @Autowired
    LectureService lectureService;
    @Autowired
    EnrollService enrollService;

    @PostMapping("/lecture/new")
    public String addLecture(@Validated @RequestBody requestLectureDto newLecture){
        return lectureService.save(newLecture);
    }

    @PutMapping("/lecture/{lecture_id}")
    public Lecture updateLecture(@PathVariable(name = "lecture_id") String id, @Validated @RequestBody requestLectureDto existLecture){
        return lectureService.modify(id, existLecture);
    }

    @GetMapping("/lecture/all")
    public List<Lecture> allLecture(){
        return lectureService.findAll();
    }

    @GetMapping("/lecture/{lecture_id}")
    public Lecture oneLecture(@PathVariable("lecture_id") String id){
        return lectureService.findOne(id);
    }

    @DeleteMapping("/lecture/{lecture_id}")
    public String deleteLecture(@PathVariable("lecture_id") String id, @RequestParam("professor_id") String professor_id){
        return lectureService.delete(id, professor_id);
    }

    @PostMapping("/lecture/enroll")
    public String enrollLecture(@Validated @RequestBody requestEnrollDto enroll){
        return enrollService.save(enroll);
    }

    @GetMapping("/lecture/list/{lecture_id}")
    public List<responseEnrollDto> studentList(@PathVariable("lecture_id") String id){
        return enrollService.findStudentList(id);
    }
}
