package com.lenovo.androidrepositories.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * 文件夹工具类
 * 
 * @author lenovo
 * 
 */
public class FolderUtil {

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件
	 * @param newPath
	 *            String 复制后文件
	 * @return boolean
	 */
	public static void copyFile(File oldPath, File newPath) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(oldPath);
			fo = new FileOutputStream(newPath);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fi != null) {
				try {
					fi.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fo != null) {
				try {
					fo.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}

	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件
	 * @param newPath
	 *            String 复制后文件
	 * @return boolean
	 */
	public static void copyFolder(File oldPath, File newPath) {
		if (!newPath.exists())
			newPath.mkdirs();
		File[] files = oldPath.listFiles();
		for (File file : files) {
			if (file.isFile()) {
				copyFile(file, new File(newPath, file.getName()));
			} else {
				copyFolder(file, new File(newPath, file.getName()));
			}
		}
	}

	/**
	 * 删除文件夹/文件
	 * 
	 * @param file
	 *            要删除的文件/文件夹
	 */
	public static void deleteFolder(File file) {
		if (!file.exists())
			return;
		if (!file.delete()) {
			File[] f = file.listFiles();
			for (File fs : f) {
				deleteFolder(fs);
			}
		}
		file.delete();
	}

	private static long length = 0;

	/**
	 * 获取文件夹总长度
	 * 
	 * @param file
	 * @return
	 */
	public static long getFolderLength(File file) {
		if (!file.exists())
			return -1;
		length=0;
		getFolder(file);
		return length;
	}

	/**
	 * 遍历文件夹
	 * 
	 * @param file
	 */
	private static void getFolder(File file) {
		if (file.isFile()) {
			length += file.length();
		} else {
			File[] fs = file.listFiles();
			for (File f : fs) {
				getFolder(f);
			}
		}
	}

}
