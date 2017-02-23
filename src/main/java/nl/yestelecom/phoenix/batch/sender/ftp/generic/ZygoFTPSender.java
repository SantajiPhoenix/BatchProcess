package nl.yestelecom.phoenix.batch.sender.ftp.generic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public abstract class ZygoFTPSender {

    @Value("${zygo.ftp.host}")
    private String sftpHost;
    @Value("${zygo.ftp.port}")
    private int sftpPort;
    @Value("${zygo.ftp.username}")
    private String username;
    @Value("${zygo.ftp.password}")
    private String password;
    @Value("${zygo.ftp.privatekey}")
    private String privateKey;

    public String getSftpHost() {
        return sftpHost;
    }

    public int getSftpPort() {
        return sftpPort;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public abstract String getFileName() ;

    public abstract String getFilePath();

    public abstract String getRemoteDirectory();

}
