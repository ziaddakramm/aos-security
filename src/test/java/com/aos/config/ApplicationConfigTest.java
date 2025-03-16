package com.sumerge.security.config;

import com.sumerge.security.model.Role;
import com.sumerge.security.model.ApplicationUser;
import com.sumerge.security.repository.ApplicationUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ApplicationConfigTest {


    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @Mock
    private AuthenticationConfiguration authConfig;

    @InjectMocks
    private ApplicationConfig applicationConfig;
    ApplicationUser mockApplicationUser = new ApplicationUser(1, "za@gmail.com", "123456", Role.USER);

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserDetailsService() {
        when(applicationUserRepository.findByEmail("za@gmail.com")).thenReturn(Optional.of(mockApplicationUser));

        assertNotNull(applicationConfig.userDetailsService().loadUserByUsername("za@gmail.com"));

        verify(applicationUserRepository, times(1)).findByEmail("za@gmail.com");
    }


    @Test
    public void testAuthenticationProvider() {
        assertNotNull(applicationConfig.authenticationProvider());
    }

    @Test
    public void testPasswordEncoder() {
        assertNotNull(applicationConfig.passwordEncoder());
    }
}


