package by.learning.web.validator;

import javax.mail.Folder;
import java.io.File;
import java.util.EnumSet;
import java.util.Set;

/**
 * <pre>Check {@link by.learning.web.model.entity.Game Game} input data.</pre>
 *
 * @author Illia Aheyeu
 */
public class GameValidator {
    private static final String TITLE_REGEX = "^[A-z0-9`\\s:]{2,35}$";
    private static final String DESCRIPTION_REGEX = "^[A-z0-9.,?!;:\\-()№'\"\\s®™*’“”]{10,300}";
    private static final String PRICE_REGEX = "^(\\d)*(\\.\\d{1,2})?$";
    private static final String TRAILER_LINK_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
    // Folder FROM where images are LOADED
    private static final String IMAGE_FOLDER_PATH = "C:\\logo\\";
    // Folder where pictures are UPLOADED
    private static final String IMAGE_PROJECT_PATH = "C:\\Users\\illya\\Desktop\\Epam\\Epam Learning\\Servlet\\src\\main\\webapp\\img\\logo";

    private GameValidator() {
    }

    public static boolean isTitleValid(String title) {
        boolean isValid = true;
        if ((title == null) || (!title.matches(TITLE_REGEX))) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isPathValid(String imagePath) {
        boolean isValid = true;
        if ((imagePath == null) || (!new File(imagePath).exists())) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isDescriptionValid(String description) {
        boolean isValid = true;
        if ((description == null) || (!description.matches(DESCRIPTION_REGEX))) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isPriceValid(String price) {
        boolean isValid = true;
        if ((price == null) || (!price.matches(PRICE_REGEX))) {
            isValid = false;
        }
        return isValid;
    }

    public static boolean isTrailerLinkValid(String trailerLink) {
        boolean isValid = true;
        if ((trailerLink == null) || (!trailerLink.matches(TRAILER_LINK_REGEX))) {
            isValid = false;
        }
        return isValid;
    }

    public static Set<ValidationInformation> findGameValidationIssues(String gameTitle, String imagePath,
                                                                      String description, String price, String trailerLink) {
        Set<ValidationInformation> issues = EnumSet.noneOf(ValidationInformation.class);
        if (!isTitleValid(gameTitle)) {
            issues.add(ValidationInformation.TITLE_INCORRECT);
        }
        String diskPath = IMAGE_FOLDER_PATH + imagePath;
        String projectPath = IMAGE_PROJECT_PATH + imagePath;
        if (!isPathValid(diskPath) && isPathValid(projectPath)) {
            issues.add(ValidationInformation.IMAGE_PATH_INCORRECT);
        }
        if (!isDescriptionValid(description)) {
            issues.add(ValidationInformation.DESCRIPTION_INCORRECT);
        }
        if (!isPriceValid(price)) {
            issues.add(ValidationInformation.PRICE_INCORRECT);
        }
        if (!isTrailerLinkValid(trailerLink)) {
            issues.add(ValidationInformation.TRAILER_LINK_INCORRECT);
        }
        if (!issues.isEmpty()) {
            issues.add(ValidationInformation.FAIL);
        }
        return issues;
    }
}
