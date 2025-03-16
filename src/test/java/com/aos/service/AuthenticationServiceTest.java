//package com.aos.service;
//
//import com.aos.dto.AuthenticationRequest;
//import com.aos.dto.AuthenticationResponse;
//import com.aos.dto.RegisterRequest;
//import com.aos.model.ApplicationUser;
//import com.aos.model.Role;
//import com.aos.repository.ApplicationUserRepository;
//import com.aos.component.JwtService;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.Optional;
//
//import static org.junit.Assert.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.when;
//
//
//@RunWith(SpringRunner.class)
//public class AuthenticationServiceTest {
//    @Mock
//    private ApplicationUserRepository applicationUserRepository;
//
//    @Mock
//    private  PasswordEncoder passwordEncoder;
//
//    @Mock
//    private  JwtService jwtService;
//
//    @Mock
//    private  AuthenticationManager authenticationManager;
//
//    private AutoCloseable autoCloseable;
//    private AuthenticationService underTest;
//
//    @Before
//    public void setUp() {
//        autoCloseable = MockitoAnnotations.openMocks(this);
//        underTest = new AuthenticationService(applicationUserRepository,passwordEncoder,jwtService,authenticationManager);
//    }
//
//    @After
//    public void tearDown() throws Exception {
//
//        autoCloseable.close();
//    }
//
//    @Test
//    public void register() {
//        ApplicationUser mockApplicationUser =new ApplicationUser(1,"za@gmail.com","123456", Role.USER);
//
//        given(applicationUserRepository.findByEmail("za@gmail.com")).willReturn(Optional.empty());
//        when(jwtService.generateToken(any(ApplicationUser.class))).thenReturn("jwtToken");
//        RegisterRequest request = new RegisterRequest("za@gmail.com","123456",Role.USER);
//
//        AuthenticationResponse response = underTest.register(request);
//
//        assertNotNull(response);
//        assertEquals(request.getEmail(), response.getEmail());
//        assertEquals("jwtToken",response.getAccessToken());
//        assertNotNull(response.getExpiration());
//
//
//    }
//
//    @Test
//    public void authenticate() {
//        ApplicationUser mockApplicationUser =new ApplicationUser(1,"za@gmail.com","123456",Role.USER);
//
//        given(applicationUserRepository.findByEmail("za@gmail.com")).willReturn(Optional.of(mockApplicationUser));
//        when(jwtService.generateToken(any(ApplicationUser.class))).thenReturn("jwtToken");
//
//        AuthenticationRequest request = new AuthenticationRequest("za@gmail.com","123456");
//
//        AuthenticationResponse response = underTest.authenticate(request);
//
//        assertNotNull(response);
//        assertEquals(request.getEmail(), response.getEmail());
//        assertEquals("jwtToken",response.getAccessToken());
//        assertNotNull(response.getExpiration());
//    }
//
//    @Test
//    public void registerExistingEmail() {
//        ApplicationUser mockApplicationUser =new ApplicationUser(1,"za@gmail.com","123456",Role.USER);
//
//        given(applicationUserRepository.findByEmail("za@gmail.com")).willReturn(Optional.of(mockApplicationUser));
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            underTest.register(null);
//        }
//
//
//        );
//
//
//
//    }
//
//}