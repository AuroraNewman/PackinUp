package learn.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import learn.data_transfer_objects.IncomingTemplateItem;
import learn.data_transfer_objects.OutgoingTemplateItem;
import learn.models.Item;
import learn.models.Template;
import learn.models.TemplateItem;
import learn.service.ItemService;
import learn.service.Result;
import learn.service.TemplateItemService;
import learn.service.TemplateService;
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
@RequestMapping("/api/packinup/template/item")
public class TemplateItemController {
    private final TemplateItemService service;
    private final SecretSigningKey secretSigningKey;
    private final TemplateService templateService;
    private final ItemService itemService;

    public TemplateItemController(TemplateItemService service, SecretSigningKey secretSigningKey, TemplateService templateService, ItemService itemService) {
        this.service = service;
        this.secretSigningKey = secretSigningKey;
        this.templateService = templateService;
        this.itemService = itemService;
    }

    @GetMapping("/{templateId}")
    ResponseEntity<Object> findAllByTemplateId(@PathVariable int templateId, @RequestHeader Map<String, String> headers) throws JsonProcessingException {


        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<TemplateItem> items = service.findAllByTemplateId(templateId);

        if (items == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            System.out.println(new ObjectMapper().writeValueAsString(items));
            return new ResponseEntity<>(items, HttpStatus.OK);
        }
    }

    @PostMapping
    ResponseEntity<Object> create(@RequestBody IncomingTemplateItem incomingTemplateItem, @RequestHeader Map<String, String> headers, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(extractDefaultMessageFromBindingResult(bindingResult), HttpStatus.BAD_REQUEST);
        }

        Integer userId = getUserIdFromHeaders(headers);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Result<TemplateItem> result = service.create(createTemplateItemFromIncoming(incomingTemplateItem));

        if (result.isSuccess()) {
            return new ResponseEntity<>(new OutgoingTemplateItem(result.getPayload()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(result.getErrorMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    private TemplateItem createTemplateItemFromIncoming(IncomingTemplateItem incomingTemplateItem) {
        Item item = itemService.findById(incomingTemplateItem.getTemplateItemItemId());
        Template template = templateService.findById(incomingTemplateItem.getTemplateItemTemplateId()).getPayload();
        int quantity = incomingTemplateItem.getTemplateItemQuantity();
        boolean isChecked = incomingTemplateItem.isTemplateItemIsChecked();
        if (item == null || template == null) {
            return null;
        } else {
            return new TemplateItem(quantity, isChecked, template, item);
        }
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
    private List<String> extractDefaultMessageFromBindingResult(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
