package by.learning.web.util;

import by.learning.web.exception.ServiceException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * <pre>Used to load games logo images into server folder.</pre>
 *
 * @author Illia Aheyeu
 */
public class CustomFileReader {
    //Folder FROM where images are LOADED
    private static final String UPLOAD_DIRECTORY = "C:\\logo";
    //Folder where pictures are UPLOADED
    private static final String UPLOAD_PROJECT_DIRECTORY = "C:\\Users\\illya\\Desktop\\Epam\\Epam Learning\\Servlet\\src\\main\\webapp\\img\\logo";
    private static final String JPG_FORMAT = "jpg";
    private static CustomFileReader instance;

    private CustomFileReader() {
    }

    public static CustomFileReader getInstance() {
        if (instance == null) {
            instance = new CustomFileReader();
        }
        return instance;
    }

    public byte[] readAndWriteImage(String fileName) throws ServiceException {
        byte[] result;
        String fileUri = UPLOAD_DIRECTORY + File.separator + fileName;
        Path filePath = Path.of(fileUri);
        if (Files.exists(filePath)) {
            try {
                result = Files.readAllBytes(filePath);
                String path = UPLOAD_PROJECT_DIRECTORY + File.separator + fileName;
                if (!Files.exists(Path.of(path))) {
                    File file = new File(fileUri);
                    BufferedImage bufferedImage = ImageIO.read(file);
                    ImageIO.write(bufferedImage, JPG_FORMAT, new File(path));
                }
            } catch (IOException e) {
                throw new ServiceException(e);
            }
        } else {
            throw new ServiceException("File does not exist");
        }
        return result;
    }
}
