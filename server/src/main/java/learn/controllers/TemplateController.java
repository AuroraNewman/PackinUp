package learn.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import learn.data_transfer_objects.IncomingPackingList;
import learn.models.PackingList;
import learn.models.TripType;
import learn.models.User;
import learn.service.Result;
import learn.service.PackingListService;
import learn.service.TripTypeService;
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
    private final PackingListService service;
    private final SecretSigningKey secretSigningKey;
    private final UserService userService;
    private final TripTypeService tripTypeService;

    public TemplateController(PackingListService service, SecretSigningKey secretSigningKey, UserService userService, TripTypeService tripTypeService) {
        this.service = service;
        this.secretSigningKey = secretSigningKey;
        this.userService = userService;
        this.tripTypeService = tripTypeService;
    }

    @GetMapping
    ResponseEntity<Object> findAllListsByUserId(@RequestHeader Map<String, String> headers) {
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Result<List<PackingList>> templatesResult = service.findAllListsByUserId(userId);

        if (!templatesResult.isSuccess()){
            return new ResponseEntity<>(templatesResult.getErrorMessages(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(templatesResult.getPayload(), HttpStatus.OK);
        }
    }

    @PostMapping
    ResponseEntity<Object> create(@RequestBody @Valid IncomingPackingList incomingPackingList, @RequestHeader Map<String, String> headers, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(extractDefaultMessageFromBindingResult(bindingResult), HttpStatus.BAD_REQUEST);
        }
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Result<User> foundUserResult = userService.findById(userId);
        PackingList packingList = constructTemplate(incomingPackingList, foundUserResult.getPayload());
        if (!foundUserResult.isSuccess() || packingList == null) {
            return new ResponseEntity<>(foundUserResult.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }

        Result<PackingList> result = service.create(packingList);

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

        private PackingList constructTemplate(IncomingPackingList incomingPackingList, User user) {

        PackingList packingList = new PackingList();
        packingList.setTemplateName(incomingPackingList.getTemplateName());
        packingList.setTemplateDescription(incomingPackingList.getTemplateDescription());
        packingList.setReusable(incomingPackingList.isReusable());
        packingList.setTemplateUser(user);
        TripType templateTripType = findTripTypeFromIncomingTemplate(incomingPackingList);
        if (templateTripType == null) {
            return null;
        } else {
            packingList.setTemplateTripType(templateTripType);
        }

        return packingList;
        }

        private TripType findTripTypeFromIncomingTemplate(IncomingPackingList incomingPackingList) {
            Result<TripType> tripType = tripTypeService.findById(incomingPackingList.getTemplateTripTypeId());
            if (!tripType.isSuccess()) {
                return null;
            } else {
                return tripType.getPayload();
            }
        }

    }
