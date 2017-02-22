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

    private String sequence;
    private String remoteFileName;

    public void fileEncoderAndSender() {
        BufferedReader stdInput = null;
        BufferedReader stdError = null;
        String data = null;

        try {
            final String[] shell = { "/bin/bash" };
            final String command1_test = "scp " + filePath + " " + remoteFilePath;
            final String commands[] = { command1_test };

            // Commands for Encoding and sending Preventel in production

            // String command1 = "gpg -o " + filePath + getFileName() + " --recipient " + privateKey
            // + " -ea --trust-model always " + filePath + getRemoteFileName();
            // String command2 = "scp " + filePath + getFileName() + " " + userName + "@" + host + ":" + remoteFilePath
            // + getRemoteFileName();
            // String commands[] = { command1, command2 };

            final Process p = Runtime.getRuntime().exec(commands, shell);

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
        fileName = fileName + sequence;
        return fileName;
    }

    public String getRemoteFileName() {
        remoteFileName = fileName + ".txt.gpg";
        return remoteFileName;
    }

}
