package com.aos.service;

import com.aos.component.JwtService;
import com.aos.dto.AuthenticationRequest;
import com.aos.dto.AuthenticationResponse;
import com.aos.dto.RegisterRequest;
import com.aos.model.ApplicationUser;
import com.aos.model.Role;
import com.aos.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final ApplicationUserRepository repository;

  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthenticationResponse register(RegisterRequest request) {

    if(emailAlreadyExists(request.getEmail()))
    {
      throw new RuntimeException("email already exists");
    }



    var user = ApplicationUser.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.USER)
        .build();


    repository.save(user);

    var jwtToken = jwtService.generateToken(user);

    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .email(user.getEmail())
        .expiration(jwtService.getJwtExpiration())
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .email(user.getEmail())
            .expiration(jwtService.getJwtExpiration())
        .build();
  }


  public boolean emailAlreadyExists(String email)
  {
    Optional<ApplicationUser> user=repository.findByEmail(email);

    if(user.isPresent())
    {
      return true;
    }
    return false;

  }
}

