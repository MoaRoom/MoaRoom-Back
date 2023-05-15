package sookmyung.moaroom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sookmyung.moaroom.Dto.requestScoreDto;
import sookmyung.moaroom.Model.Step;
import sookmyung.moaroom.Service.StepService;

import java.util.List;

@RestController
public class StepController {
    @Autowired
    StepService stepService;

    @PostMapping("assignment/score")
    public void scoring(@RequestBody requestScoreDto score){
        stepService.scoring(score);
    }

    @GetMapping("step/all")
    public List<Step> allStep() {
        return stepService.allStep();
    }
}
