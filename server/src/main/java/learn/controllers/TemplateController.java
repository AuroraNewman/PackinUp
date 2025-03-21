package learn.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import learn.models.Template;
import learn.models.User;
import learn.service.Result;
import learn.service.TemplateService;
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
@RequestMapping("/api/packinup/template")
public class TemplateController {
    private final TemplateService service;
    private final SecretSigningKey secretSigningKey;
    private final UserService userService;

    public TemplateController(TemplateService service, SecretSigningKey secretSigningKey, UserService userService) {
        this.service = service;
        this.secretSigningKey = secretSigningKey;
        this.userService = userService;
    }

    @GetMapping
    ResponseEntity<Object> findByUserId(@RequestHeader Map<String, String> headers) {
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Result<List<Template>> templatesResult = service.findByUserId(userId);

        if (!templatesResult.isSuccess()){
            return new ResponseEntity<>(templatesResult.getErrorMessages(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(templatesResult.getPayload(), HttpStatus.OK);
        }
    }

    @PostMapping
    ResponseEntity<Object> create(@RequestBody @Valid Template template, @RequestHeader Map<String, String> headers, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(extractDefaultMessageFromBindingResult(bindingResult), HttpStatus.BAD_REQUEST);
        }
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Result<User> foundUserResult = userService.findById(userId);

        if (!foundUserResult.isSuccess()) {
            return new ResponseEntity<>(foundUserResult.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }

        template.setTemplateUser(foundUserResult.getPayload());

        Result<Template> result = service.create(template);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
    }
    private List<String> extractDefaultMessageFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }

    private Integer getUserIdFromHeaders(Map<String, String> headers) {
        if (headers.get("authorization") == null) {
            return null;
        }

        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretSigningKey.getKey())
                    .build().parseClaimsJws(headers.get("authorization"));
            return (Integer) claims.getBody().get("userId");
        } catch (Exception e) {
            return null;
        }
    }
//    nneds to get the authorization from the header or i\t wn' pss ess

}
