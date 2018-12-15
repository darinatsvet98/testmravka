/**
* AntCell for color position of ant.
*
* @version 1.0
*/

public class AntCell implements Cell{
  private int x;
  private int y;
  private int colorIndex;
  private int colCount;

  /**
   * Get the value of y.
   *
   * @return the value of y
   */
  public int getY() {
    return y;
  }

  /**
   * Set the value of y.
   *
   * @param y new value of y
   */
  public void setY(int y) {
    this.y = y;
  }

  /**
   * Get the value of x.
   *
   * @return the value of x
   */
  public int getX() {
    return x;
  }

  /**
   * Set the value of x.
   *
   * @param x new value of x
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * Get the value of colCount.
   *
   * @return the value of colCount
   */
  public int getColCount() {
    return colCount;
  }

  /**
   * Set the value of colCount.
   *
   * @param colCount new value of colCount
   */
  public void setColCount(int colCount) {
    this.colCount = colCount;
  }

  /**
   * Set the value of colorIndex
   *
   */
  public void incrementColor() {
    if ( colorIndex == (colCount-1))
      colorIndex=0;
    else
      colorIndex ++;
  }

  /**
   * Get the state.
   *
   * @return String of getColorIndex
   */
  @Override
  public String getState() {
    if (getColorIndex()<0 || getColorIndex()>11)
      return AntGrid.ANSI_RESET;
    else {
      return AntGrid.getColors()[getColorIndex()];
    }
  }
  /**
   * Set the AntCell.
   *
   * @param colCount x y
   */
  public AntCell(int colCount, int x, int y) {
    this.colCount = colCount;
    this.x = x;
    this.y = y;
    colorIndex = 0;
  }

  /**
   * Get the value of color.
   *
   * @return the value of color
   */
  public int getColorIndex() {
    return colorIndex;
  }

  /**
   * Set the value of color.
   *
   * @param color new value of color
   */
  public void setColorIndex(int color) {
    this.colorIndex = color;
  }


}
