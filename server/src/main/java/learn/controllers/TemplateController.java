package learn.controllers;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import learn.data_transfer_objects.*;
import learn.models.*;
import learn.service.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    private final TripTypeService tripTypeService;
    private final ItemService itemService;
    private final TemplateItemService templateItemService;

    public TemplateController(TemplateService service, SecretSigningKey secretSigningKey, UserService userService, TripTypeService tripTypeService, ItemService itemService, TemplateItemService templateItemService) {
        this.service = service;
        this.secretSigningKey = secretSigningKey;
        this.userService = userService;
        this.tripTypeService = tripTypeService;
        this.itemService = itemService;
        this.templateItemService = templateItemService;
    }

    @GetMapping
    ResponseEntity<Object> findByUserId(@RequestHeader Map<String, String> headers) {
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Result<List<Template>> templatesResult = service.findByUserId(userId);
        for (Template template : templatesResult.getPayload()) {
            System.out.println(template.getTemplateName());
        }

        if (!templatesResult.isSuccess()){
            return new ResponseEntity<>(templatesResult.getErrorMessages(), HttpStatus.BAD_REQUEST);
        } else {
            List<OutgoingTemplate> outgoingTemplates = templatesResult.getPayload().stream()
                    .map(OutgoingTemplate::new)
                    .collect(Collectors.toList());
//            return new ResponseEntity<>(templates, HttpStatus.OK);
            return new ResponseEntity<>(outgoingTemplates, HttpStatus.OK);
        }
    }
    @GetMapping("/{templateId}")
    ResponseEntity<Object> findById(@PathVariable int templateId, @RequestHeader Map<String, String> headers) {
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Result<Template> templateResult = service.findById(templateId);

        if (templateResult.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(templateResult.getErrorMessages(), HttpStatus.NOT_FOUND);
        } else if (!templateResult.isSuccess()){
            return new ResponseEntity<>(templateResult.getErrorMessages(), HttpStatus.BAD_REQUEST);
        } else {
            OutgoingTemplate outgoingTemplate = new OutgoingTemplate(templateResult.getPayload());
            return new ResponseEntity<>(outgoingTemplate, HttpStatus.OK);
        }
    }

    @PostMapping("/{templateId}")
    ResponseEntity<Object> create(@RequestBody @Valid IncomingTemplate incomingTemplate, @RequestHeader Map<String, String> headers, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(extractDefaultMessageFromBindingResult(bindingResult), HttpStatus.BAD_REQUEST);
        }
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Result<User> foundUserResult = userService.findById(userId);
        Template template = constructTemplate(incomingTemplate, foundUserResult.getPayload());
        if (!foundUserResult.isSuccess() || template == null) {
            return new ResponseEntity<>(foundUserResult.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }

        Result<Template> result = service.create(template);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new OutgoingTemplate(result.getPayload()), HttpStatus.CREATED);
        }
    }

    @PutMapping("/{templateId}")
    ResponseEntity<Object> update(@PathVariable int templateId, @RequestBody @Valid IncomingTemplate incomingTemplate, @RequestHeader Map<String, String> headers, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(extractDefaultMessageFromBindingResult(bindingResult), HttpStatus.BAD_REQUEST);
        }
        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Result<User> foundUserResult = userService.findById(userId);
        Result<Template> existingTemplateResult = service.findById(templateId);
        Template existingTemplate = existingTemplateResult.getPayload();
//        Template template = constructTemplate(incomingTemplate, foundUserResult.getPayload());
        if (!foundUserResult.isSuccess() || existingTemplate == null) {
            return new ResponseEntity<>(foundUserResult.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
        Template foundTemplate = existingTemplateResult.getPayload();
        if (foundTemplate.getTemplateId() != templateId) {
            return new ResponseEntity<>(List.of("IDs must match"), HttpStatus.CONFLICT);
        }
        foundTemplate.setTemplateName(incomingTemplate.getTemplateName());
        foundTemplate.setTemplateDescription(incomingTemplate.getTemplateDescription());
        foundTemplate.setTemplateTripType(tripTypeService.findById(incomingTemplate.getTemplateTripTypeId()).getPayload());
        foundTemplate.setTemplateUser(foundUserResult.getPayload());
        Result<Template> result = service.update(foundTemplate);

        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(new OutgoingTemplate(result.getPayload()), HttpStatus.OK);
        }
    }
    @PutMapping("/{templateId}/additem")
    ResponseEntity<Object> addTemplateItem(@PathVariable int templateId, @RequestBody IncomingTemplateItem incomingTemplateItem){
        Result<Template> templateResult = service.findById(templateId);
        if (templateResult.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(templateResult.getErrorMessages(), HttpStatus.NOT_FOUND);
        } else if (!templateResult.isSuccess()){
            return new ResponseEntity<>(templateResult.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
        Template template = templateResult.getPayload();
        TemplateItem templateItem = convertIncomingTIToTI(incomingTemplateItem);
        if (templateItem == null) {
            return new ResponseEntity<>(List.of("Template item not found"), HttpStatus.NOT_FOUND);
        }
        Result<TemplateItem> result = templateItemService.create(templateItem);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
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

        private Template constructTemplate(IncomingTemplate incomingTemplate, User user) {

        Template template = new Template();
        template.setTemplateName(incomingTemplate.getTemplateName());
        template.setTemplateDescription(incomingTemplate.getTemplateDescription());
        template.setTemplateUser(user);
        TripType templateTripType = findTripTypeFromIncomingTemplate(incomingTemplate);
        if (templateTripType == null) {
            template.setTemplateTripType(tripTypeService.findById(1).getPayload());
        } else {
            template.setTemplateTripType(templateTripType);
        }

        return template;
        }

        private TripType findTripTypeFromIncomingTemplate(IncomingTemplate incomingTemplate) {
            Result<TripType> tripType = tripTypeService.findById(incomingTemplate.getTemplateTripTypeId());
            if (!tripType.isSuccess()) {
                return null;
            } else {
                return tripType.getPayload();
            }
        }
        private List<OutgoingItem> convertItemsToOutgoingItems(List<Item> items){
        return items.stream()
                .map(OutgoingItem::new)
                .collect(Collectors.toList());
        }

        private TemplateItem convertIncomingTIToTI(IncomingTemplateItem incomingTemplateItem){
        TemplateItem templateItem = new TemplateItem();
        Item foundItem = itemService.findByName(incomingTemplateItem.getTemplateItemItemName());

        templateItem.setTemplateItemId(foundItem.getItemId());
        templateItem.setQuantity(incomingTemplateItem.getTemplateItemQuantity());
        templateItem.setChecked(incomingTemplateItem.isTemplateItemIsChecked());

        Template foundTemplate = convertIncomingTemplateToTemplate(incomingTemplateItem.getTemplateItemTemplateId());

        OutgoingItem foundOutgoingItem = new OutgoingItem(foundItem);

        if (foundTemplate == null) {
            return null;
        }else if (foundOutgoingItem == null) {
            itemService.create(convertIncomingItemToItem(incomingTemplateItem.getTemplateItemTemplateId()));
        } else {
            templateItem.setOutgoingItem(foundOutgoingItem);
            templateItem.setTemplate(foundTemplate);
        }

            return templateItem;
        }

        private Template convertIncomingTemplateToTemplate(int incomingTemplateId) {
            Result<Template> foundTemplate = service.findById(incomingTemplateId);
            if (!foundTemplate.isSuccess()) {
                return null;
            } else {
                return foundTemplate.getPayload();
            }
        }

        private Item convertIncomingItemToItem(int incomingItemId){
        Item foundItem = itemService.findById(incomingItemId);
        if (foundItem == null) {
            return null;
        } else {
            return foundItem;
        }
            }
    }