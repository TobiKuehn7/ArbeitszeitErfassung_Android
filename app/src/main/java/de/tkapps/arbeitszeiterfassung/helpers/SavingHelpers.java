package de.tkapps.arbeitszeiterfassung.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import de.tkapps.arbeitszeiterfassung.models.Workday;


public class SavingHelpers {

    /**
     * this function saves the start time of a workday
     * @param ctx           Context is needed for getting the file dir
     * @param dateTimeStart is the string that should be saved
     */
    public static void saveStartTime(Context ctx, String dateTimeStart) {
        // prepare strings
        String strStartTime = dateTimeStart + ",";

        // get file path for saving
        String[] filePathParts = getFilePath();
        String filename = filePathParts[1] + ".csv";
        String filepath = filePathParts[0];

        File myExternalFile = new File(ctx.getExternalFilesDir(filepath), filename);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(myExternalFile, true);
            fos.write(strStartTime.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveEndTime(Context ctx, String dateTimeStart, String dateTimeEnd, String workTimeBrutto, String workTimeNetto, String pauseTime) {
        // make hash from all data
        Workday workday = new Workday();
        workday.setDateTimeStart(dateTimeStart);
        workday.setDateTimeEnd(dateTimeEnd);
        workday.setWorkTimeBrutto(workTimeBrutto);
        workday.setWorkTimeNetto(workTimeNetto);
        workday.setPauseTime(pauseTime);

        String hash = hash(workday.toString());

        // prepare strings
        String strStartTime = dateTimeEnd + "," + workTimeBrutto + "," + workTimeNetto + "," + pauseTime + "," + hash + "\n";

        // get file path for saving
        String[] filePathParts = getFilePath();
        String filename = filePathParts[1] + ".csv";
        String filepath = filePathParts[0];

        File myExternalFile = new File(ctx.getExternalFilesDir(filepath), filename);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(myExternalFile, true);
            fos.write(strStartTime.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String hash(String str) {
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(str.getBytes(StandardCharsets.UTF_8));
            for (byte b : hash) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexString.toString();
    }

    /**
     * this function returns both parts of a file path (filedirectory, filename+extension)
     *
     * @return an array with the dir on 0 and file name on 1
     */
    public static String[] getFilePath() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date(System.currentTimeMillis());
        String strDate = formatter.format(date);
        String[] dateParts = strDate.split("-");
        String year = dateParts[2];
        String month = dateParts[1];
        String[] fileData = new String[2];
        fileData[0] = year + "/" + month + "/";
        fileData[1] = year + month;
        return fileData;
    }

    /**
     * this function gets the newest file and returns its content
     *
     * @param ctx Context is needed for getting the file dir
     * @return a string with the files content
     */
    public static String getFileContent(Context ctx) {
        // get file path for saving
        String[] filePathParts = getFilePath();
        String filename = filePathParts[1] + ".csv";
        String filepath = filePathParts[0];

        // init return variable
        String fileContents;

        FileReader fr;
        File myExternalFile = new File(ctx.getExternalFilesDir(filepath), filename);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fr = new FileReader(myExternalFile);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileContents = stringBuilder.toString();
        }
        return fileContents;
    }

    public static List<Workday> getAllWorkdays(Context ctx) {
        List<Workday> workdays = new ArrayList<>();
        String fileContents = getFileContent(ctx);

        if (!fileContents.isEmpty()) {
            if (fileContents.contains("\n")) {
                // file contains multiple Workdays
                String[] lines = fileContents.split("\n");
                for (String line : lines) {
                    Workday workday;
                    String[] values = line.split(",");
                    if (values.length == 1) {
                        // workday is just started and not finished
                        workday = new Workday();
                        workday.setDateTimeStart(values[0]);
                    } else {
                        // workday is complete
                        workday = new Workday(values[0], values[1], values[2], values[3], values[4], values[5]);
                    }
                    workdays.add(workday);
                }
            } else {
                // file just contains one workday
                Workday workday;
                String[] values = fileContents.split(",");

                // workday is just started and not finished
                workday = new Workday();
                workday.setDateTimeStart(values[0]);

                workdays.add(workday);
            }
        }

        return workdays;
    }

    public static boolean fileHasStartedWorkday(Context ctx) {
        List<Workday> workdays = getAllWorkdays(ctx);
        Workday lastWorkday = workdays.get(workdays.size() - 1);

        return lastWorkday.getHash() == null;
    }

    public static Workday getStartedWorkday(Context ctx) {
        List<Workday> workdays = getAllWorkdays(ctx);
        return workdays.get(workdays.size() - 1);
    }

}