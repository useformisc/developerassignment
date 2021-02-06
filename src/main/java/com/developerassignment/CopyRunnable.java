package com.developerassignment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.developerassignment.DeveloperassignmentApplication.Content;
import com.developerassignment.DeveloperassignmentApplication.Ext;

public class CopyRunnable implements Runnable {

	private Path dir;
	private String outputFolder;
	private Ext ext;

	public CopyRunnable(Path dir, String outputFolder, Ext ext) {
		this.dir = dir;
		this.outputFolder = outputFolder;
		this.ext = ext;
	}

	@Override
	public void run() {
		try {
			makeDirectory(Paths.get(outputFolder + ext + "\\" + dir.getFileName()));
			File[] jsonfiles = extracted(dir.toFile(), ext);
			copyFiles(jsonfiles, ext);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void makeDirectory(Path dir) throws IOException {
		if (!Files.exists(dir)) {
			Files.createDirectory(dir);
		}
	}

	private void copyFiles(File[] files, Ext ext) throws IOException {
		String extPathStr = ConstantsClass.OUTPUT_FOLDER + ext;
		for (File file : files) {
			String parentPathStr = extPathStr + "\\" + file.toPath().getParent().getFileName();
			String newPathStr = parentPathStr + "\\" + file.toPath().getFileName();
			Path newPath = Paths.get(newPathStr);
			Files.copy(file.toPath(), newPath);
			String fileStr = Files.readString(file.toPath());
			if (fileStr.equals(Content.EMAIL.toString()) || fileStr.equals(Content.MOBILE_NUMBER.toString())) {
				Path qf = Paths.get(parentPathStr + "\\quaratine_folder");
				makeDirectory(qf);
				Path qfFile = Paths.get(qf.toString() + "\\" + file.toPath().getFileName());
				Files.copy(file.toPath(), qfFile);
			}
		}
	}

	private File[] extracted(File folder, Ext ext) {
		return folder.listFiles((dir, name) -> name.endsWith("." + ext));
	}
}
