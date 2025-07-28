package net.engineeringdigest.journalApp.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

// to map this with our collection in db use
@Document (collection ="journalEntry")//this says to spring that this entity is mapped with the mongodb collection
@Data
@NoArgsConstructor
public class JournalEntry {


    @Id // making id to unique key (primary key)
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;



}
