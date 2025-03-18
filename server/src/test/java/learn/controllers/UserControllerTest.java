package learn.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.TestHelper;
import learn.models.User;
import learn.service.Result;
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
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Autowired
    private ObjectMapper jsonMapper;

    private final String PREFIX = "/api/packinup/user";
    private User testUser;
    private User testAddUser;
    @BeforeEach
    void setUp(){
        testUser = new User(1, TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.goodPassword);
        testAddUser = new User(0, TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.goodPassword);
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

            User beforeAdd = testAddUser;
            User expectedAfterAdd = testUser;

            Result<User> expectedResult = new Result<>();
            expectedResult.setPayload(expectedAfterAdd);

            String userJson = jsonMapper.writeValueAsString(beforeAdd);
            when(service.create(beforeAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated());                    ;
        }
    }

    @Test
    void login() {
    }

    @Test
    void updateUsername() {
    }

    @Test
    void delete() {
    }
}