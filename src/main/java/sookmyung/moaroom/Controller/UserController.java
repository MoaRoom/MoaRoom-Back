package sookmyung.moaroom.Controller;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sookmyung.moaroom.Model.Users;
import sookmyung.moaroom.Service.UserService;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/user/new")
    public String addUser(@RequestBody Map<String, String> data){
        JsonObject newUser = new JsonObject();
        newUser.addProperty("id", data.get("id"));
        newUser.addProperty("pwd", data.get("pwd"));
        newUser.addProperty("name", data.get("name"));
        newUser.addProperty("role", data.get("role"));

        if(data.containsKey("num")){
            newUser.addProperty("num", data.get("num"));
        }

        return userService.save(newUser);
    }

    @PutMapping("/user/{user_id}")
    public Users updateUser(@PathVariable(name = "user_id")String id, @RequestBody Map<String, String> data){
        JsonObject existUser = new JsonObject();
        existUser.addProperty("id", data.get("id"));
        existUser.addProperty("pwd", data.get("pwd"));
        existUser.addProperty("name", data.get("name"));
        existUser.addProperty("role", data.get("role"));

        if(data.containsKey("num")){
            existUser.addProperty("num", data.get("num"));
        }

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
