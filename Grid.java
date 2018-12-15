/**
* Interface Grid.
*
* @version 1.0
*/

import java.util.List;
import java.util.Map;

public interface Grid {
  void setAnt ( Ant object , int col , int row ); // set ant into cell
  Map < Coordinate , Ant > getAnts (); // get all ants on Grid
  void clearAnts (); // delete all ants on Grid

  void performStep (); // compute next step
  void performStep ( int number ); // compute next n steps
  void reset (int number ); // reset to state n steps ago

  int getWidth (); // x- dimension
  int getHeight (); // y- dimension

  List <Cell > getColumn ( int i); // get column i ( starting at 0)
  List <Cell > getRow ( int j); // get row j ( starting at 0)

  void resize ( int cols , int rows ); // resize grid
  void clear (); // clear grid

  int getStepCount (); // get number of steps
}