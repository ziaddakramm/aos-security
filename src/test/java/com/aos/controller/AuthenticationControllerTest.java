package com.aos.controller;

import com.aos.dto.AuthenticationRequest;
import com.aos.dto.AuthenticationResponse;
import com.aos.model.ApplicationUser;
import com.aos.model.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.aos.component.JwtService;
import com.aos.dto.RegisterRequest;
import com.aos.repository.ApplicationUserRepository;
import com.aos.service.AuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private AuthenticationService authService;

    @MockBean
    private JwtService jwtService;
    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @Test
    public void register() throws Exception {

        RegisterRequest mockRequest=new RegisterRequest("za@gmail.com","123456", Role.USER);
        AuthenticationResponse mockResponse=getMockResponse();

        given(authService.register(mockRequest)).willReturn(mockResponse);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.email").value(mockResponse.getEmail()))
                .andExpect(jsonPath("$.expiration").value(mockResponse.getExpiration()));

    }

    @Test
    public void authenticate() throws Exception {
        AuthenticationRequest mockRequest=new AuthenticationRequest("za@gmail.com","123456");
        AuthenticationResponse mockResponse=getMockResponse();

        given(authService.authenticate(mockRequest)).willReturn(mockResponse);

        mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.email").value(mockResponse.getEmail()))
                .andExpect(jsonPath("$.expiration").value(mockResponse.getExpiration()));
    }

    @Test
    public void validate() throws Exception{
        mockMvc.perform(get("/api/auth/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZW1haWwxQGdtYWlsLmNvbSIsImlhdCI6MTcwNjcyNTE0OSwiZXhwIjoxNzA2NzI2MDEzfQ.poQPShUn22UIIfQS3mFrbJunUCnBVzRrHdnClpDPi1k"))
                .andExpect(status().isOk());

    }



    public AuthenticationResponse getMockResponse(){
        var user = ApplicationUser.builder()
                .email("za@gmail.com")
                .password("123456")
                .role(Role.USER)
                .build();

        var jwtToken = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .email(user.getEmail())
                .expiration(86400)
                .build();
    }



}