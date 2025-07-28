package net.engineeringdigest.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement

//we can create the beans inside the main class when using @SpringBootApplication

public class JournalApplication {

	public static void main(String[] args) {

		 SpringApplication.run(JournalApplication.class, args);
	}
	//bean for saying platformtransactional manager is the implementation of mongotransactionall manager

	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
		return  new MongoTransactionManager(dbFactory);
	}

}
//MongoDatabaseFactory helps us to form connection with database
