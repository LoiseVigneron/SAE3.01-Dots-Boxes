package fr.univlille.dotsboxes.color;

public enum Color {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[36m"),
    RESET("\u001B[0m");

    private final String colorCode;

    Color(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    @Override
    public String toString() {
        return colorCode;
    }
}
