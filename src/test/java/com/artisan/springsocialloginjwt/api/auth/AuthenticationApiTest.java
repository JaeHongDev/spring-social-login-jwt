package com.artisan.springsocialloginjwt.api.auth;

import static org.junit.jupiter.api.Assertions.*;

// 테스트를 위한 의존성 추가
// build.gradle 파일에 다음 의존성을 추가하세요.
// testImplementation 'org.springframework.boot:spring-boot-starter-test'

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationApiTest {

    @Autowired
    private MockMvc mockMvc;

    // 로그인 API 테스트
    @Test
    public void loginTest() throws Exception {
        String requestBody = "{\"id\":\"testuser\",\"password\":\"password1234\"}";

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").isNotEmpty())
                .andReturn();

        String token = mvcResult.getResponse().getContentAsString();
        assertThat(token).isNotNull();
    }

    // 회원 가입 API 테스트
    @Test
    public void signupTest() throws Exception {
        String requestBody = "{\"id\":\"testuser\",\"password\":\"password1234\",\"name\":\"Test User\",\"email\":\"testuser@example.com\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/api/signup")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // 로그아웃 API 테스트
    @Test
    @WithMockUser
    public void logoutTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/logout")
                        .header("Authorization", "Bearer {token}"))
                .andExpect(status().isOk());
    }

    // 사용자 정보 조회 API 테스트
    @Test
    @WithMockUser
    public void getUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user")
                        .header("Authorization", "Bearer {token}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("testuser"))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }

    // 비밀번호 변경 API 테스트
    @Test
    @WithMockUser
    public void changePasswordTest() throws Exception {
        String requestBody = "{\"old_password\":\"password1234\",\"new_password\":\"newpassword1234\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/password")
                        .header("Authorization", "Bearer {token}")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void deleteUserTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/user")
                        .header("Authorization", "Bearer {token}")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}