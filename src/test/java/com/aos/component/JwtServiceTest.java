//package com.aos.component;
//
//
//import com.aos.model.ApplicationUser;
//import com.aos.model.Role;
//import com.aos.repository.ApplicationUserRepository;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//public class JwtServiceTest {
//
//
//
//    private String secretKey="404E635266556A586E3272357538782F413F4428472B4B6250645367566B5971";
//    private static final long jwtExpiration = 3600000;
//
//    @MockBean
//    private ApplicationUserRepository applicationUserRepository;
//
//
//    private JwtService underTest;
//    ApplicationUser mockApplicationUser =new ApplicationUser(1,"za@gmail.com","123456", Role.USER);
//    @Before
//    public void setUp() {
//        underTest = new JwtService();
//        underTest.setSecretKey(secretKey);
//        underTest.setJwtExpiration(jwtExpiration);
//        System.out.println(underTest);
//    }
//    @Test
//    public void testIsTokenValid() {
//
//        String token = underTest.generateToken(mockApplicationUser);
//        assertTrue(underTest.isTokenValid(token, mockApplicationUser));
//    }
//
//
//
//}
