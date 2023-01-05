import org.w3c.dom.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class JCustomButton extends JButton {
    public JCustomButton (String text) {
        final Color BUTTONS_COLOR = new Color(52, 88, 48);
        final Color TEXT_COLOR = new Color(148, 236, 190);

        this.setText(text);
        this.setBackground(BUTTONS_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setFocusable(false);
        this.setFont(new Font("ComicSans", Font.BOLD, 36));
        this.setForeground(TEXT_COLOR);
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.TRACKING, 0.1);
        this.setFont(this.getFont().deriveFont(attributes));
    }
}
