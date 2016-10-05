package nl.yestelecom.phoenix.batch.sender.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Component
public class FTPSenderUtil {

	JSch jSch = null;
	Session session = null;
	Channel channel = null;

	@PostConstruct
	public void init() {
		jSch = new JSch();
	}

	public void addIdentity(String privateKey) {
		try {
			jSch.addIdentity(privateKey);
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	public void setSession(String userName, String password, int port) {
		try {
			session = jSch.getSession(userName, password, port);
			// if (password != null && !password.isEmpty()) {
			session.setPassword(password);
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	public void setChannel() {
		java.util.Properties configuration = new java.util.Properties();
		configuration.put("StrictHostKeyChecking", "no");
		session.setConfig(configuration);
	}

	public void connect() {
		try {
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
	}

	public void transferFile(String fileName, String filePath, String remoteDirectory) {
		ChannelSftp channelSftp = null;
		try {
			channelSftp = (ChannelSftp) channel;
			channelSftp.cd(remoteDirectory);
			File f = new File(fileName);
			channelSftp.put(new FileInputStream(f), f.getName());			
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
