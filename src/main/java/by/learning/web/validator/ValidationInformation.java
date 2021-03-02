package by.learning.web.validator;

public enum ValidationInformation {
    SUCCESS("success"),
    FAIL("fail"),
    TITLE_INCORRECT("incorrect title"),
    IMAGE_PATH_INCORRECT("incorrect path"),
    DESCRIPTION_INCORRECT("incorrect description"),
    PRICE_INCORRECT("incorrect price"),
    TRAILER_LINK_INCORRECT("incorrect link");

    private final String value;

    ValidationInformation(String value) {
        this.value = value;
    }

    public String getIssueValue() {
        return value;
    }
}
