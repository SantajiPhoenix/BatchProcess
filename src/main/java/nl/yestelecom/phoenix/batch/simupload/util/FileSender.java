package nl.yestelecom.phoenix.batch.simupload.util;

import java.io.File;
import java.io.FileInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import nl.yestelecom.phoenix.batch.simupload.configuration.impl.ZygoFtpConfiguration;

@Service
public class FileSender {

    private static final Logger LOG = LoggerFactory.getLogger(FileSender.class);

    public void send(ZygoFtpConfiguration zygoFtpConfiguration, String fileName) {
        final ChannelSftp channelSftp = getConnection(zygoFtpConfiguration);
        try {
            final File f = new File(fileName);
            channelSftp.put(new FileInputStream(f), f.getName());
            LOG.info(fileName + " is transfered successfully to host.");
        } catch (final Exception e) {
            LOG.error("Exceptiomn is >> " + e);
        }
    }

    private ChannelSftp getConnection(ZygoFtpConfiguration zygoFtpConfiguration) {
        // TODO Auto-generated method stub
        final JSch jSch = new JSch();
        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        try {
            if (zygoFtpConfiguration.getPrivateKey() != null && !zygoFtpConfiguration.getPrivateKey().isEmpty()) {
                jSch.addIdentity(zygoFtpConfiguration.getPrivateKey());
            }

            session = jSch.getSession(zygoFtpConfiguration.getUsername(), zygoFtpConfiguration.getSftpHost(), zygoFtpConfiguration.getSftpPort());

            if (zygoFtpConfiguration.getPassword() != null && !zygoFtpConfiguration.getPassword().isEmpty()) {
                session.setPassword(zygoFtpConfiguration.getPassword());
            }

            final java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();

            channel = session.openChannel("sftp");
            channel.connect();
            LOG.info("Shell channel connected....");

            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(zygoFtpConfiguration.getRemoteDirectory());
            LOG.info("Changed the directory to : " + zygoFtpConfiguration.getRemoteDirectory());
        } catch (final Exception e) {
            LOG.info("Exception is >> " + e);
        }
        return channelSftp;

    }

}
