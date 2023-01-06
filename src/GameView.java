import javax.swing.*;
import java.awt.*;
public class GameView extends JPanel {
    private final GameModel gameModel;
    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        setBackground(new Color(30, 63, 32));
        setPreferredSize(new Dimension(gameModel.getGridSize() + 2 * gameModel.getMargin(), gameModel.getGridSize() + 2 * gameModel.getMargin()));
        setFont(new Font("ComicSans", Font.BOLD, 60));
    }
    private void drawCenteredString(Graphics g, String s, int x, int y) {
        int tileSize = gameModel.getTileSize();
        /** draw the string to the center of the tile **/
        FontMetrics fontMetrics = g.getFontMetrics();
        int asc = fontMetrics.getAscent();
        int desc = fontMetrics.getDescent();
        g.drawString(s, x + (tileSize - fontMetrics.stringWidth(s)) / 2, y + (asc + (tileSize - (asc + desc)) / 2));
    }
    private void drawGrid(Graphics g) {
        int[] tiles = gameModel.getTiles();
        int panelSize = gameModel.getPanelSize();
        int margin = gameModel.getMargin();
        int tileSize = gameModel.getTileSize();
        boolean gameOver = gameModel.isGameOver();
        final Color TEXT_COLOR = new Color(148, 236, 190);
        final Color TILE_COLOR = new Color(52, 88, 48);

        for (int i = 0; i < tiles.length; i++) {
            /** converting the tile coords from 1D to 2D **/
            int row = i / panelSize;
            int column = i % panelSize;

            int x = margin + column * tileSize;
            int y = margin + row * tileSize;

            /** checking if it's the blank tile **/
            if (tiles[i] == 0) {
                if (gameOver) {
                    break;
                }
                continue;
            }

            /** for the other tiles **/
            g.setColor(TILE_COLOR);
            g.setColor(TILE_COLOR);
            g.fillRoundRect(x, y, tileSize, tileSize, 10, 10);
            g.setColor(TEXT_COLOR);
            g.drawRoundRect(x, y, tileSize, tileSize, 10, 10);
            g.setColor(TEXT_COLOR);

            drawCenteredString(g, String.valueOf(tiles[i]), x, y);
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g);
    }
}
