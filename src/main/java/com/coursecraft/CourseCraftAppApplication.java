package com.coursecraft;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.coursecraft.dao.AppDao;
import com.coursecraft.entity.Admin;
import com.coursecraft.entity.UserSession;

@SpringBootApplication
public class CourseCraftAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseCraftAppApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	CommandLineRunner commandLineRunner(AppDao appDao) {
		return runner -> {
			if (appDao.queryWith(Admin.class, "email", "kamarf505@gmail.com", 1).isEmpty()) {
				Admin me = new Admin("kamarf505@gmail.com", "123456789", "Kamar El-Dawla", "Shalaby", "EGY");
				appDao.save(me);
				appDao.save(new UserSession(me, "00000000-0000-0000-0000-000000000000")).getSessionUuid();
			}
		};
	}

}
