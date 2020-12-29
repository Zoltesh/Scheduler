package scheduler.tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author Brayden McArthur
 * @version 1.0
 * Contains methods for creating a log file.
 */
public class FileMaker {

    public FileMaker(){}

    /**
     * Provided a File Name and a Message, creates a file in the root directory of the Project. Tracks login attempts and
     * adds timestamps for each fail/success.
     * @param fileName A string that holds the File Name.
     * @param message A string that holds the message to be written/appended to the file.
     * @return Returns a boolean value. True if the file was created successfully or already exists. False if there is an
     * error creating the file.
     */
    public boolean make(String fileName, String message) {

        try {

            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write("Login Activity\n");
                bufferedWriter.close();
            }

            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("\n" + message);
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}