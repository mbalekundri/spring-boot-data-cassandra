package com.bezkoder.spring.data.cassandra;

import com.bezkoder.spring.data.cassandra.dao.TutorialDao;
import com.bezkoder.spring.data.cassandra.model.Tutorial;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDataCassandraApplication implements CommandLineRunner {

	@Autowired
	private TutorialDao tutorialDao;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDataCassandraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		tutorialDao.save(new Tutorial(Uuids.timeBased(), "Java 8", "Java 8 Series", false));
		tutorialDao.save(new Tutorial(Uuids.timeBased(), "Java 9", "Java 9 Series", false));
		tutorialDao.save(new Tutorial(Uuids.timeBased(), "Java 11", "Java 11 Series", false));
		tutorialDao.save(new Tutorial(Uuids.timeBased(), "Java 17", "Java 17 Series", false));
		tutorialDao.save(new Tutorial(Uuids.timeBased(), "Java 21", "Java 21 Series", false));

		System.out.println("Spring Boot Data Cassandra Application started successfully!");

	}
}
