package ua.com.smida.restcontroller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:database/schema.sql")
public class RestSharePublicIT {

    private static String BASIC = "/api/v1/public";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldBeGetOneById() throws Exception {
        long id = 1L;
        mockMvc
            .perform(get(BASIC + "/" + id))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk())
            .andReturn();
        mockMvc
            .perform(get(BASIC + "/10"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isNotFound())
            .andReturn();
    }

    @Test
    public void shouldBeGetAllByCompany() throws Exception {
        String actual = "1245";
        mockMvc
            .perform(get(BASIC + "/company")
                .param("codeCompany", actual))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.*").isNotEmpty())
            .andExpect(status().isOk());

        mockMvc
            .perform(get(BASIC + "/company")
                .param("codeCompany", actual + 5))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.*").isNotEmpty())
            .andExpect(status().isNotFound());
    }

    @Test
    public void shouldBeGetAll() throws Exception {

        mockMvc
            .perform(get(BASIC + "/all"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.*").isNotEmpty())
            .andExpect(jsonPath("$.*").isArray())
            .andExpect(status().isOk())
            .andReturn();
    }

    @Test
    public void shouldBeGetAllByCriteria() throws Exception {
//        with param
        mockMvc
            .perform(get(BASIC + "/pages")
                .param("sortBy", "version", "codeCompany", "amount"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.*").isNotEmpty())
            .andExpect(jsonPath("$.*").isArray())
            .andExpect(status().isOk())
            .andReturn();
//        without param
        mockMvc
            .perform(get(BASIC + "/pages"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.*").isNotEmpty())
            .andExpect(jsonPath("$.*").isArray())
            .andExpect(status().isOk())
            .andReturn();
    }
}
