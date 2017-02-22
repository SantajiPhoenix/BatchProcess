package nl.yestelecom.phoenix.batch.sender.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Component
public class FTPSenderUtil {

    private static Logger logger = LoggerFactory.getLogger(FTPSenderUtil.class);
    JSch jSch = null;
    Session session = null;
    Channel channel = null;

    @PostConstruct
    public void init() {
        jSch = new JSch();
    }

    public void addIdentity(String privateKey) {
        logger.info("Setting keys");
        try {
            jSch.addIdentity(privateKey);
        } catch (final JSchException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void setSession(String userName, String host, int port, String password) {
        logger.info("Setting sessions");
        try {
            session = jSch.getSession(userName, host, port);
            if (password != null && !password.isEmpty()) {
                session.setPassword(password);
            }
        } catch (final JSchException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void setChannel() {
        logger.info("Setting channel");
        final java.util.Properties configuration = new java.util.Properties();
        configuration.put("StrictHostKeyChecking", "no");
        session.setConfig(configuration);
    }

    public void connect() {
        logger.info("Test Connection");
        try {
            session.connect();
            channel = session.openChannel("sftp");
            channel.connect();
            logger.info("Connection Established");
        } catch (final JSchException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void transferFile(String fileName, String filePath, String remoteDirectory) {
        ChannelSftp channelSftp = null;
        FileInputStream inputStream = null;
        logger.info("Sening file");
        try {
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(remoteDirectory);
            final File f = new File(filePath + fileName);
            inputStream = new FileInputStream(f);
            channelSftp.put(inputStream, f.getName());
            logger.info("File Sent");

        } catch (SftpException | FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } finally {

            channelSftp.disconnect();
            channelSftp.exit();

            channel.disconnect();

            session.disconnect();
            try {
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (final IOException e) {
                logger.error(e.getMessage(), e);
            }

        }
    }

}
