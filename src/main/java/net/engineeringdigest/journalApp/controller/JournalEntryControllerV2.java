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


import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {


    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    //@GetMapping
    // This returns all entries of all users
//    public ResponseEntity<?> getAll(){
//        List<JournalEntry>all=journalEntryService.getAll();
//
//        if(all!=null && !all.isEmpty()){
//            return new ResponseEntity<>(all,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUserName(userName);
        List<JournalEntry>all =user.getJournalEntries();

        if(all !=null){
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

//    @PostMapping
//    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){ //localhost:8080/journal when req is post
//        try {
//            myEntry.setDate(LocalDateTime.now());
//            journalEntryService.saveEntry(myEntry);
//            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
//
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//    }
    // for perrticular user entry
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){ //localhost:8080/journal when req is post
        try {
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName=authentication.getName();
            myEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(myEntry ,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getEntrybyId(@PathVariable ObjectId myId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntry>journalEntry=journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @DeleteMapping("id/{myId}")
//    //it delets only from journalentries not from user
//    public ResponseEntity<?> DeleteEntrybyId(@PathVariable ObjectId myId){
//        journalEntryService.deleteById(myId);
//
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//
//    }

    @DeleteMapping("id/{myId}")

    public ResponseEntity<?> DeleteEntrybyId(@PathVariable ObjectId myId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        journalEntryService.deleteById(myId,userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping("/id/{myId}")
    public ResponseEntity<?>UpdateBYId(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();

        User user=userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(myId))
                .collect(Collectors.toList());

        if(!collect.isEmpty()){
            Optional<JournalEntry>journalEntry=journalEntryService.findById(myId);
            if(journalEntry.isPresent()){
                    JournalEntry old =journalEntry.get();
                    old.setTitle(newEntry!=null && !newEntry.getTitle().equals("")?newEntry.getTitle(): old.getTitle());
                    old.setContent(newEntry.getContent()!=null && !newEntry.getContent().equals("") ? newEntry.getContent(): old.getContent());
                    journalEntryService.saveEntry(old);
                    return new ResponseEntity<>(old,HttpStatus.OK);

            }
        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
