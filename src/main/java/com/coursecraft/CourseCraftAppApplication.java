package com.coursecraft;

import java.util.Scanner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.coursecraft.daos.AppDao;
import com.coursecraft.entities.Student;
import com.coursecraft.entities.User;

@SpringBootApplication
public class CourseCraftAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseCraftAppApplication.class, args);
	}

	// @Bean
	CommandLineRunner commandLineRunner(AppDao appDao) {
		return runner -> {
			Scanner in = new Scanner(System.in);
			while (true)
			{
				System.out.print("1-Create\n2-Find\n3-Modify\n4-Delete\n");
				int choice = in.nextInt();

				switch (choice) {
					case 1 -> {
						System.out.print("email: ");
						String email = in.next();
						System.out.print("password: ");
						String password = in.next();
						
						appDao.save(new Student(email, password));
					}
					case 2 -> {
						System.out.print("email: ");
						String email = in.next();

						System.out.println(appDao.queryWith(User.class, "email", email, 1));
					}
					case 4 -> {
						System.out.print("email: ");
						String email = in.next();
						appDao.delete(appDao.queryWith(User.class, "email", email, 1));
						System.out.println();
					}
					case 5 -> {
						break;
					}
				}
				in.close();
			}
		};
	}
}
