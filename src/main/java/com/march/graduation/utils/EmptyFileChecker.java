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

	//删除指定文件夹下所有文件
	//param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return false;
		}
		if (!file.isDirectory()) {
			return false;
		}
		String[] tempList = file.list();
		File temp;
		for (String aTempList : tempList) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + aTempList);
			} else {
				temp = new File(path + File.separator + aTempList);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + aTempList);//先删除文件夹里面的文件
				delFolder(path + "/" + aTempList);//再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	//删除文件夹
	//param folderPath 文件夹完整绝对路径

	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			File myFilePath = new File(folderPath);
			boolean success = myFilePath.delete(); //删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFileEmpty4(File file) {
		return file.length() == 0L;
	}
 }