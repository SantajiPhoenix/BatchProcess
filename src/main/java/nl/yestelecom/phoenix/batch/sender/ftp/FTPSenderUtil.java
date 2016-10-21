package nl.yestelecom.phoenix.batch.sender.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

import nl.yestelecom.phoenix.batch.sender.SenderVisitorImpl;

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
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	public void setSession(String userName, String host, int port, String password) {
		logger.info("Setting sessions");
		try {
			session = jSch.getSession(userName, host, port);
			if (password != null && !password.isEmpty()) {
				session.setPassword(password);
			}
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	public void setChannel() {
		logger.info("Setting channel");
		java.util.Properties configuration = new java.util.Properties();
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
		} catch (JSchException e) {
			logger.error(e.getMessage());
		}
	}

	public void transferFile(String fileName, String filePath, String remoteDirectory) {
		ChannelSftp channelSftp = null;
		logger.info("Sening file");
		try {
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(remoteDirectory);
			File f = new File(filePath + fileName);
			channelSftp.put(new FileInputStream(f), f.getName());
			logger.info("File Sent");
		} catch (SftpException | FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (channelSftp != null) {
				channelSftp.disconnect();
				channelSftp.exit();
			}
		}
	}

}
