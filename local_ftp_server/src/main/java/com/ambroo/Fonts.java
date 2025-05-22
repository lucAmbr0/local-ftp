package com.ambroo;

import java.awt.Font;
import java.io.IOException;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.font.TextAttribute;
import java.util.Map;

public class Fonts {
    public static final Font TITLE_FONT;
    public static final Font REGULAR_FONT;
    public static final Font BIG_TEXT_FONT;
    public static final Font SUBTITLE_FONT;
    public static final Font INFO_FONT;

    static {
        Font inter = null;
        try {
            // Load font from resources using class loader
            inter = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream("/Inter.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(inter);
            Main.logger.info("Font loaded");
        } catch (FontFormatException | IOException | NullPointerException e) {
            inter = new Font("SansSerif", Font.PLAIN, 12); // fallback
            Main.logger.error("Error loading font, using SansSerif as fallback.");
            System.out.println(e);
        }
        // Set custom weights using TextAttribute
        TITLE_FONT = inter.deriveFont(Map.of(
            TextAttribute.WEIGHT, TextAttribute.WEIGHT_ULTRABOLD,
            TextAttribute.SIZE, 22f
        ));
        REGULAR_FONT = inter.deriveFont(Map.of(
            TextAttribute.WEIGHT, TextAttribute.WEIGHT_REGULAR,
            TextAttribute.SIZE, 12f
        ));
        SUBTITLE_FONT = inter.deriveFont(Map.of(
            TextAttribute.WEIGHT, TextAttribute.WEIGHT_HEAVY,
            TextAttribute.SIZE, 16f
        ));
        BIG_TEXT_FONT = inter.deriveFont(Map.of(
            TextAttribute.WEIGHT, TextAttribute.WEIGHT_MEDIUM,
            TextAttribute.SIZE, 18f
        ));
        INFO_FONT = inter.deriveFont(Map.of(
            TextAttribute.WEIGHT, TextAttribute.WEIGHT_LIGHT,
            TextAttribute.SIZE, 12f
        ));
    }
}
