package learn.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.TestHelper;
import learn.models.Template;
import learn.models.User;
import learn.service.Result;
import learn.service.TemplateService;
import learn.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
class TemplateControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private TemplateService service;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private SecretSigningKey secretSigningKey;

    private final String PREFIX = "/api/packinup/template";
    private Template testTemplate;
    private Template testAddTemplate;
    @BeforeEach
    void setUp(){
        testTemplate = TestHelper.makeTestTemplate();
        testAddTemplate = TestHelper.makeTestTemplate();
        testAddTemplate.setTemplateId(0);
    }

    /**
     * following test recommended by Spring to ensure app can start
     * https://spring.io/guides/gs/testing-web
     */
    @Test
    void contextLoads(){
        assertNotNull(mvc);
    }

    @Nested
    public class CreateTests {
        @Test
        void shouldReturn201Create() throws Exception {
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(PREFIX)
                    .contentType(MediaType.APPLICATION_JSON);

            Template beforeAdd = testAddTemplate;
            Template afterAdd = TestHelper.makeTestTemplate();
            Result<Template> expectedResult = new Result<>();
            expectedResult.setPayload(afterAdd);

            String templateJson = jsonMapper.writeValueAsString(beforeAdd);
            when(service.create(beforeAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(templateJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        }
    }
}