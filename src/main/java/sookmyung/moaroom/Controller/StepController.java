package sookmyung.moaroom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sookmyung.moaroom.Dto.requestScoreDto;
import sookmyung.moaroom.Dto.responseStepDto;
import sookmyung.moaroom.Model.Step;
import sookmyung.moaroom.Service.StepService;

import java.util.List;

@RestController
public class StepController {
    @Autowired
    StepService stepService;

    @PostMapping("/assignment/score")
    public void scoring(@RequestBody requestScoreDto score){
        stepService.scoring(score);
    }

    @GetMapping("/step/{assignment_id}")
    public List<responseStepDto> findStepList(@PathVariable(name = "assignment_id") String id){
        return stepService.findStepList(id);
    }

    @GetMapping("/step/all")
    public List<Step> allStep() {
        return stepService.allStep();
    }
}
