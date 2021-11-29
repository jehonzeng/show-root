package com.szhengzhu.config;

import cn.hutool.extra.ftp.Ftp;
import lombok.Data;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

/**
 * @author Administrator
 */
@Component
@Data
public class FtpConfig {

    /** ftp服务器地址 */
    @Value("${ftp.server}")
    private String hostname;

    /** ftp服务器端口 */
    @Value("${ftp.port}")
    private int port;

    /** ftp登录账号 */
    @Value("${ftp.userName}")
    private String username;

    /** ftp登录密码 */
    @Value("${ftp.userPassword}")
    private String password;

    /** ftp保存目录 */
    @Value("${ftp.basePath}")
    private String basePath;

    public boolean upload(String savePath, String newFileName, InputStream inputStream) throws IOException {
        Ftp ftp = new Ftp(hostname, port, username, password);
        ftp.getClient().setFileType(FTP.BINARY_FILE_TYPE);
        ftp.getClient().enterLocalPassiveMode();
        return ftp.upload(basePath + savePath, newFileName, inputStream);
    }

    public FTPClient getFtpClient() {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setControlEncoding("utf-8");

        try {
            ftpClient.setDataTimeout(1000 * 120);// 设置连接超时时间
            System.out.println("connecting...ftp服务器:" + hostname + ":" + port);
            ftpClient.connect(hostname, port); // 连接ftp服务器
            ftpClient.login(username, password); // 登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); // 是否成功登录服务器
            // 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
            FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS UTF8", "ON"));

            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("connect failed...ftp服务器:" + hostname + ":" + port);
            }
            System.out.println("connect successful...ftp服务器:" + hostname + ":" + port);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }
}
