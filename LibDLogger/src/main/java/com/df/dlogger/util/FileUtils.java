/*
 * Copyright JiongBull 2016
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.df.dlogger.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.df.dlogger.logger.DLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 文件工具类.
 */
public class FileUtils {

    private static final String TAG = "FileUtils";


    /** 读写文件的线程池，单线程模型. */
    private static ExecutorService sExecutorService;

    static {
        sExecutorService = Executors.newSingleThreadExecutor();
    }

    private FileUtils() {}

    /**
     * 判断文件或目录是否存在.
     *
     * @param filePath 路径
     * @return true - 存在，false - 不存在
     */
    public static boolean isExist(@NonNull String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 创建目录，若目录已存在则不处理.
     *
     * @param dirPath 目录路径
     * @return true - 目录存在（创建成功或已存在），false - 目录不存在
     */
    public static boolean createDir(@NonNull String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            return file.mkdirs();
        }
        return file.exists();
    }

    /**
     * 把文本写入文件中.
     *
     * @param dirPath    目录路径
     * @param fileName   文件名
     * @param content    待写内容
     * @param isOverride 写入模式，true - 覆盖，false - 追加
     */
    public static void write(@NonNull final String dirPath, @NonNull final String fileName,
                             @NonNull final String content, final boolean isOverride) {
        sExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                String filePath = dirPath + File.separator + fileName;
                FileOutputStream fos = null;
                try {
                    if (createDir(dirPath)) {
                        File file = new File(filePath);
                        boolean isExist = file.exists();
                        fos = new FileOutputStream(file, !(!isExist || isOverride));
                        fos.write(content.getBytes(DLog.getInstance().getSetting().getCharset()));
                    }
                } catch (IOException e) {
                    Log.e(TAG, "write(...)#catch", e);
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            Log.e(TAG, "write(...)#finally", e);
                        }
                    }
                }
            }
        });
    }


    /**
     * 删除多个文件
     *
     * @param filePaths
     * @return
     */
    public static void delFile(@NonNull final List<String> filePaths) {
        sExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < filePaths.size(); i++) {
                    delFile(filePaths.get(i));
                }
            }
        });
    }

    /**
     * 根据给出路径自动选择删除文件或整个文件夹
     *
     * @param filePath :文件或文件夹路径
     */
    public static boolean delFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();// 删除文件
        }

        File[] subFiles = file.listFiles();
        for (File subfile : subFiles) {
            delFile(subfile.getAbsolutePath());// 删除当前目录下的子目录
        }
        return file.delete();// 删除当前目录
    }


    /**
     * 获取当前文件夹下全部文件
     *
     * @param dirName 文件目录名
     * @return 若不存在或者不为目录返回null, 否则返回文件列表, 可能回空
     */
    public static List<String> listFiles(@NonNull String dirName) {
        return listFiles(dirName, 0);
    }

    /**
     * 获取文件夹下文件
     *
     * @param dirName 文件目录
     * @param deep    -- 表示深度，0代表当前目录下的文件，1表示1层，以此类推
     * @return 若不存在或者不为目录返回null, 否则返回文件列表, 可能回空
     */
    public static List<String> listFiles(@NonNull String dirName, int deep) {
        if (!dirName.endsWith(File.separator)) {
            dirName = dirName + File.separator;
        }
        File dirFile = new File(dirName);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return null;
        }
        List<String> list = new ArrayList<>();
        File[] files = dirFile.listFiles();
        if (0 == deep) {
            for (int i = 0; i < files.length; i++) {
                list.add(files[i].getAbsolutePath());
            }
            return list;
        }

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                list.addAll(listFiles(files[i].getAbsolutePath(), deep - 1));
            } else {
                list.add(files[i].getAbsolutePath());
            }
        }

        return list;
    }

    /**
     * 拼接文件路径
     *
     * @param dirPath  目录名称
     * @param fileName 文件名称
     * @return
     */
    public static String makePath(String dirPath, String fileName) {
        if (dirPath.endsWith(fileName)) {
            return dirPath;
        }

        int index = fileName.indexOf(File.separator);
        if (index > -1) {
            if (dirPath.endsWith(fileName.substring(index))) {
                return dirPath;
            }
        }

        if (dirPath.endsWith(File.separator))
            return dirPath + fileName;
        return dirPath + File.separator + fileName;
    }

    /**
     * 获取文件名（不带后缀）
     *
     * @param filename
     * @return
     */
    public static String getNameFromFilename(String filename) {
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition != -1) {
            return filename.substring(0, dotPosition);
        }
        return "";
    }

    /**
     * 获取文件后缀名
     *
     * @param filename
     * @return
     */
    public static String getExtFromFilename(String filename) {
        int dotPosition = filename.lastIndexOf('.');
        if (dotPosition != -1) {
            return filename.substring(dotPosition + 1, filename.length());
        }
        return "";
    }


    /**
     * 文件拷贝，不支持目录拷贝
     *
     * @param fromPath 来源文件完整路径
     * @param toPath   目标文件目录
     * @return new file path if successful, or return null
     */
    private static String copyOnlyFile(@NonNull String fromPath, @NonNull String toPath) {
        File file = new File(fromPath);
        if (!file.exists() || file.isDirectory()) { return null; }

        FileInputStream fi = null;
        FileOutputStream fo = null;
        try {
            fi = new FileInputStream(file);
            File destPlace = new File(toPath);
            if (!destPlace.exists()) {
                if (!destPlace.mkdirs())
                    return null;
            }

            String destPath = FileUtils.makePath(toPath, file.getName());
            File destFile = new File(destPath);
            int i = 1;
            while (destFile.exists()) {
                String destName = FileUtils.getNameFromFilename(file.getName())
                        + "(" + i++ + ")."
                        + FileUtils.getExtFromFilename(file.getName());
                destPath = FileUtils.makePath(toPath, destName);
                destFile = new File(destPath);
            }

            if (!destFile.createNewFile())
                return null;

            fo = new FileOutputStream(destFile);
            int count = 102400;
            byte[] buffer = new byte[count];
            int read = 0;
            while ((read = fi.read(buffer, 0, count)) != -1) {
                fo.write(buffer, 0, read);
            }

            // TODO: set access privilege

            return destPath;
        } catch (Exception e) {
            Log.e(TAG, "copyOnlyFile(...)#catch", e);
        } finally {
            try {
                if (fi != null)
                    fi.close();
                if (fo != null)
                    fo.close();
            } catch (IOException e) {
                Log.e(TAG, "copyOnlyFile(...)#finally", e);
            }
        }

        return null;
    }

    /**
     * 文件拷贝，支持目录拷贝
     *
     * @param fromPath 来源文件目录
     * @param toPath   目标文件目录
     * @return 返回真实的目标文件
     */
    public static String copyFile(@NonNull String fromPath, @NonNull String toPath) {
        if (TextUtils.isEmpty(fromPath) || TextUtils.isEmpty(toPath)) { return null; }

        File fromFile = new File(fromPath);
        if (!fromFile.exists()) return null;

        if (fromFile.isDirectory()) {
            // directory exists in destination, rename it
            // String destPath = FileUtils.makePath(toPath, fromPath);
            File toFile = new File(toPath);
            String toPathTemp = toPath;
            int i = 1;
            while (toFile.exists()) {
                toPath = toPathTemp + "(" + i++ + ")";
                toFile = new File(toPath);
            }

            for (File child : fromFile.listFiles()) {
                FileUtils.copyOnlyFile(child.getAbsolutePath(), toPath);
            }
        } else {
            String destFile = FileUtils.copyOnlyFile(fromPath, toPath);
            //  FileInfo destFileInfo = FileUtil.GetFileInfo(destFile);
            //  ops.add(ContentProviderOperation.newInsert(FileUtil.getMediaUriFromFilename(destFileInfo.fileName))
            //          .withValue(MediaStore.Files.FileColumns.TITLE, destFileInfo.fileName)
            //          .withValue(MediaStore.Files.FileColumns.DATA, destFileInfo.filePath)
            //          .withValue(MediaStore.Files.FileColumns.MIME_TYPE, FileUtil.getMimetypeFromFilename(destFileInfo.fileName))
            //          .withValue(MediaStore.Files.FileColumns.DATE_MODIFIED, destFileInfo.ModifiedDate)
            //          .withValue(MediaStore.Files.FileColumns.SIZE, destFileInfo.fileSize).build());
        }

        return toPath;
    }


}