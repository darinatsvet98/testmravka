/**
* Coordinates sets and gets.
*
* @version 1.0
*/
public class Coordinate {

  private int X;
  private int Y;

  public Coordinate(int X, int Y) {
    this.X = X;
    this.Y = Y;
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
   * @param Y new value of Y
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
   * @param X new value of X
   */
  public void setX(int X) {
    this.X = X;
  }

  /**
   * Compare the coordinates.
   *
   * @param obj as Object
   * @return boolean
   */
  @Override
  public boolean equals(Object obj) {
    Coordinate c = (Coordinate)obj;
    if( getX() == c.getX() && getY()==c.getY()) {
      return true;
    }
    return false;
  }

  /**
   * Calculates the hashCode.
   *
   * @return int the hash code
   */
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + this.X;
    hash = 59 * hash + this.Y;
    return hash;
  }
}
