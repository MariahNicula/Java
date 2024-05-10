package Properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsReader {
    private String repositoryType;
    private String patientsFileName;
    private String appointmentsFileName;

    public SettingsReader(String filePath) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
        }

        repositoryType = properties.getProperty("Repository");
        patientsFileName = properties.getProperty("RepoFile");
        appointmentsFileName = properties.getProperty("RepoFile2");
    }

    public String getRepositoryType() {
        return repositoryType;
    }

    public String getPatientsFileName() {
        return patientsFileName;
    }

    public String getAppointmentsFileName() {
        return appointmentsFileName;
    }
}