package com.wp.system.utils;

public enum CategoryColor {
    PURPLE("#F72DFB", "Фиолетовый"),
    RED("#FB392D", "Красный"),
    ORANGE("#FB5E2D", "Оранжевый"),
    YELLOW("#FAC91D", "Желтый"),
    GREEN("#4EEF16", "Зеленый"),
    LIGHT_GREEN("#2DFB8C", "Салатневый"),
    TURQUOISE("#2DC9FB", "Бирюзовый"),
    BLUE("#2D67FB", "Синий");

    private String colorHex;

    private String colorName;

    CategoryColor(String colorHex, String colorName) {
        this.colorHex = colorHex;
        this.colorName = colorName;
    }

    public String getColorName() {
        return colorName;
    }

    public String getColorHex() {
        return colorHex;
    }
}
