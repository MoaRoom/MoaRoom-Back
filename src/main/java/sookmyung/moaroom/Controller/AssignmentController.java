package sookmyung.moaroom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sookmyung.moaroom.Dto.requestAssignmentDto;
import sookmyung.moaroom.Dto.responseAssignmentDto;
import sookmyung.moaroom.Model.Assignment;
import sookmyung.moaroom.Model.Url;
import sookmyung.moaroom.Respository.AssignmentRepository;
import sookmyung.moaroom.Service.AssignmentService;

import java.util.List;

@RestController
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    // test용
    @Autowired
    AssignmentRepository assignmentRepository;

    @PostMapping("/assignment")
    public String addAssignment(@Validated @RequestBody requestAssignmentDto newAssignment){
        return assignmentService.save(newAssignment);
    }

    @PutMapping("/assignments/{assignment_id}")
    public Assignment updateAssignment(@PathVariable(name = "assignment_id") String id, @Validated @RequestBody requestAssignmentDto existAssignment){
        return assignmentService.modify(id, existAssignment);
    }

    @GetMapping("/assignments/users/{user_id}")
    public List<responseAssignmentDto> allLecture(@PathVariable("user_id") String id){
        return assignmentService.findAll(id);
    }

    @GetMapping("/assignments/{assignment_id}")
    public Assignment oneAssignment(@PathVariable("assignment_id") String id){
        return assignmentService.findOne(id);
    }

    @DeleteMapping("/assignments/{assignment_title}")
    public String deleteAssignment(@PathVariable("assignment_title") String title){
        return assignmentService.delete(title);
    }

    @GetMapping("/assignments/{assignment_id}/urls")
    public List<Url> studentUrlList(@PathVariable("assignment_id") String id){
        return assignmentService.findStudentUrlList(id);
    }

    // test용
    @GetMapping("/assignments")
    public List<Assignment> allLecture(){
        return assignmentRepository.findAll();
    }

}
