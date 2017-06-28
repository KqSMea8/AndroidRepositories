package com.lenovo.androidrepositories.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 文件压缩解压工具类
 *
 * @author lenovo
 */
public class ZipUtil {
    /**
     * @param src  要压缩的文件路径
     * @param dest 生成的zip路径
     * @throws IOException
     */
    public static void zip(String src, String dest) {
        // 提供了一个数据项压缩成一个ZIP归档输出流
        ZipOutputStream out = null;
        try {
            File outFile = new File(dest);// 源文件或者目录
            if (!outFile.getParentFile().exists()) {
                outFile.getParentFile().mkdirs();
            }
            File fileOrDirectory = new File(src);// 压缩文件路径
            out = new ZipOutputStream(new FileOutputStream(outFile));
            // 如果此文件是一个文件，否则为false。
            if (fileOrDirectory.isFile()) {
                zipFileOrDirectory(out, fileOrDirectory, "");
            } else {
                // 返回一个文件或空阵列。
                File[] entries = fileOrDirectory.listFiles();
                for (File file : entries) {
                    // 递归压缩，更新curPaths
                    zipFileOrDirectory(out, file, "");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // 关闭输出流
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩文件集合
     *
     * @param list 文件集合
     * @param dest 保存路径
     */
    public static void zipFileList(List<File> list, String dest) {
        File outFile = new File(dest);// 源文件或者目录
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(outFile));
            for (File file : list) {
                // 递归压缩，更新curPaths
                zipFileOrDirectory(out, file, "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭输出流
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 压缩文件
     *
     * @param out             压缩流
     * @param fileOrDirectory 压缩文件保存路径
     * @param curPath
     * @throws IOException
     */
    private static void zipFileOrDirectory(ZipOutputStream out,
                                           File fileOrDirectory, String curPath) {
        // 从文件中读取字节的输入流
        FileInputStream in = null;
        try {
            // 如果此文件是一个目录，否则返回false。
            if (fileOrDirectory.isFile()) {
                // 压缩文件
                in = new FileInputStream(fileOrDirectory);
                // 实例代表一个条目内的ZIP归档
                ZipEntry entry = new ZipEntry(curPath
                        + fileOrDirectory.getName());
                // 条目的信息写入底层流
                out.putNextEntry(entry);
                byte[] buffer = new byte[1024 * 32];
                int total = 0;
                int bytes_read = 0;
                while ((bytes_read = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytes_read);
                    total += bytes_read;
                }
                out.closeEntry();
            } else {
                // 压缩目录
                File[] entries = fileOrDirectory.listFiles();
                for (File file : entries) {
                    // 递归压缩，更新curPaths
                    zipFileOrDirectory(out, file,
                            curPath + fileOrDirectory.getName() + "/");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    /**
     * 解压文件
     *
     * @param zipFileName     要解压的zip文件
     * @param outputDirectory 解压后文件存放路径
     * @throws IOException
     */
    public static void unZip(String zipFileName, String outputDirectory) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipFileName);
            Enumeration e = zipFile.entries();
            ZipEntry zipEntry = null;
            File dest = new File(outputDirectory);
            dest.mkdirs();
            while (e.hasMoreElements()) {
                zipEntry = (ZipEntry) e.nextElement();
                String entryName = zipEntry.getName();
                InputStream in = null;
                FileOutputStream out = null;
                try {
                    if (zipEntry.isDirectory()) {
                        String name = zipEntry.getName();
                        name = name.substring(0, name.length() - 1);
                        File f = new File(outputDirectory + File.separator
                                + name);
                        f.mkdirs();
                    } else {
                        int index = entryName.lastIndexOf("\\");
                        if (index != -1) {
                            File df = new File(outputDirectory + File.separator
                                    + entryName.substring(0, index));
                            df.mkdirs();
                        }
                        index = entryName.lastIndexOf("/");
                        if (index != -1) {
                            File df = new File(outputDirectory + File.separator
                                    + entryName.substring(0, index));
                            df.mkdirs();
                        }
                        File f = new File(outputDirectory + File.separator
                                + zipEntry.getName());
                        in = zipFile.getInputStream(zipEntry);
                        out = new FileOutputStream(f);
                        int c = 0;
                        int total = 0;
                        byte[] by = new byte[1024];
                        while ((c = in.read(by)) != -1) {
                            out.write(by, 0, c);
                            total += c;
                        }
                        out.flush();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException ex) {
                        }
                    }
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException ex) {
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException ex) {
                }
            }
        }
    }

}