public class GameModel {
    private final int panelSize;     //size of the panel
    private final int noTiles;       //number of tiles
    private final int[] tiles;       //tiles stored in a 1D array
    private final int tileSize;      //size of tiles on the UI
    private int blankPos;      //position of blank tile
    private final int margin;             //margin for grid on the frame
    private final int gridSize;           //sizeof grid UI
    private boolean gameOver;       //true - game over, false - otherwise
    private final GameFrame gameFrame;

    public GameModel(int panelSize, int dimension, int margin, GameFrame gameFrame) {
        this.panelSize = panelSize;
        this.noTiles = panelSize * panelSize - 1;
        this.tiles = new int[panelSize * panelSize];
        this.gridSize = (dimension - 2 * margin);
        this.tileSize = gridSize / panelSize;
        this.blankPos = -1;
        this.margin = margin;
        this.gameFrame = gameFrame;

        gameFrame.setScoreCounter(0);
        gameFrame.setScore();

        this.gameOver = false;
    }

    public int getPanelSize() {
        return panelSize;
    }

    public int getNoTiles() {
        return noTiles;
    }

    public int[] getTiles() {
        return tiles;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getBlankPos() {
        return blankPos;
    }

    public void setBlankPos(int blankPos) {
        this.blankPos = blankPos;
    }

    public int getMargin() {
        return margin;
    }

    public int getGridSize() {
        return gridSize;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public GameFrame getGameFrame() {
        return gameFrame;
    }
}
