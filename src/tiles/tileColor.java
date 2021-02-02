package tiles;

import java.awt.*;

public enum tileColor {
    YELLOW(new Color(255, 242, 204)),
    GREEN(new Color(213, 232, 212)),
    BLUE(new Color(0, 79, 238)),
    RED(new Color(248, 206, 204));

    public Color color;

    tileColor(Color c) {
        color = c;
    }
}
