package nl.yestelecom.phoenix.batch.job.preventel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PreventelEncodedFileSender {

    private static Logger logger = LoggerFactory.getLogger(PreventelEncodedFileSender.class);

    @Value("${preventel.filePath}")
    private String filePath;
    @Value("${preventel.sftp.remoteDirectory}")
    private String remoteFilePath;
    @Value("${preventel.sftp.username}")
    private String userName;
    @Value("${preventel.sftp.host}")
    private String host;
    @Value("${preventel.sftp.privateKey}")
    private String privateKey;
    @Value("${preventel.fileName}")
    private String fileName;
    @Value("${preventel.env}")
    private String env;

    private String sequence;

    public void fileEncoderAndSender() {
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        String data = null;
        final String currentEnv = "TEST";

        try {
            final String[] shell = { "/bin/bash" };
            final String commandTest1 = "scp " + filePath + getFileName() + " " + userName + "@" + host + ":" + remoteFilePath;
            final String[] commandsTest = { commandTest1 };
            Process p;

            final String command1 = "gpg -o " + filePath + getFileName() + " --recipient " + privateKey + " -ea --trust-model always " + filePath + getRemoteFileName();
            final String command2 = "scp " + filePath + getFileName() + " " + userName + "@" + host + ":" + remoteFilePath + getRemoteFileName();
            final String[] commandsProd = { command1, command2 };

            if (env.equalsIgnoreCase(currentEnv)) {
                p = Runtime.getRuntime().exec(commandsTest, shell);
            } else {
                p = Runtime.getRuntime().exec(commandsProd, shell);
            }

            stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

            // read the output from the command
            while ((data = stdInput.readLine()) != null) {
                logger.info("Here is the standard output of the command:\n");
                logger.info(data);
            }

            // read any errors from the attempted command
            while ((data = stdError.readLine()) != null) {
                logger.error("Here is the standard error of the command (if any):\n");
                logger.error(data);
            }
        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getFileName() {
        return fileName + sequence;
    }

    public String getRemoteFileName() {
        return fileName + ".txt.gpg";

    }

}
