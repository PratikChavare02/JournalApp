//package net.engineeringdigest.journalApp.controller;
//
//
//import net.engineeringdigest.journalApp.entity.JournalEntry;
//import net.engineeringdigest.journalApp.service.JournalEntryService;
//import org.bson.types.ObjectId;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/journal")
//public class JournalEntryControllerV2 {
//
//
//    @Autowired
//    private JournalEntryService journalEntryService;
//
//    @GetMapping
//    public List<JournalEntry>getAll(){
//        return journalEntryService.getAll();
//    }
//
//    @PostMapping
//    public JournalEntry createEntry(@RequestBody JournalEntry myEntry){ //localhost:8080/journal when req is post
//        myEntry.setDate(LocalDateTime.now());
//        journalEntryService.saveEntry(myEntry);
//        return myEntry;
//
//    }
//
//    @GetMapping("id/{myId}")
//    public JournalEntry getEntrybyId(@PathVariable ObjectId myId){
//        return journalEntryService.findById(myId).orElse(null);
//    }
//
//    @DeleteMapping("id/{myId}")
//    public boolean DeleteEntrybyId(@PathVariable ObjectId myId){
//        journalEntryService.deleteById(myId);
//        return true;
//
//    }
//
//    @PutMapping("/id/{id}")
//    public JournalEntry UpdateBYId(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
//        JournalEntry old=journalEntryService.findById(id).orElse(null);
//
//        if(old!=null){
//            old.setTitle(newEntry!=null && !newEntry.getTitle().equals("")?newEntry.getTitle(): old.getTitle());
//            old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent(): old.getContent());
//        }
//        journalEntryService.saveEntry(old);
//        return old;
//    }
//
//
//}



// by using the response entity
package net.engineeringdigest.journalApp.controller;


import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User userInDb=userService.findByUserName(userName);
        if(userInDb !=null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveNewUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById(@RequestBody User user){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
