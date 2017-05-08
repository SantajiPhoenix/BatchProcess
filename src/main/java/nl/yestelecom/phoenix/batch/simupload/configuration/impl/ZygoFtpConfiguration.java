package nl.yestelecom.phoenix.batch.simupload.configuration.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import nl.yestelecom.phoenix.batch.simupload.configuration.FTPConfiguration;

@Configuration
public class ZygoFtpConfiguration implements FTPConfiguration {

    @Value("${process.zygo.host}")
    private String sftpHost;
    @Value("${process.zygo.port}")
    private int sftpPort;
    @Value("${process.zygo.username}")
    private String username;
    @Value("${process.zygo.password}")
    private String password;
    @Value("${process.zygo.privatekey}")
    private String privateKey;
    @Value("${process.zygo.remotedirectory}")
    private String remoteDirectory;

    @Override
    public String getSftpHost() {
        return sftpHost;
    }

    @Override
    public int getSftpPort() {
        return sftpPort;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPrivateKey() {
        return privateKey;
    }

    @Override
    public String getRemoteDirectory() {
        return remoteDirectory;
    }

}
