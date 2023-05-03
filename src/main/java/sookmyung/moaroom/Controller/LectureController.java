package sookmyung.moaroom.Controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    public String addLecture(@RequestBody Map<String, String> data){
        JsonObject newLecture = new JsonObject();
        newLecture.addProperty("title", data.get("title"));
        newLecture.addProperty("professor_id", data.get("professor_id"));
        newLecture.addProperty("room", data.get("room"));

        System.out.println("new Lecture");
        System.out.println(newLecture);
        return lectureService.save(newLecture);
    }

    @PutMapping("/lecture/{lecture_id}")
    public Lecture updateLecture(@PathVariable(name = "lecture_id") String id, @RequestBody Map<String, String> data){
        JsonObject existLecture = new JsonObject();
        existLecture.addProperty("title", data.get("title"));
        existLecture.addProperty("room", data.get("room"));
        existLecture.addProperty("professor_id", data.get("professor_id"));

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
    public String enrollLecture(@RequestBody Map<String, String> data){
        JsonObject enroll = new JsonObject();
        enroll.addProperty("lecture_id", data.get("lecture_id"));
        enroll.addProperty("student_id", data.get("student_id"));

        return enrollService.save(enroll);
    }

    @GetMapping("/lecture/list/{lecture_id}")
    public String studentList(@PathVariable("lecture_id") String id){
        return enrollService.findStudentList(id);
    }
}
