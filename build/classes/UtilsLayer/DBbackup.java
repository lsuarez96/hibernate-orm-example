package UtilsLayer;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class DBbackup {

    private String hostName, port, userName, dbName, password, pgVersion;

    public DBbackup() {

    }

    public DBbackup(String hostName, String port, String userName, String dbName, String password, String pgVersion) {
        this.hostName = hostName;
        this.port = port;
        this.userName = userName;
        this.dbName = dbName;
        this.password = password;
        this.pgVersion = pgVersion;

    }

    public void performBackup() {
        List<String> baseCmds = new ArrayList<>();
        String pgDumpAddress;

        pgDumpAddress = "C:/Program Files/PostgreSQL/" + pgVersion + "/bin/pg_dump";

        baseCmds.add(pgDumpAddress);
        baseCmds.add("-h");
        baseCmds.add(hostName);
        baseCmds.add("-p");
        baseCmds.add(port);
        baseCmds.add("-U");
        baseCmds.add(userName);
        baseCmds.add("-b");
        baseCmds.add("-v");
        baseCmds.add("-f");
        String backupName;
        int i = 1;
        String date= DateFormat.getDateInstance().format(Calendar.getInstance().getTime());
       
        backupName = "backup" + " "+date+"("+String.valueOf(i)+")" + ".sql";
        while (new File("backup" + " "+date+"("+String.valueOf(i)+")" + ".sql").exists()) {
            backupName = "backup" + " "+date+"("+String.valueOf(++i)+")" + ".sql";
        }
        baseCmds.add(backupName);
        baseCmds.add(dbName);
        final ProcessBuilder pb = new ProcessBuilder(baseCmds); // Set the password
        final Map<String, String> env = pb.environment();
        env.put("PGPASSWORD", password);
        try {
            final Process process = pb.start();
            final BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = r.readLine();
            while (line != null) {
                System.err.println(line);
                line = r.readLine();
            }
            r.close();
            final int dcertExitCode = process.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DBbackup("localhost", "5432", "postgres", "rentaCar", "0000", "9.3").performBackup();
    }
}
