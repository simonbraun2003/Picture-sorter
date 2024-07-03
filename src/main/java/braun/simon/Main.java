package braun.simon;


import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Main {
    public static void main(final String[] args) {
        final File imageFile = new File("src/main/java/braun/simon/pictures/test2.JPEG");
        System.out.println(getCaptureDate(imageFile).get(Calendar.MONTH)+1);
    }

    public static Calendar getCaptureDate(File pImageFile){
        try {
            final Metadata metadata = ImageMetadataReader.readMetadata(pImageFile);
            final ExifSubIFDDirectory directory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
            if (directory != null) {
                final Date date = directory.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
                if (date != null) {
                    final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    final String formattedDate = formatter.format(date);
                    //System.out.println("Aufnahmedatum: " + formattedDate);
                    final Calendar calender = Calendar.getInstance();
                    calender.setTime(date);
                    return calender;
                } else {
                    System.out.println("Aufnahmedatum nicht gefunden.");
                }
            } else {
                System.out.println("Keine EXIF-Daten gefunden.");
            }
        } catch (final Exception e) {
            System.err.println("Fehler beim Auslesen der Bildmetadaten: " + e.getMessage());
        }
        return null;
    }
}