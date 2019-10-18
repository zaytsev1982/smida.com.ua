package ua.com.smida.restcontroller.auth;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.smida.model.user.User;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthRestControllerIT {

    @Autowired
    private WebApplicationContext webContext;
    @Autowired
    private ObjectMapper mapper;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(webContext)
            .apply(springSecurity())
            .build();
    }

    @Test
    public void shouldBeRegistrationUser() throws Exception {
        User user = User
            .builder()
            .login("candidate")
            .password("candidate")
            .build();
        String jsonRequest = mapper.writeValueAsString(user);

        MvcResult result = mvc
            .perform(post("/api/v1/registration")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated())
            .andDo(print())
            .andReturn();

        assertEquals(201, result.getResponse().getStatus());
    }

    @Test
    public void shouldGetStatus200Or403() throws Exception {
        User userStatus200 = User
            .builder()
            .login("user")
            .password("user")
            .build();
        String jsonRequest200 = mapper.writeValueAsString(userStatus200);
        mvc.perform(
            post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequest200)
                .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andDo(print());
        User userStatus403 = User
            .builder()
            .login("user1")
            .password("user")
            .build();
        String jsonRequest403 = mapper.writeValueAsString(userStatus403);
        mvc.perform(
            post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonRequest403)
                .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isForbidden())
            .andDo(print());
    }

}
