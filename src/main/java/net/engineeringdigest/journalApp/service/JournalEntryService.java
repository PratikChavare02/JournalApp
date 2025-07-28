package net.engineeringdigest.journalApp.service;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    // this is the dependency injection
    private JournalEntryRepository journalEntryRepo;

    @Autowired
    private UserService userService;

    private static final Logger logger= LoggerFactory.getLogger((JournalEntryService.class));

    //@transactional when it is used on any method ,the function will executes when the operations in that method are executes properly otherwise if any operation gets false it will rollback
    // To use Transactional use the @EnableTransactionalManagement on main fun  it says to spring find the methods are using @Transactional when it found it makes the corresponding Transactional context(it finds them wrap all the operations in method into single container in which all db operations are present related to db)
    //it simply means @Transactional uses the property atomicity and isolation
    // THe transactional management is done by the PlatformTransactionalManager (which is the interface do the work of rollback,transaction start and it is implemented by MongoTRansactionalMAnager)
    //creatte the bean to say the platformtransactionalmanager has the implementation of mongotransactional manager in main fun

    @Transactional
    public void saveEntry(JournalEntry je, String userName){
       try{
           User user =userService.findByUserName(userName);
           //journalEntry.setDate(LocalDateTime.now());
           JournalEntry saved= journalEntryRepo.save(je);
           // adding journalentry in arraylist of user entity class
           user.getJournalEntries().add(saved);
           // saving user in db
           userService.saveUser(user);
       } catch (Exception e) {
//           System.out.println(e);
           logger.info("hahhahhhh");
           throw new RuntimeException("An error is happened",e);
       }

    }
    public void saveEntry(JournalEntry je){

        journalEntryRepo.save(je);

    }
    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepo.findById(id);
    }
    @Transactional
    public void deleteById(ObjectId id, String userName){
        try{
            User user =userService.findByUserName(userName);
            boolean removed= user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if(removed){
                userService.saveUser(user);
                journalEntryRepo.deleteById(id);

            }

        } catch (Exception e){
                log.error("Error found",e);
                throw new RuntimeException("An error Occured while deleting entry",e);
            }
    }
//    public List<JournalEntry> findByUserName(String userName){
//
//    }

}
