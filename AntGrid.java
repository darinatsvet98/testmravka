import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* AntGrid for the Grid.
*
* @version 1.0
*/

public class AntGrid implements Grid {
  public static final String ANSI_RESET = "\u001B[0m";   //ANSI_RESET
  public static final String COLOR_0 = "\u001B[47m";   //ANSI_WHITE_BACKGROUND
  public static final String COLOR_1 = "\u001B[37;40m";  //ANSI_WHITE;ANSI_BLACK_BACKGROUND
  public static final String COLOR_2 = "\u001B[42m";   //ANSI_GREEN_BACKGROUND
  public static final String COLOR_3 = "\u001B[41m";   //ANSI_RED_BACKGROUND
  public static final String COLOR_4 = "\u001B[37;44m";  //ANSI_WHITE;ANSI_BLUE_BACKGROUND
  public static final String COLOR_5 = "\u001B[43m";   //ANSI_YELLOW_BACKGROUND
  public static final String COLOR_6 = "\u001B[46m";   //ANSI_CYAN_BACKGROUND
  public static final String COLOR_7 = "\u001B[45m";   //ANSI_PURPLE_BACKGROUND
  public static final String COLOR_8 = "\u001B[36;41m";  //ANSI_CYAN;ANSI_RED_BACKGROUND
  public static final String COLOR_9 = "\u001B[31;44m";  //ANSI_RED;ANSI_BLUE_BACKGROUND
  public static final String COLOR_10 = "\u001B[34;43m"; //ANSI_BLUE;ANSI_YELLOW_BACKGROUND
  public static final String COLOR_11 = "\u001B[32;45m"; //ANSI_GREEN;ANSI_PURPLE_BACKGROUND

  private  Map<Coordinate, Ant> antsMap;
  private static String[] colors= {COLOR_0,COLOR_1,COLOR_2,COLOR_3,COLOR_4,COLOR_5,COLOR_6,
    COLOR_7,COLOR_8,COLOR_9,COLOR_10,COLOR_11};
  private int X;
  private int Y;
  private String rule;
  private AntCell[][] cells;
  public static int totalStepCount;

  /**
   * Set the value of rule.
   *
   * @param rule the value of rule
   */
  public void setRule(String rule) {
    this.rule = rule;
  }

  /**
   * Get the value of cells.
   *
   * @return the value of cells
   */
  public AntCell[][] getCells() {
    return cells;
  }
  
  /**
   * Get the rule.
   *
   * @return the String rule
   */
  public String getRule() {
    return rule;
  }

  /**
   * Set the value of cells.
   *
   * @param cells the value of cells
   */
  public void setCells(AntCell[][] cells) {
    this.cells = cells;
  }
  
  /**
   * Set the AntGrid with X and Y.
   *
   * @param X Y rule
   */
  public AntGrid(int X, int Y, String rule) {
    this.X = X;
    this.Y = Y;
    this.antsMap = new HashMap<Coordinate, Ant>();
    this.rule = rule;
    InitCells();
  }
  
  /**
   * Init Cells.
   */
  public void InitCells() {
    setCells(new AntCell[X][Y]);
    int colorCount = rule.toCharArray().length;
    for( int i = 0;i<getY();i++) {
      for( int j=0;j<getX();j++) {
        getCells()[i][j]=new AntCell(colorCount,i,j);
      }
    }

  }

  /**
   * Get the value of Y.
   *
   * @return the value of Y
   */
  public int getY() {
    return Y;
  }

  /**
   * Set the value of Y.
   *
   * @param Y the value of Y
   */
  public void setY(int Y) {
    this.Y = Y;
  }

  /**
   * Get the value of X.
   *
   * @return the value of X
   */
  public int getX() {
    return X;
  }

  /**
   * Set the value of X.
   *
   * @param X the value of X
   */
  public void setX(int X) {
    this.X = X;
  }

  /**
   * Get the value of colors.
   *
   * @return the value of colors
   */
  public static String[] getColors() {
    return colors;
  }

  /**
   * Set the value of colors.
   *
   * @param colors the value of colors
   */
  public void setColors(String[] colors) {
    AntGrid.colors = colors;
  }

  /**
   * Set the value of antsMap.
   *
   * @param antsMap the value of antsMap
   */
  public void setAntsMap(Map<Coordinate, Ant> antsMap) {
    this.antsMap = antsMap;
  }

  /**
   * Constructs a string builder for the game field
   * 
   * @return game field as String without ANSI escape sequences.
   */
  public String Print() {
    StringBuilder sb = new StringBuilder();
    for( int i = 0;i<getY();i++) {
      for( int j=0;j<getX();j++) {
        Coordinate c = new Coordinate(j,i);
        if ( getAnts().containsKey(c)) {
          sb.append(getAnts().get(c).getDirection().toString());
          continue;
        }
        AntCell cell = getCells()[j][i];
        int colorIndex = cell.getColorIndex();
        String colornmr;
        if (colorIndex > 9) colornmr =  String.valueOf((char) (colorIndex + 55));
        else colornmr = String.valueOf(colorIndex);
        sb.append(colornmr);
      }
      if (i<(getY()-1)) {
        sb.append('\n');
      }
    }
    return sb.toString();
  }

  /**
   * Constructs a string builder for the game field
   *  
   * @return game field as String including ANSI escape sequences.
   */
  public String PrintANSIescapes() {
    StringBuilder sb = new StringBuilder();
    for( int i = 0;i<getY();i++) {
      for( int j=0;j<getX();j++) {
        Coordinate c = new Coordinate(j,i);
        if ( getAnts().containsKey(c)) {
          sb.append(getAnts().get(c).getDirection().toString());
          continue;
        }
        AntCell cell = getCells()[j][i];
        int colorIndex = cell.getColorIndex();
        String colornmr;
        if (colorIndex > 9) colornmr = Character.toString((char) (colorIndex + 55));
        else colornmr = Character.toString((char) colorIndex);
        sb.append(cell.getState()).append(colornmr).append(ANSI_RESET);
      }
      if (i<(getY()-1)) {
        sb.append('\n');
      }
    }
    return sb.toString();
  }

  /**
   * Set ant into cell.
   */
  @Override
  public void setAnt(Ant object, int col, int row) {
    if ( !getAnts().isEmpty()) {
      Shell.error("You already have Ant on the field");
      return;
    }
    Coordinate c = new  Coordinate(col,row);
    getAnts().put( c , object);
    //getCells()[col][row].incrementColor();
  }

  /**
   * Get ant on Grid.
   * 
   * @return antsMap
   */
  @Override
  public Map<Coordinate, Ant> getAnts() {
    return antsMap;
  }

  /**
   * Delete Ant.
   * 
   */
  @Override
  public void clearAnts() {
    getAnts().clear();
    // InitCells();
  }

  /**
   * Clear Playing field.
   */
  public void clearPlayField() {
    getAnts().clear();
    InitCells();
  }

  /**
   * Compute steps.
   */
  @Override
  public void performStep() {
    if ( getAnts().values().isEmpty() ) {
      Shell.error("There is not any ant");
      return;
    }
    Ant ant;
    for (Coordinate c : getAnts().keySet()) {
      ant = getAnts().get(c);
      Coordinate newCoordinate =  ant.getDirection().computeAnSetNextCell(ant.getCurrentCell());
      Coordinate oldCoordinate = new Coordinate(ant.getCurrentCell().getX(), ant.getCurrentCell().getY());
      AntCell newCell = getCells()[newCoordinate.getX()][newCoordinate.getY()];
      ant.getCurrentCell().incrementColor();
      getAnts().remove(oldCoordinate);
      ant.setCurrentCell(newCell);

      getAnts().put(newCoordinate, ant);
      ant.incrementStepCount();
      // System.out.println("steps performed "+ant.getStepCount());
      totalStepCount = ant.getStepCount();
    }
  }

  /**
   * Compute int number of steps.
   * 
   * @param int number
   */
  @Override
  public void performStep(int number) {
    for( int i=0;i<number;i++)
      performStep();
  }

  /**
   * Reset to state number steps ago.
   * 
   * @param int number
   */
  @Override
  public void reset(int number) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Get x - dimension.
   * 
   */
  @Override
  public int getWidth() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * Get y - dimension.
   * 
   */
  @Override
  public int getHeight() {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  /**
   * get Column from Cells list.
   * 
   * @param int i
   */
  @Override
  public List<Cell> getColumn(int i) {
    List<Cell> list = new ArrayList<>();
    for( int j = 0;i<getY();j++ )
      list.add(getCells()[j][i]);
    return list;
  }

  /**
   * get Row from Cells list.
   * 
   * @param int j
   */
  @Override
  public List<Cell> getRow(int j) {
    List<Cell> list = new ArrayList<>();
    for( int i = 0;i<getX();i++ )
      list.add(getCells()[j][i]);
    return list;
  }

  /**
   * Resize is look for which cell is the same from new grid is the same as the old grid.
   * The x and y belong to the new gridand the newX and newY  are to the old grid
   * 
   * @param rows
   * @param cols
   */
  @Override
  public void resize(int rows, int cols) {
    int width = Y;
    int height = X;
    
    int dWidth = (width - rows);
    int dHeight = (height - cols);
    
    AntCell[][] newCells = new AntCell[rows][cols];

    int startX = dWidth / 2;
    int startY = dHeight / 2;
    
    int colorCount = rule.toCharArray().length;
    
    for (int x = 0; x < rows; x++) {
      for (int y = 0; y < cols; y++) {
        int newX = x + startX;
        int newY = y + startY;
        
        if (newX < 0 || newY < 0 || newX >= X || newY >= Y) {
          newCells[x][y] = new AntCell(colorCount, x, y);
        } else {
          newCells[x][y] = getCells()[newX][newY];
          newCells[x][y].setX(x);
          newCells[x][y].setY(y);
        }
      }
    }

    this.X = rows;
    this.Y = cols;
    
    setCells(newCells);

    for (Coordinate c : getAnts().keySet()) {
      int newX = c.getX() - startX;
      int newY = c.getY() - startY;

      if (newX < 0 || newY < 0 || newX >= rows || newY >= cols) {
        getAnts().remove(c);
      }
      else {
        Ant oldAnt = getAnts().get(c);
        Ant newAnt = new Ant(getCells()[newX][newY], getRule(), getX(), getY() );
        newAnt.setStepCount(oldAnt.getStepCount());
        newAnt.setDirection(oldAnt.getDirection());
        newAnt.getDirection().setMaxX(rows);
        newAnt.getDirection().setMaxY(cols);

        getAnts().clear();
        setAnt(newAnt, newX, newY);
      }	
    }
  }

  /**
   * clear grid.
   * 
   */
  @Override
  public void clear() {
    setCells(new AntCell[X][Y]);
    getAnts().clear();
  }

  /**
   * get number of steps.
   * 
   * @param int return number of steps
   */
  @Override
  public int getStepCount() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
