/**
* Direction calculations.
*
* @version 1.0
*/

public class Direction {
  public enum StepDirection {
    None, Left, Right, Up, Down
  };

  private StepDirection prevStep;
  private StepDirection nextStep;
  private  int maxX;
  private  int maxY;
  private char[] rules;
  private int cellState;

  /**
   * Set the value of MaxX.
   *
   * @param maxX
   */
  public void setMaxX(int maxX) {
    this.maxX = maxX;
  }
  
  /**
   * Set the value of MaxY.
   *
   * @param maxY
   */
  public void setMaxY(int maxY) {
    this.maxY = maxY;
  }

  /**
   * Get the value of nextStep.
   *
   * @return the value of nextStep
   */
  public StepDirection getNextStep() {
    return nextStep;
  }

  /**
   * Set the value of nextStep.
   *
   * @param nextStep new value of nextStep
   */
  public void setNextStep(StepDirection nextStep) {
    this.nextStep = nextStep;
  }

  /**
   * Get the value of prevStep.
   *
   * @return the value of prevStep
   */
  public StepDirection getPrevStep() {
    return prevStep;
  }

  /**
   * Set the value of prevStep.
   *
   * @param prevStep new value of prevStep
   */
  public void setPrevStep(StepDirection prevStep) {
    this.prevStep = prevStep;
  }

  /**
   * Get the value of cellState.
   *
   * @return the value of cellState
   */
  public int getCellState() {
    return cellState;
  }

  /**
   * Set the value of cellState.
   *
   * @param cellState new value of cellState
   */
  public void setCellState(int cellState) {
    this.cellState = cellState;
  }

  /**
   * Get the value of rules.
   *
   * @return the value of rules
   */
  public char[] getRules() {
    return rules;
  }

  /**
   * Set the value of rules.
   *
   * @param rules new value of rules
   */
  public void setRules(char[] rules) {
    this.rules = rules;
  }

  /**
   * Set previous step compute next step and set maxX and maxY.
   *
   * @param cmd x y for rules computeNextStep
   */
  public Direction(String cmd, int x, int y) {
    rules = new char[cmd.toCharArray().length];
    for( int i=0;i<cmd.toCharArray().length;i++) {
      rules[i]=cmd.toCharArray()[i];
    }
    cellState =0;

    setPrevStep(Direction.StepDirection.None);
    computeNextStep();
    setMaxX(x);
    setMaxY(y);
  }

  /**
   * Get the value of maxX.
   *
   * @return the integer value of maxX
   */
  public int getMaxX() {
    return maxX;
  }

  /**
   * Get the value of maxY.
   *
   * @return the integer value of maxY
   */
  public int getMaxY() {
    return maxY;
  }

  /**
   * Compute and set next cell for ant.
   *
   * @param currentCell for position of ant
   */
  public Coordinate computeAnSetNextCell(AntCell currentCell) {
    Coordinate newCoordinate = new Coordinate(-1, -1);
    switch( getNextStep()) {
      case Left:
        newCoordinate.setY(currentCell.getY());
        setPrevStep(StepDirection.Left);
        if ( currentCell.getX() == 0 ) {
          newCoordinate.setX( getMaxX()-1 );
          break;
        }
        newCoordinate.setX( currentCell.getX()-1 );
        break;
      case Right:
        newCoordinate.setY(currentCell.getY());
        setPrevStep(StepDirection.Right);
        if ( currentCell.getX() == (getMaxX()-1) ) {
          newCoordinate.setX( 0 );
          break;
        }
        newCoordinate.setX( currentCell.getX()+1 );
        break;
      case Down:
        newCoordinate.setX(currentCell.getX());
        setPrevStep(StepDirection.Down);
        if ( currentCell.getY() == (getMaxY()-1) ) {
          newCoordinate.setY( 0 );
          break;
        }
        newCoordinate.setY( currentCell.getY()+1 );
        break;
      case Up:
        newCoordinate.setX(currentCell.getX());
        setPrevStep(StepDirection.Up);
        if ( currentCell.getY() == 0 ) {
          newCoordinate.setY( getMaxY()-1 );
          break;
        }
        newCoordinate.setY( currentCell.getY()-1 );
        break;
      default :
        break;
    }
    return newCoordinate;
  }

  /**
   * Compute next step for ant.
   *
   */
  public void computeNextStep() {
    switch( getPrevStep()) {
      case None:
      {
        setNextStep(StepDirection.Left);
/*        if ( getRules()[getCellState()] == 'L')
            setNextStep(StepDirection.Left);
          else
            setNextStep(StepDirection.Right);*/
        break;
      }
      case Left:
      {
        if ( getRules()[getCellState()] == 'L')
          setNextStep(StepDirection.Down);
        else
          setNextStep(StepDirection.Up);
        break;
      }
      case Right:
      {
        if ( getRules()[getCellState()] == 'L')
          setNextStep(StepDirection.Up);
        else
          setNextStep(StepDirection.Down);
        break;
      }
      case Up:
      {
        if ( getRules()[getCellState()] == 'L')
          setNextStep(StepDirection.Left);
        else
          setNextStep(StepDirection.Right);
        break;
      }
      case Down:
      {
        if ( getRules()[getCellState()] == 'L')
          setNextStep(StepDirection.Right);
        else
          setNextStep(StepDirection.Left);
        break;
      }
    }
  }

  @Override
  public String toString() {
    switch( getNextStep()) {
      case None:
        return "N";
      case Left:
        return "<";
      case Right:
        return ">";
      case Up:
        return "^";
      case Down:
        return "V";
    }
    return "N";
  }

}