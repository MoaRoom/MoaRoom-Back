package sookmyung.moaroom.Controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sookmyung.moaroom.Dto.requestUserDto;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Service.UserService;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/new")
    public String addUser(@Validated @RequestBody requestUserDto newUser){
        return userService.save(newUser);
    }

    @PutMapping("/user/{user_id}")
    public Users updateUser(@PathVariable(name = "user_id")String id, @Validated @RequestBody requestUserDto existUser){
        return userService.modify(id, existUser);
    }

    @GetMapping("/user/all")
    public List<Users> allUser(){
        return userService.findAll();
    }

    @GetMapping("/user/{user_id}")
    public Users oneUser(@PathVariable("user_id") String id){
        return userService.findOne(id);
    }

    @DeleteMapping("user/{user_id}")
    public String deleteUser(@PathVariable("user_id") String id){
        return userService.delete(id);
    }
}
