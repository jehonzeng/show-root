package com.szhengzhu.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    /**
     * 得到文件路径
     * 
     * @date 2019年5月5日 上午11:48:45
     * @param fileNameWithPath
     * @return
     */
    public static String getFilePath(String fileNameWithPath) {
        String filePath = "";
        fileNameWithPath = fileNameWithPath.replace("/", File.separator);
        int idx = fileNameWithPath.lastIndexOf(File.separator);
        if (idx != -1) {
            filePath = fileNameWithPath.substring(0, idx);
        }
        return filePath;
    }

    /**
     * 将内容写入文件
     * 
     * @date 2019年5月5日 上午11:49:16
     * @param context  内容
     * @param fileName 目标文件
     * @throws Exception
     */
    public static void writeFile(String context, String fileName) throws Exception {
        byte[] bytes = context.getBytes();
        File file = new File(fileName);
        FileOutputStream fop = new FileOutputStream(file);
        BufferedOutputStream out = new BufferedOutputStream(fop);
        if (!file.exists()) {
            file.mkdirs();
        }
        out.write(bytes);
        out.flush();
        out.close();
    }

    /**
     * 复制文件
     * 
     * @date 2019年5月5日 上午11:49:52
     * @param srcFileName  源文件
     * @param destFileName 目标文件
     * @throws Exception
     */
    public static void copyFile(String srcFileName, String destFileName) throws Exception {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(new File(srcFileName)));
            String destPath = getFilePath(destFileName);
            File pathFile = new File(destPath);
            if (!pathFile.exists()) {
                pathFile.mkdirs();
            }
            out = new BufferedOutputStream(new FileOutputStream(new File(destFileName)));
            int len;
            byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            System.out.println("复制文件失败：" + e.getMessage());
            throw e;
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception ex) {
            }
            try {
                if (out != null)
                    out.close();
            } catch (Exception ex) {
            }
        }
    }

    public static boolean isImgSuffix(String suffix) {
        String suffixList = ".jpg,.png,.ico,.bmp,.jpeg";
        return suffixList.contains(suffix);
    }

    /**
     * 文件上传 文件修改后上传保存，如果源文件不是空，则覆盖源文件，如果源文件为空，则创建新文件
     * 
     * @date 2019年5月5日 下午5:24:13
     * @param file
     * @param savePath
     */
    public static void uploadImg(MultipartFile file, String savePath) {
        if (StringUtils.isEmpty(savePath))
            throw new RuntimeException("There is no save path for setting files on the server");

        File superPath = new File(getFilePath(savePath));
        if (!superPath.exists()) {
            if (!superPath.mkdirs())
                throw new RuntimeException("Failed to create file upload path: " + savePath);
        }
        try {
            File savePathFile = new File(superPath.getAbsolutePath(),
                    savePath.substring(savePath.lastIndexOf(File.separator) + 1)); // transferTo上传需要绝对路径
            file.transferTo(savePathFile);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }
}
