package sookmyung.moaroom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sookmyung.moaroom.Dto.requestEnrollDto;
import sookmyung.moaroom.Dto.requestLectureDto;
import sookmyung.moaroom.Dto.responseLectureDto;
import sookmyung.moaroom.Dto.responseLectureInfoDto;
import sookmyung.moaroom.Model.Lecture;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Service.EnrollService;
import sookmyung.moaroom.Service.LectureService;

import java.util.List;

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

    @GetMapping("/lecture/all/{user_id}")
    public List<responseLectureDto> allLecture(@PathVariable(name = "user_id") String id){
        return lectureService.findAll(id);
    }

    @GetMapping("/lecture/{lecture_id}")
    public Lecture oneLecture(@PathVariable("lecture_id") String id){
        return lectureService.findOne(id);
    }

    @DeleteMapping("/lecture/{lecture_title}/{lecture_room}")
    public String deleteLecture(@PathVariable("lecture_title") String title, @PathVariable("lecture_room") Integer room){
        return lectureService.delete(title, room);
    }

    @PostMapping("/lecture/enroll")
    public String enrollLecture(@Validated @RequestBody requestEnrollDto enroll){
        return enrollService.save(enroll);
    }

    @GetMapping("/lecture/list/{lecture_id}")
    public List<Users> studentList(@PathVariable("lecture_id") String id){
        return enrollService.findStudentList(id);
    }

    @GetMapping("/lecture/info/{assignment_id}")
    public responseLectureInfoDto lectureInfo(@PathVariable("assignment_id") String id){
        return lectureService.findLectureInfo(id);
    }
}
