package sookmyung.moaroom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sookmyung.moaroom.Dto.requestAutoScoreDto;
import sookmyung.moaroom.Dto.requestScoreDto;
import sookmyung.moaroom.Dto.responseStepDto;
import sookmyung.moaroom.Model.Step;
import sookmyung.moaroom.Service.StepService;

import java.util.List;

@RestController
public class StepController {
    @Autowired
    StepService stepService;

    @PostMapping("/assignments/score")
    public void scoring(@Validated @RequestBody requestScoreDto score){
        stepService.scoring(score);
    }

    @PostMapping("/assignments/{assignment_id}/auto")
    public void autoScoring(@PathVariable(name = "assignment_id") String id, @Validated @RequestBody requestAutoScoreDto data){
        stepService.autoScoring(id, data);
    }

    @GetMapping("/steps/{assignment_id}")
    public List<responseStepDto> findStepList(@PathVariable(name = "assignment_id") String id){
        return stepService.findStepList(id);
    }

    @GetMapping("/steps")
    public List<Step> allStep() {
        return stepService.allStep();
    }
}
