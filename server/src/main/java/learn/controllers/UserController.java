package learn.controllers;

import jakarta.validation.Valid;
import learn.models.User;
import learn.service.Result;
import learn.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/packinup/user")
public class UserController {
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        Result<User> result = service.create(user);

        if (result.isSuccess()) {
//            Map<String, String> output = createJwtFromUser(result.getPayload());
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    private ResponseEntity<Object> login(@RequestBody @Valid User user, BindingResult bindingResult) {
        return null;
    }
    @PutMapping("/{userId}")
    private ResponseEntity<Object> updateUsername(@PathVariable int userId, @RequestBody User user) {
        //look up existing user with userId
        //set the existing user with the new username
        //update user with new username through service
        return null;

    }
    @DeleteMapping("/{userId}")
    private ResponseEntity<Object> delete(@PathVariable int userId) {
        Result<Void> result = service.delete(userId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
    }
}
