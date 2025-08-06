package com.adish.myfriend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
public class MyFriendApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFriendApplication.class, args);
	}

	@Bean
	public PlatformTransactionManager PTM(MongoDatabaseFactory factoryDB){
		return new MongoTransactionManager(factoryDB);
	}
}
