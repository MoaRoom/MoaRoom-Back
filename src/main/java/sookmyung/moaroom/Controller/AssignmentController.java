package sookmyung.moaroom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sookmyung.moaroom.Dto.requestAssignmentDto;
import sookmyung.moaroom.Dto.responseAssignmentDto;
import sookmyung.moaroom.Model.Assignment;
import sookmyung.moaroom.Model.Url;
import sookmyung.moaroom.Service.AssignmentService;

import java.util.List;

@RestController
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    @PostMapping("/assignment/new")
    public String addAssignment(@Validated @RequestBody requestAssignmentDto newAssignment){
        return assignmentService.save(newAssignment);
    }

    @PutMapping("/assignment/{assignment_id}")
    public Assignment updateAssignment(@PathVariable(name = "assignment_id") String id, @Validated @RequestBody requestAssignmentDto existAssignment){
        return assignmentService.modify(id, existAssignment);
    }

    @GetMapping("/assignment/all/{user_id}")
    public List<responseAssignmentDto> allLecture(@PathVariable("user_id") String id){
        return assignmentService.findAll(id);
    }

    @GetMapping("/assignment/{assignment_id}")
    public Assignment oneAssignment(@PathVariable("assignment_id") String id){
        return assignmentService.findOne(id);
    }

    @DeleteMapping("/assignment/{assignment_title}")
    public String deleteAssignment(@PathVariable("assignment_title") String title){
        return assignmentService.delete(title);
    }

    @GetMapping("/assignment/list/{assignment_id}")
    public List<Url> studentUrlList(@PathVariable("assignment_id") String id){
        return assignmentService.findStudentUrlList(id);
    }
}
