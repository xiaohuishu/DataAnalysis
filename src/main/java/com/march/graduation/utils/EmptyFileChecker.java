package com.march.graduation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EmptyFileChecker {

	private static final Logger logger = LoggerFactory.getLogger(EmptyFileChecker.class);

	public static boolean isFileEmpty1(File file) {
		FileInputStream fis = null;
		boolean flag = true;
		try {
			fis = new FileInputStream(file);
			if (fis.available() != 0) {
				flag = false;
			}
		} catch (FileNotFoundException e) {
			logger.error("[file: {}] is not found", file.getName());
		} catch (IOException e) {
			logger.error("[file: {}] read error: {}", file.getName(), e);
		} finally {
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException e) {
					logger.error("[file: {}] close error: {}", file.getName(), e);
				}
			}
		}

		return flag;
	}
	
	public static boolean isFileEmpty4(File file) {
		return file.length() == 0L;
	}
 }