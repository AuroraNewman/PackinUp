package learn.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.TestHelper;
import learn.data_transfer_objects.IncomingPackingList;
import learn.models.PackingList;
import learn.service.Result;
import learn.service.PackingListService;
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
    private PackingListService service;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private SecretSigningKey secretSigningKey;

    private final String PREFIX = "/api/packinup/template";
    private PackingList testPackingList;
    private IncomingPackingList testAddTemplate;
    @BeforeEach
    void setUp(){
        testPackingList = TestHelper.makeTestTemplate();
        testAddTemplate = TestHelper.makeTestAddTemplate();
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

            IncomingPackingList beforeAdd = testAddTemplate;
            PackingList serviceAdd = TestHelper.makeTestTemplate();
            serviceAdd.setTemplateId(0);
            PackingList afterAdd = TestHelper.makeTestTemplate();
            Result<PackingList> expectedResult = new Result<>();
            expectedResult.setPayload(afterAdd);

            String templateJson = jsonMapper.writeValueAsString(beforeAdd);
            when(service.create(serviceAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(templateJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated());
        }
    }
}