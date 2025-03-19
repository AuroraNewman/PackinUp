package learn.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import learn.TestHelper;
import learn.models.User;
import learn.service.Result;
import learn.service.ResultType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        testAddUser = new User(TestHelper.goodUsername, TestHelper.goodEmail, TestHelper.goodPassword);
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
        @Test
        void shouldReturn400WhenEmpty() throws Exception {
            var request = post(PREFIX)
                    .contentType(MediaType.APPLICATION_JSON);

            mvc.perform(request)
                    .andExpect(status().isBadRequest());
        }
        @Test
        void addShouldReturn400WhenInvalid() throws Exception {
        User user = new User();
        String userJson = jsonMapper.writeValueAsString(user);
        var request = post(PREFIX)
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson);
        mvc.perform(request)
                .andExpect(status().isBadRequest());
        }
        @Test
        void shouldReturn400OnBlankUsername() throws Exception {
            User failAdd = new User();
            failAdd.setUserId(0);
            failAdd.setUsername("");
            failAdd.setEmail(TestHelper.goodEmail);
            failAdd.setPassword(TestHelper.goodPassword);

            Result<User> expectedResult = new Result<>();
            expectedResult.addErrorMessage("Username is required.", ResultType.INVALID);

            when(service.create(failAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(jsonMapper.writeValueAsString(failAdd)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void shouldReturn400OnLongUsername() throws Exception {
            User failAdd = new User();
            failAdd.setUserId(0);
            failAdd.setUsername(TestHelper.tooLongUsername);
            failAdd.setEmail(TestHelper.goodEmail);
            failAdd.setPassword(TestHelper.goodPassword);

            Result<User> expectedResult = new Result<>();
            expectedResult.addErrorMessage("Username must be fewer than 50 characters.", ResultType.INVALID);

            when(service.create(failAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(failAdd)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void shouldReturn400OnLongEmail() throws Exception {
            User failAdd = new User();
            failAdd.setUserId(0);
            failAdd.setUsername(TestHelper.goodUsername);
            failAdd.setEmail(TestHelper.tooLongEmail);
            failAdd.setPassword(TestHelper.goodPassword);

            Result<User> expectedResult = new Result<>();
            expectedResult.addErrorMessage("Email must be fewer than 100 characters.", ResultType.INVALID);

            when(service.create(failAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(failAdd)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void shouldReturn400OnBlankEmail() throws Exception {
            User failAdd = new User();
            failAdd.setUserId(0);
            failAdd.setUsername(TestHelper.goodUsername);
            failAdd.setEmail("");
            failAdd.setPassword(TestHelper.goodPassword);

            Result<User> expectedResult = new Result<>();
            expectedResult.addErrorMessage("Email is required.", ResultType.INVALID);

            when(service.create(failAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(failAdd)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void shouldReturn400OnBlankPassword() throws Exception {
            User failAdd = new User();
            failAdd.setUserId(0);
            failAdd.setUsername(TestHelper.goodUsername);
            failAdd.setEmail(TestHelper.goodEmail);
            failAdd.setPassword("");

            Result<User> expectedResult = new Result<>();
            expectedResult.addErrorMessage("Password is required.", ResultType.INVALID);

            when(service.create(failAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(failAdd)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void shouldReturn400OnInvalidPasswordNoCaps() throws Exception {
            User failAdd = new User();
            failAdd.setUserId(0);
            failAdd.setUsername(TestHelper.goodUsername);
            failAdd.setEmail(TestHelper.goodEmail);
            failAdd.setPassword(TestHelper.badFormatPasswordNoCaps);

            Result<User> expectedResult = new Result<>();
            expectedResult.addErrorMessage("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.", ResultType.INVALID);

            when(service.create(failAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(failAdd)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void shouldReturn400OnInvalidPasswordNoLowers() throws Exception {
            User failAdd = new User();
            failAdd.setUserId(0);
            failAdd.setUsername(TestHelper.goodUsername);
            failAdd.setEmail(TestHelper.goodEmail);
            failAdd.setPassword(TestHelper.badFormatPasswordNoLowers);

            Result<User> expectedResult = new Result<>();
            expectedResult.addErrorMessage("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.", ResultType.INVALID);

            when(service.create(failAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(failAdd)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void shouldReturn400OnInvalidPasswordNoNumber() throws Exception {
            User failAdd = new User();
            failAdd.setUserId(0);
            failAdd.setUsername(TestHelper.goodUsername);
            failAdd.setEmail(TestHelper.goodEmail);
            failAdd.setPassword(TestHelper.badFormatPasswordNoNumber);

            Result<User> expectedResult = new Result<>();
            expectedResult.addErrorMessage("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.", ResultType.INVALID);

            when(service.create(failAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(failAdd)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void shouldReturn400OnInvalidPasswordNoSpecials() throws Exception {
            User failAdd = new User();
            failAdd.setUserId(0);
            failAdd.setUsername(TestHelper.goodUsername);
            failAdd.setEmail(TestHelper.goodEmail);
            failAdd.setPassword(TestHelper.badFormatPasswordNoSpecial);

            Result<User> expectedResult = new Result<>();
            expectedResult.addErrorMessage("Password must contain at least one uppercase letter, one lowercase letter, one special character, and one number.", ResultType.INVALID);

            when(service.create(failAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(failAdd)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void shouldReturn400OnInvalidPasswordTooShort() throws Exception {
            User failAdd = new User();
            failAdd.setUserId(0);
            failAdd.setUsername(TestHelper.goodUsername);
            failAdd.setEmail(TestHelper.goodEmail);
            failAdd.setPassword(TestHelper.badFormatPasswordShort);

            Result<User> expectedResult = new Result<>();
            expectedResult.addErrorMessage("Password must be at least 8 characters long.", ResultType.INVALID);

            when(service.create(failAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(failAdd)))
                    .andExpect(status().isBadRequest());
        }
        @Test
        void shouldReturn400OnDuplicateUsername() throws Exception{
            User failAdd = new User();
            failAdd.setUserId(0);
            failAdd.setUsername(TestHelper.goodUsername);
            failAdd.setEmail(TestHelper.existingUser.getEmail());
            failAdd.setPassword(TestHelper.goodPassword);

            Result<User> expectedResult = new Result<>();
            expectedResult.addErrorMessage("Email already exists.", ResultType.INVALID);

            when(service.create(failAdd)).thenReturn(expectedResult);

            mvc.perform(MockMvcRequestBuilders.post(PREFIX)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonMapper.writeValueAsString(failAdd)))
                    .andExpect(status().isBadRequest());
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