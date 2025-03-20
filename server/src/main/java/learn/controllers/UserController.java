package learn.controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import learn.data_transfer_objects.UserForLogin;
import learn.models.User;
import learn.service.Result;
import learn.service.ResultType;
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
            return new ResponseEntity<>(createJwtFromUser(result.getPayload()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserForLogin user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(extractDefaultMessageFromBindingResult(bindingResult), HttpStatus.BAD_REQUEST);
        }
        Result<User> result = service.findByEmail(user.getEmail());

        if (result.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.NOT_FOUND);
        }

        if (result.getPayload().getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>(createJwtFromUser(result.getPayload()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(List.of("Email and password do not match"), HttpStatus.UNAUTHORIZED);
        }
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
        return null;
    }
    private List<String> extractDefaultMessageFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
    private Map<String, String> createJwtFromUser(User user) {
        String jwt = Jwts.builder()
                .claim("email", user.getEmail())
                .claim("username", user.getUsername())
                .claim("userId", user.getUserId())
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
        return Map.of("jwt", jwt);
    }
}
