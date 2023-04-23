package com.example.adminApiWebGameBlog;

import com.example.adminApiWebGameBlog.service.FilesStorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class AdminApiWebGameBlogApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(AdminApiWebGameBlogApplication.class, args);
	}

	@Resource
	FilesStorageService storageService;

	@Override
	public void run(String... args) throws Exception {
//		storageService.deleteAll();
		storageService.init();
	}
}
