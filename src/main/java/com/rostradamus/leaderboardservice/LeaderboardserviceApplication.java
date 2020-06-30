package com.rostradamus.leaderboardservice;

import com.rostradamus.leaderboardservice.repository.LeaderboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LeaderboardserviceApplication implements ApplicationRunner {
	@Autowired
	LeaderboardRepository leaderboardRepository;


	public static void main(String[] args) {
		SpringApplication.run(LeaderboardserviceApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		leaderboardRepository.seedRandomData(10000000);
	}

}
