package spring_boot_stu.stu01;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class App {

	Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@RequestMapping("/hello")
	public String hello(String name) {
		logger.warn("哈哈啊，我来了 .......");
		return "hello " + name;
	}
}
