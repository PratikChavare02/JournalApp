//package net.engineeringdigest.journalApp.service;
//
//import net.engineeringdigest.journalApp.entity.User;
//import net.engineeringdigest.journalApp.repository.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.*;
//
//public class UserDetailsServiceImplTest {
//
//    @InjectMocks
//    private CustomUserDetailsServiceImpl userDetailsService;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp(){
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    void loadUserByUserName(){
//        // Create your custom User entity using the required constructor
//        User mockUser = new User("ram", "abcd");
//        mockUser.setRoles(new ArrayList<>()); // Set empty roles list
//
//        // Mock the repository to return your custom User entity
//        when(userRepository.findByUserName(ArgumentMatchers.anyString()))
//                .thenReturn(mockUser);
//
//        // Test the service method
//        UserDetails userDetails = userDetailsService.loadUserByUsername("ram");
//
//        // Assertions
//        Assertions.assertNotNull(userDetails);
//        Assertions.assertEquals("ram", userDetails.getUsername());
//        Assertions.assertEquals("abcd", userDetails.getPassword());
//
//        // Verify that the repository method was called
//        verify(userRepository, times(1)).findByUserName("ram");
//    }
//}