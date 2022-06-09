package helpers;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHelper {
    public static void makePotwierdzenie(String sender, boolean senderEuro, int amount, String recievierAccount) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss");
            Date date = new Date();
            String currentDate = formatter.format(date);
            FileWriter myWriter = new FileWriter(currentDate + "-przelew.txt");
            myWriter.write("Potwiedzenie przelewu. \n Użytkownik: " + sender + " wysłał środki w ilości: " + amount + (senderEuro ? " EUR " : " PLN ") + "na rachunek: " + recievierAccount);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}