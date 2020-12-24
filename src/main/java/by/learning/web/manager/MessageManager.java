package by.learning.web.manager;

import java.util.Locale;
import java.util.ResourceBundle;

public enum MessageManager {
    EN(ResourceBundle.getBundle("property.text", new Locale("en", "US"))),
    BY(ResourceBundle.getBundle("property.text", new Locale("be", "BY")));

    private ResourceBundle bundle;

    MessageManager(ResourceBundle resourceBundle) {
        this.bundle = resourceBundle;
    }

    public String getMessage(String key) {
        return bundle.getString(key);
    }
}
