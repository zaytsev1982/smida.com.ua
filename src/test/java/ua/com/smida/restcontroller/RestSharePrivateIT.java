package ua.com.smida.restcontroller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.smida.model.Share;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:database/schema.sql")
public class RestSharePrivateIT {

    private static String BASIC = "/api/v1/private";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldBeCreate() throws Exception {
        Share candidate = Share
            .builder()
            .comments("company 10")
            .codeCompany(10000)
            .price(123.9)
            .quantity(250000)
            .capital(1000000)
            .build();

        mockMvc.perform(post("/api/v1/private/")
            .content(asJsonString(candidate))
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.comments", is("company 10")))
            .andExpect(jsonPath("$.codeCompany", is(10000)))
            .andExpect(jsonPath("$.price", is(123.9)))
            .andExpect(jsonPath("$.quantity", is(250000)))
            .andExpect(jsonPath("$.capital", is(1000000)));
    }

    @Test
    public void shouldUpdate() throws Exception {
        Share candidate = Share
            .builder()
            .comments("company 10")
            .codeCompany(10000)
            .price(123.9)
            .quantity(250000)
            .capital(1000000)
            .build();

        mockMvc.perform(put(BASIC + "/" + 2)
            .content(asJsonString(candidate))
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.comments", is("company 10")));


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
                .param("pageNo", String.valueOf(1))
                .param("pageSize", String.valueOf(6))
                .param("sortBy", "version", "codeCompany", "amount"))
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.*").isNotEmpty())
            .andExpect(jsonPath("$.*").isArray())
            .andExpect(status().isOk())
            .andReturn();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}