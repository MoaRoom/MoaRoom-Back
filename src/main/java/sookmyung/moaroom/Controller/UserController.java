package sookmyung.moaroom.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sookmyung.moaroom.Dto.requestLoginDto;
import sookmyung.moaroom.Dto.requestUserDto;
import sookmyung.moaroom.Model.Url;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user")
    public String addUser(@Validated @RequestBody requestUserDto newUser){
        return userService.save(newUser);
    }

    @PostMapping("/login")
    public UUID login(@Validated @RequestBody requestLoginDto loginUser){
        return userService.login(loginUser);
    }

    @PutMapping("/users/{user_id}")
    public Users updateUser(@PathVariable(name = "user_id")String id, @Validated @RequestBody requestUserDto existUser){
        return userService.modify(id, existUser);
    }

    @GetMapping("/users")
    public List<Users> allUser(){
        return userService.findAll();
    }

    @GetMapping("/users/{user_id}")
    public Users oneUser(@PathVariable("user_id") String id){
        return userService.findOne(id);
    }

    @DeleteMapping("/users/{user_id}")
    public String deleteUser(@PathVariable("user_id") String id){
        return userService.delete(id);
    }

    @GetMapping("/users/{user_id}/{lecture_id}/url")
    public Url getUrl(@PathVariable("user_id") String userId, @PathVariable("lecture_id") String lectureId){
        return userService.getUrl(userId, lectureId);
    }
    @GetMapping("/users/{user_id}/urls")
    public List<Url> getUrls(@PathVariable("user_id") String userId){
        return userService.getUrls(userId);
    }
}
