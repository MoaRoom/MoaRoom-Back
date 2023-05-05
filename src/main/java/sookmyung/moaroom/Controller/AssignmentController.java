package sookmyung.moaroom.Controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sookmyung.moaroom.Dto.requestAssignmentDto;
import sookmyung.moaroom.Model.Assignment;
import sookmyung.moaroom.Service.AssignmentService;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/assignment/all")
    public List<Assignment> allLecture(){
        return assignmentService.findAll();
    }

    @GetMapping("/assignment/{assignment_id}")
    public Assignment oneAssignment(@PathVariable("assignment_id") String id){
        return assignmentService.findOne(id);
    }

    @DeleteMapping("/assignment/{assignment_id}")
    public String deleteAssignment(@PathVariable("assignment_id") String id){
        return assignmentService.delete(id);
    }
}
