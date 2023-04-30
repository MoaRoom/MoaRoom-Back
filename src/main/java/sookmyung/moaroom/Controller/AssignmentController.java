package sookmyung.moaroom.Controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sookmyung.moaroom.Model.Assignment;
import sookmyung.moaroom.Service.AssignmentService;

import java.util.List;
import java.util.Map;

@RestController
public class AssignmentController {

    @Autowired
    AssignmentService assignmentService;

    @PostMapping("/assignment/new")
    public String addAssignment(@RequestBody Map<String, String> data){
        JsonObject newAssignment = new JsonObject();
        newAssignment.addProperty("lecture_id", data.get("lecture_id"));
        newAssignment.addProperty("title", data.get("title"));

        if(data.containsKey("start_date")){
            newAssignment.addProperty("start_date", data.get("start_date"));
        }
        if(data.containsKey("due_date")){
            newAssignment.addProperty("due_date", data.get("due_date"));
        }
        if(data.containsKey("description")){
            newAssignment.addProperty("description", data.get("description"));
        }

        return assignmentService.save(newAssignment);
    }

    @PutMapping("/assignment/{assignment_id}")
    public Assignment updateAssignment(@PathVariable(name = "assignment_id") String id, @RequestBody Map<String, String> data){
        JsonObject existAssignment = new JsonObject();
        existAssignment.addProperty("lecture_id", data.get("lecture_id"));
        existAssignment.addProperty("title", data.get("title"));

        existAssignment.addProperty("start_date", data.get("start_date"));
        existAssignment.addProperty("due_date", data.get("due_date"));
        existAssignment.addProperty("description", data.get("description"));


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
