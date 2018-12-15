/**
* Ant step direction of ant.
*
* @version 1.0
*/

public class Ant {
  private int stepCount;
  private AntCell currentCell;
  private Direction direction;

  /**
   * Get the value of stepCount.
   *
   * @return the int value of stepCount
   */
  public int getStepCount() {
    return stepCount;
  }

  /**
   * Set the value of stepCount.
   *
   * @param stepCount the value of stepCount
   */
  public void setStepCount(int stepCount) {
    this.stepCount = stepCount;
  }

  /**
   * Increment Step Count.
   *
   */
  public void incrementStepCount() {
    stepCount++;
  }

  /**
   * Current Ant Cell and Rules.
   * 
   * @param currentCell moveRulls X Y
   */
  public Ant(AntCell currentCell, String moveRulls, int X, int Y) {
    this.currentCell = currentCell;
    setDirection(new Direction(moveRulls, X,Y) );
    getDirection().setCellState(getCurrentCell().getColorIndex());
    stepCount=0;
  }

  /**
   * Get the value of currentCell.
   *
   * @return the value of currentCell
   */
  public AntCell getCurrentCell() {
    return currentCell;
  }

  /**
   * Set the value of currentCell.
   *
   * @param currCell the value of currentCell
   */
  public void setCurrentCell(AntCell currCell) {
    this.currentCell = currCell;
    if (getDirection() != null) {
      getDirection().setCellState(currCell.getColorIndex());
      getDirection().computeNextStep();
    }
  }

  /**
   * Get the value of direction.
   *
   * @return the value of direction
   */
  public Direction getDirection() {
    return direction;
  }

  /**
   * Set the value of direction.
   *
   * @param direction the value of direction
   */
  public void setDirection(Direction direction) {
    this.direction = direction;
  }
  
  /**
   * Get the value of orientation.
   *
   * @return the direction
   */
  public Direction getOrientation () {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
