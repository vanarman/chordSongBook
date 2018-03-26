import java.io.IOException;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Dmytro Sytnik (VanArman)
 * @version 06 March, 2018
 */


class LocalLogger {

    private static final Logger logger = Logger.getLogger(LocalLogger.class.getName());

    public LocalLogger(){
        String filePath = System.getProperty("user.dir")+System.getProperty("file.separator")+System.getProperty("file.separator")+"chordSongBook.log";
        Handler file = null;
        try {
            file = new FileHandler(filePath, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addHandler(Objects.requireNonNull(file));
        logger.setLevel(Level.INFO);
    }

    public void logInfo(String message){
        logger.info(message);
    }
}
