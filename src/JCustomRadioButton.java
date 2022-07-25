import javax.swing.*;
import java.awt.*;

public class JCustomRadioButton extends JRadioButton {
    public final Color CIRCLE_COLOR = new Color(52, 88, 48);
    public final Color CHECKED_COLOR = new Color(148, 236, 190);
    public JCustomRadioButton() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBackground(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int ly = (getHeight() - 16) / 2;
        if (isSelected()) {
            g2.setColor(CHECKED_COLOR);
            g2.drawRoundRect(1, ly, 17, 17, 200, 200);
            g2.setColor(CHECKED_COLOR);
            g2.fillRoundRect(4, ly + 3, 12, 12, 200, 200);
        } else {
            g2.setColor(CIRCLE_COLOR);
            g2.fillRoundRect(1, ly, 16, 16, 200, 200);
        }
        g2.dispose();
    }
}
