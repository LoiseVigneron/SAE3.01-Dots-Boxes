package fr.univlille.dotsboxes.textStyle;

public enum TextStyle {
    BOLD("\u001B[1m"),
    FAINT("\u001B[2m"),
    ITALIC("\u001B[3m"),
    UNDERLINE("\u001B[4m"),
    BLINK("\u001B[5m"),
    REVERSE("\u001B[7m"),
    HIDDEN("\u001B[8m"),
    STRIKETHROUGH("\u001B[9m"),
    RESET("\u001B[0m");

    private final String styleCode;

    TextStyle(String styleCode) {
        this.styleCode = styleCode;
    }

    public String getStyleCode() {
        return styleCode;
    }

    @Override
    public String toString() {
        return styleCode;
    }
}
