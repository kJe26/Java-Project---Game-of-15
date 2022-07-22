import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TiliToli extends JPanel {
    private int panelSize;     //size of the panel
    private int noTiles;       //number of tiles
    private int dimension;     //dimension of the grid UI
    private static final Color TILE_COLOR = new Color(127, 0, 255);             //tiles color
    private static final Color BACKGROUND_COLOR = new Color(153, 0, 76);        //BG color
    private static final Random rnd = new Random();     //random that shuffles the tiles
    private int[] tiles;       //tiles stored in a 1D array
    private int tileSize;      //size of tiles on the UI
    private int blankPos;      //position of blank tile
    private int margin;             //margin for grid on the frame
    private int gridSize;           //sizeof grid UI
    private boolean gameOver;       //true - game over, false - otherwise
    private int scoreCounter;       //counts how many click were made until game completed
    private JLabel score;           //score will be displayed on this

    public TiliToli(int panelSize, int dimension, int margin){
        this.panelSize = panelSize;
        this.dimension = dimension;
        this.margin = margin;

        this.noTiles = panelSize * panelSize - 1;
        this.tiles = new int[panelSize * panelSize];
        this.gridSize = (dimension - 2 * margin);
        this.tileSize = gridSize / panelSize;

        this.scoreCounter = 0;


        /** score counter display **/
        /*
        score = new JLabel();
        score.setBounds(dimension / 2, 0, dimension / 4, dimension / 2);
        score.setText("Your score: " + String.valueOf((scoreCounter)));
        score.setBackground(null);
        Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
        attributes.put(TextAttribute.TRACKING, 0.12);
        score.setFont(new Font("ComicSans", Font.BOLD, 18));
        score.setFont(score.getFont().deriveFont(attributes));
        score.setForeground(Color.WHITE);
        GameFrame.menuBar.add(score);*/

        setPreferredSize(new Dimension(dimension, dimension));
        setBackground(BACKGROUND_COLOR);
        setForeground(TILE_COLOR);
        setFont(new Font("ComicSans", Font.BOLD, 60));

        gameOver = true;

        /** for moving the tiles **/
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gameOver) {
                    newGame();
                } else {
                    /** get the position of the clicked tile **/
                    int ex = e.getX() - margin;
                    int ey = e.getY() - margin;

                    /** if clicked out of the grid **/
                    if (ex < 0 || ex > gridSize || ey < 0 || ey > gridSize)
                        return;

                    /** get the position in the grid **/
                    int gridColumn = ex / tileSize;
                    int gridRow = ey / tileSize;

                    /** get the position of the blank tile **/
                    int blankColumn = blankPos % panelSize;
                    int blankRow = blankPos / panelSize;

                    int clickPos = gridRow * panelSize + gridColumn;

                    int dir = 0;

                    /** checking for the direction the tile could go **/
                    if (gridColumn == blankColumn && Math.abs(gridRow - blankRow) > 0 && (gridRow + 1 == blankRow || gridRow - 1 == blankRow)) {
                        dir = (gridRow - blankRow) > 0 ? panelSize : -panelSize;
                    } else if (gridRow == blankRow && Math.abs(gridColumn - blankColumn) > 0 && (gridColumn + 1 == blankColumn || gridColumn - 1 == blankColumn)) {
                        dir = (gridColumn - blankColumn) > 0 ? 1 : -1;
                    }

                    /** move the tile in that direction **/
                    if (dir != 0) {
                        do {
                            int newBlankPos = blankPos + dir;
                            tiles[blankPos] = tiles[newBlankPos];
                            blankPos = newBlankPos;
                        } while (blankPos != clickPos);

                        tiles[blankPos] = 0;

                        /*
                        scoreCounter++;
                        score.setText("Your score: " + String.valueOf((scoreCounter)));*/
                    }

                    /** check if solved **/
                    gameOver = isSolved();
                }

                /** repaint the panel **/
                repaint();
            }
        });

        newGame();
    }

    private void newGame() {
        do{
            resetGame();
            shuffleTiles();
        } while(!isSolvable());
        gameOver = false;
    }

    private void resetGame() {
        for (int i = 0; i < tiles.length; ++i) {
            tiles[i] = (i + 1) % tiles.length;
        }

        blankPos = tiles.length - 1;
    }

    private void shuffleTiles() {
        int n = noTiles;
        while (n > 1){
            int rand = rnd.nextInt(n--);
            int temp = tiles[rand];
            tiles[rand] = tiles[n];
            tiles[n] = temp;
        }
    }

    private boolean isSolvable() {
        int noInversions = 0;
        for (int i = 0; i < noTiles; ++i) {
            for (int j = 0; j < i; ++j) {
                if (tiles[j] > tiles[i])
                    noInversions++;
            }
        }

        return noInversions % 2 == 0;
    }

    private boolean isSolved() {
        /** if the blank tile isn't on the last(solved) position -> isn't solved **/
        if (tiles[tiles.length - 1] != 0) {
            return false;
        }

        for (int i = noTiles - 1; i >= 0; --i) {
            if (tiles[i] != i + 1)
                return false;
        }

        return true;
    }

    private void drawGrid(Graphics2D g) {
        for (int i = 0; i < tiles.length; i++) {
            /** converting the tile coords from 1D to 2D **/
            int row = i / panelSize;
            int column = i % panelSize;

            int x = margin + column * tileSize;
            int y = margin + row * tileSize;

            /** checking if it's the blank tile **/
            if (tiles[i] == 0) {
                if (gameOver) {
                    g.setColor(TILE_COLOR);
                    drawCenteredString(g, "✓", x, y);
                }

                continue;
            }

            /** for the other tiles **/
            g.setColor(getForeground());
            g.fillRoundRect(x, y, tileSize, tileSize, 10, 10);
            g.setColor(Color.BLACK);
            g.drawRoundRect(x, y, tileSize, tileSize, 10, 10);
            g.setColor(Color.WHITE);

            drawCenteredString(g, String.valueOf(tiles[i]), x, y);
        }
    }

    private void drawStartMessage(Graphics2D g) {
        if (gameOver) {
            g.setFont(getFont().deriveFont(Font.BOLD, 18));
            g.setColor(TILE_COLOR);
            String s = "Click to start a new game";
            g.drawString(s, (getWidth() - g.getFontMetrics().stringWidth(s)) / 2, getHeight() - margin);
        }
    }

    private void drawCenteredString(Graphics2D g, String s, int x, int y) {
        /** draw the string to the center of the tile **/
        FontMetrics fontMetrics = g.getFontMetrics();
        int asc = fontMetrics.getAscent();
        int desc = fontMetrics.getDescent();
        g.drawString(s, x + (tileSize - fontMetrics.stringWidth(s)) / 2, y + (asc + (tileSize - (asc + desc)) / 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawGrid(graphics2D);
        drawStartMessage(graphics2D);
    }
}

