package com.developerassignment;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.developerassignment.DeveloperassignmentApplication.Ext;

public class SimpleFileVisitorExample extends SimpleFileVisitor<Path> {

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		String dirName = dir.getFileName().toString();
		if (dirName.equals("input")) {
			return FileVisitResult.CONTINUE;
		}
		ExecutorService fileThreadExecutor = Executors.newFixedThreadPool(3);
		Runnable jsonRunnable = new CopyRunnable(dir, ConstantsClass.OUTPUT_FOLDER, Ext.json);
		fileThreadExecutor.execute(jsonRunnable);

		Runnable txtRunnable = new CopyRunnable(dir, ConstantsClass.OUTPUT_FOLDER, Ext.txt);
		fileThreadExecutor.execute(txtRunnable);

		Runnable csvRunnable = new CopyRunnable(dir, ConstantsClass.OUTPUT_FOLDER, Ext.csv);
		fileThreadExecutor.execute(csvRunnable);

		return FileVisitResult.CONTINUE;

	}

	@Override
	public FileVisitResult postVisitDirectory(Path path, IOException ioException) {
		System.out.println(path + " visited.");
		return FileVisitResult.CONTINUE;

	}

}
