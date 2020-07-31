package greenred.game;

public class Grid {
    private boolean[][] grid;
    private int height;
    private int width;

    public Grid(int height, int width) {
        if (height >= 1000 || width >= 1000 || height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Invalid grid size");
        }
        this.height = height;
        this.width = width;
        grid = new boolean[height][width];
        initialise();
    }

    public void initialise() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = false;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean getCell(int i, int j) {
        if (i >= height || j >= width || i < 0 || j < 0) {
            throw new IllegalArgumentException("Cell is out of bound");
        }
        return grid[i][j];
    }

    public void setCell(int i, int j, boolean value) {
        if (i >= height || j >= width || i < 0 || j < 0) {
            throw new IllegalArgumentException("Cell is out of bound");
        }
        grid[i][j] = value;
    }
}
