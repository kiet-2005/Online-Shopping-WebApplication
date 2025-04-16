package com.example.Spring;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @ParameterizedTest
    @CsvSource({
        "Kiệt, 1, 200",
        "admin, wrongpassword, 401",
        "invalidUser, password, 401",
        "'', password, 400",
        "admin, '', 400"
    })
    void testLogin(String username, String password, int expectedStatus) throws Exception {
        String loginRequest = objectMapper.writeValueAsString(new LoginRequest(username, password));

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginRequest))
                .andExpect(status().is(expectedStatus));
    }
    static class LoginRequest {
        public String username;
        public String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }
}
