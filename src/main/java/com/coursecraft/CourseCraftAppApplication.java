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
			// appDao.truncateTable("student_course");
			// appDao.truncateTable("user_session");
			// appDao.truncateTable("course");
			// appDao.truncateTable("student");
			// appDao.truncateTable("admin");
			// appDao.truncateTable("instructor");
			// appDao.truncateTable("user");
			if (appDao.queryWith(Admin.class, "email", "kamarf505@gmail.com", 1).isEmpty()) {
				Admin me = new Admin("kamarf505@gmail.com", "123456789", "Kamar El-Dawla", "Shalaby", "EGY");
				appDao.save(me);
				System.out.println(
						appDao.save(new UserSession(me, "00000000-0000-0000-0000-000000000000")).getSessionUuid());
			}
			/*
			 * try (Scanner in = new Scanner(System.in)) {
			 * while (true)
			 * {
			 * System.out.print("1-Create\n2-Find\n3-Modify\n4-Delete\n");
			 * int choice = in.nextInt();
			 * 
			 * switch (choice) {
			 * case 1 -> {
			 * System.out.print("email: ");
			 * String email = in.next();
			 * System.out.print("password: ");
			 * String password = in.next();
			 * 
			 * appDao.save(new Student(email, password));
			 * }
			 * case 2 -> {
			 * System.out.print("email: ");
			 * String email = in.next();
			 * 
			 * System.out.println(appDao.queryWith(User.class, "email", email, 1));
			 * }
			 * case 4 -> {
			 * System.out.print("email: ");
			 * String email = in.next();
			 * appDao.delete(appDao.queryWith(User.class, "email", email, 1));
			 * System.out.println();
			 * }
			 * case 5 -> {
			 * break;
			 * }
			 * }
			 * }
			 * }
			 */
		};
	}
}
