package learn.controllers;

import jakarta.validation.Valid;
import learn.models.User;
import learn.service.Result;
import learn.service.UserService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            return new ResponseEntity<>(extractDefaultMessageFromBindingResult(bindingResult), HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<Object> login(@RequestBody @Valid User user, BindingResult bindingResult) {
        return null;
    }
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUsername(@PathVariable int userId, @RequestBody User user) {
        //look up existing user with userId
        //set the existing user with the new username
        //update user with new username through service
        return null;

    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable int userId) {
        Result<Void> result = service.delete(userId);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
    }
    private List<String> extractDefaultMessageFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
