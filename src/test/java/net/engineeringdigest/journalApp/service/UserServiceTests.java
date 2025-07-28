//package net.engineeringdigest.journalApp.service;
//
//import net.engineeringdigest.journalApp.entity.User;
//import net.engineeringdigest.journalApp.repository.UserRepository;
//import org.assertj.core.util.Strings;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest
//public class UserServiceTests {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Disabled //it disable the test
//   @Test
//    //the method which we want to make as testing methos  annotet it using @Test
//
//    public void testFindByUserName(){
//        User user =userRepository.findByUserName("ram"); // finding user from db
//        assertTrue(!user.getJournalEntries().isEmpty());   // checking user has any hournal entries or not
//
//    }
//    @ParameterizedTest
//    @ValueSource(strings= {
//            "ram",
//            "Sham",
//            "Vipul",
//            "''"
//    })
//    public void CheckName(String name){
//        assertNotNull(userRepository.findByUserName(name),"failed for"+name);
//    }
//    @Disabled
//    @ParameterizedTest
//    @CsvSource({
//            "1,1,2",
//            "2,10,12",
//            "3,3,9"
//    }) // used to sne ddata for parameters in paramaetrizedTest annotation
//    public void Test(int a, int b, int expected){
//        assertEquals(expected,a+b);
//    }
//}
