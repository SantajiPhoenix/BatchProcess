package nl.yestelecom.phoenix.batch.simupload.configuration;

public interface FTPConfiguration {

    public String getSftpHost();

    public int getSftpPort();

    public String getUsername();

    public String getPassword();

    public String getPrivateKey();

    public String getRemoteDirectory();

    public String getRemotePukDirectory();

    public String getremoteSimpUploadDirectory();

}
