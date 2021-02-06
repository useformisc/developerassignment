package com.developerassignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeveloperassignmentApplication {

	private static final String FOLDER_PATH = ConstantsClass.INPUT_FOLDER + "Folder";
	private static final Random random = new Random();

	public static void main(String[] args) throws IOException {
		SpringApplication.run(DeveloperassignmentApplication.class, args);
		System.out.println("Up and Running");
		makeDirectory();
		readFiles();
	}

	public static void readFiles() throws IOException {
		Path outPutRoot = Paths.get(ConstantsClass.OUTPUT_FOLDER);
		makeDirectory(outPutRoot);
		for (Ext ext : Ext.values()) {
			Path dest = Paths.get(outPutRoot.toString() + "\\" + ext);
			makeDirectory(dest);
		}
		Path root = Paths.get(ConstantsClass.INPUT_FOLDER);
		Files.walkFileTree(root, new SimpleFileVisitorExample());
	}

	private static void makeDirectory(Path dir) throws IOException {
		if (!Files.exists(dir)) {
			Files.createDirectory(dir);
		}
	}

	public static void makeDirectory() throws IOException {
		int numOfDirs = ThreadLocalRandom.current().nextInt(1, 6);
		makeDirectory(numOfDirs);
		makeFiles(numOfDirs);
	}

	private static void makeFiles(int numOfDirs) throws IOException {
		for (int i = 1; i <= 50; i++) {
			int directory = ThreadLocalRandom.current().nextInt(numOfDirs);
			Path path = Paths.get(FOLDER_PATH + directory + "\\sample" + i + "." + randomEnum(Ext.class));
			Files.createFile(path);
			Files.writeString(path, randomEnum(Content.class).toString());

		}
	}

	private static void makeDirectory(int numOfDirs) throws IOException {
		for (int i = 0; i < numOfDirs; i++) {
			Path path = Paths.get(FOLDER_PATH + i);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
		}
	}

	public enum Content {
		MOBILE_NUMBER, EMAIL, OTHER
	}

	public enum Ext {
		csv, json, txt
	}

	public static <T extends Enum<?>> T randomEnum(Class<T> clazz) {
		int x = random.nextInt(clazz.getEnumConstants().length);
		return clazz.getEnumConstants()[x];
	}
}
