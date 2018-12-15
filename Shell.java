import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
* Shell implementation Langton Ant.
*
* @version 1.0
*/

public final class Shell {
  private static final int WIDTH = 80;
  private static final int HEIGHT = 80;
  private static final int MINSTEPS = -99999;
  private static final int MAXSTEPS = 99999;
  private static boolean quit = false;
  private static boolean cmdnew = false;
  private static AntGrid antGrid;

  /**
   * Get the value of antGrid.
   * 
   * @return the value of antGrid
   */
  public static AntGrid getAntGrid() {
    return antGrid;
  }

  /**
   * Set the value of antGrid.
   *
   * @param antGrid new value of antGrid
   */
  public void setAntGrid(AntGrid antGrid) {
    Shell.antGrid = antGrid;
  }

  /**
   * The newGame method All fields on the field are in their initial white state, there is no ant.
   * 
   * @param scanner
   */
  private static void newGame(Scanner scanner) {
    Integer x = rdInt(scanner);
    if (x == null) {
      return;
    }
    if (x < 1 || x > WIDTH) {
      error("The value: " + x +" muss be 1<=" + x + "<=" + WIDTH);
      return;
    }
    Integer y = rdInt(scanner);
    if (y == null) {
      return;
    }
    if (y < 1 || y > HEIGHT) {
      error("The value: " + y +" muss be 1<=" + x + "<=" +  HEIGHT);
      return;
    }
    String rules = rdStates(scanner);
      if (rules == null) {
        error("Entry step's rule");
        return;
    }
    for( char c : rules.toCharArray()) {
      if ( !(c == 'R' || c == 'L') ) {
        error("in step's rule only 'R' and 'L' are permited. Not '"+c+"'");
        return;
      }
    }
    // All fields on the field are in their initial state (white), there is no ant.
    antGrid =  new AntGrid(x, y, rules);
  }

  /**
   * The placeAnt method place the Ant in i-th column and j-th row.
   * Only one ant may exist.
   * 
   * @param scanner
   */
  private static void placeAnt(Scanner scanner) {
    Integer ii = rdInt(scanner);
    if (ii == null) {
      return;
    }
    if (ii < 0 || ii > (getAntGrid().getX()-1)) {
      error("The value: " + ii +" muss be 0<=" + ii + "<=" + (getAntGrid().getX()-1));
      return;
    }
    Integer jj = rdInt(scanner);
    if (jj == null) {
      return;
    }
    if (jj < 0 || jj > (getAntGrid().getY()-1)) {
      error("The value: " + jj +" muss be 0<=" + jj + "<=" + (getAntGrid().getY()-1));
      return;
    }
    // Place ant in i-th column and j-th row.
    // Only one ant may exist. If an ant already exists, an error message will be displayed.
    getAntGrid().setAnt(new Ant(getAntGrid().getCells()[ii][jj], getAntGrid(). getRule(), getAntGrid().getX(),getAntGrid().getY() ) , ii, jj);
  }

  /**
   * The removeAnt method removes the ant.
   * If no ant exists, an error message will be displayed.Field remains.
   * 
   * @param scanner
   */
  private static void removeAnt(Scanner scanner) {
  if (scanner.hasNextInt()) {
    error("Does not have the correct input.");
    return;
  }
    if ( getAntGrid().getAnts().values().isEmpty() ) {
    error("NO ant exists!");
    return;
    }
    getAntGrid().clearAnts();
  }

  /**
   * The computeoutnextStep method Computes the next step and outputs the (continuous) number.
   * of steps after the calculation, or undoes steps.
   * The optional argument[n] specifies the number of steps to go at once.
   * 
   * @param scanner
   */
  private static void computeoutnextStep(Scanner scanner) {
    Integer nn = 0;
    if (scanner.hasNextInt()) {
      nn = rdInt(scanner);
    } else {
      nn = 1;  // "STEP" a step equals to "STEP 1"
    }
    if (nn < MINSTEPS || nn > MAXSTEPS) {
      error("The value: " + nn +" muss be " + MINSTEPS + "<=" + nn + "<=" + MAXSTEPS);
      return;
    }
    // Computes the next step and outputs the (continuous) number of steps after the calculation,
    // or undoes steps.
    getAntGrid().performStep(nn);
    final int totalStepCount = AntGrid.totalStepCount;
    System.out.println(totalStepCount);
  }

  /**
   * The playsGame method Play the field as a rectangular matrix.
   * White fields with '0' .The following colors with next digit number
   * 
   * @param scanner
   */
  private static void playsGame(Scanner scanner) {
    if (scanner.hasNextInt()) {
      error("Does not have the correct input.");
      return;
    }
    System.out.println(getAntGrid().PrintANSIescapes());
  }

  /**
   * The cleansuplayField method cleans up the entire playing field.
   * Set all fields to initial state, remove Ant and reset step count.
   * 
   * @param scanner
   */
  private static void cleansuplayField(Scanner scanner) {
    if (scanner.hasNextInt()) {
      error("Does not have the correct input.");
      return;
    }
    // Cleans up the entire playing field.
    // Set all fields to initial state, remove Ant and reset step count.
    getAntGrid().clearPlayField();
  }

  /**
   * The resizeplayField method Changes the size of the playing field(x,y).
   * Fields that remain on the new board remain the same.
   * Newly added fields are in the initial state.
   * If the ant is outside the field, it will be deleted.
   * The field should be enlarged / reduced symmetrically.
   * If the difference is odd, then first zoom in/out on the right and bottom.
   * 
   * @param scanner
   */
  private static void resizeplayField(Scanner scanner) {
    Integer xx = rdInt(scanner);
    if (xx == null) {
      return;
    }
    if (xx < 1 || xx > WIDTH) {
      error("The value: " + xx +" muss be 1<=" + xx + "<=" + WIDTH);
      return;
    }
    Integer yy = rdInt(scanner);
    if (yy == null) {
      return;
    }
    if (yy < 1 || yy > HEIGHT) {
      error("The value: " + yy +" muss be 1<=" + yy + "<=" + HEIGHT);
      return;
    }
    // RESIZE x y "Changes the size of the playing field (x,y)"
    getAntGrid().resize(xx, yy);
  }

  /**
   * The error() method displays an error when there is one.
   *
   * @param message Print Error! message on standard output.
   */
  public static void error(String message) {
    System.out.println("Error! " + message);
  }

  /**
   * The help() method displays a series of messages with a description of each method.
   * 
   */
  private static void help() {
    System.out.println("help menu:\n");
    System.out.println("commands:");
    System.out.println("NEW x y c\n\tStarts a new game with size (x,y)and configuration c");
    System.out.println("ANT i j\n\tPlace ant in i-th column and j-th row. Only one ant may exist.");
    System.out.println("UNANT\n\tRemoves the ant. If no ant exists, an error message will be "
        + "displayed.");
    System.out.println("STEP [n]\n\tComputes the next step and outputs the (continuous) number of "
        + "steps after the calculation, or undoes steps.");
    System.out.println("PRINT\n\tPlays game field.");
    System.out.println("CLEAR\n\tCleans up the entire playing field.");
    System.out.println("RESIZE x y\n\tChanges the size of the playing field (x,y). ");
    System.out.println("HELP\n\tShow this help command.");
    System.out.println("QUIT\n\tEnds the program.\n");
    System.out.println("args:");
    System.out.println("x y\n\tpositive integer.");
    System.out.println("c\n\tconfiguration c written in upper case.");
    System.out.println("i j\n\tnon-negative integer.");
    System.out.println("[n]\n\tinteger.\n");
  }

  /**
   * The rdStates() method reads the configuration commands.
   * which must be at least 2, and may consist of a maximum of 12 states
   * in the RL syntax and with capitalization only
   * 
   * @param scanner
   * @return the String of rules RL...
   */
  private static String rdStates(Scanner scanner) {
    String cmdStates;
    if (scanner.hasNext()) {
      cmdStates = scanner.next();
    } else {
      error("Not a recognised command.");
      return null;
    }
    // cmdStates: configuration must be >= 2, and may consist of a max. of 12 states (RL: 2 states).
    char[] nameArray = cmdStates.toCharArray();
    boolean consist = true;
    for (char ch : nameArray) {
      if (Character.getType(ch) != Character.UPPERCASE_LETTER) {
        consist = false;
      }
    }
    if (!consist) {
      error(cmdStates + " does not have correct input.");
      return null;
    }
    Integer lngcmdStates = cmdStates.length();
    if (lngcmdStates < 2 || lngcmdStates > 12) {
      error(cmdStates + " must be at least 2, and may consist of a maximum of 12 states.");
      return null;
    }

    return cmdStates;
  }

  /**
   * The rdInt() method reads and checks for the correct input.
   * 
   * @param scanner
   * @return the Integer of digit(s)
   */
  private static Integer rdInt(Scanner scanner) {
    int dgt;
    if (scanner.hasNextInt()) {
      dgt = scanner.nextInt();
    } else {
      error("Does not have the correct input.");
      return null;
    }
    /*
    if (dgt < 0) {
      error(dgt + " should be greater than 0");
      return null;
    }
    */
    return dgt;
  }

  /**
   * The utility class.
   */
  private Shell() {
  }

  /**
   * The main method is where the Langton ant app actually starts.
   *
   * @param args is an array containing the command line arguments
   * @throws IOException is thrown if the files cannot be created
   */
  public static void main(String[] args) throws IOException {
    BufferedReader l = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
    //the program keeps printing the same message over and over again unless we want to quit Trie
    while (!quit) {
      System.out.print("ant> ");
      String input = l.readLine();
      //when there is no more input
      if (input == null) {
        break;
      }
      Scanner scanner = new Scanner(input);
      // initialize the string delimiter. split input on white spaces. \\s+ means 1 or more
      // repetitions of any whitespace character
      scanner.useDelimiter("\\s+");
      String next;
      if (scanner.hasNext()) {
        next = scanner.next();
      } else {
        // Error. No command in the shell.
        error("Not a recognised command. Type 'help' to find out more.");
        scanner.close();
        return;
      }

      // get the first letter from command by using scanner.next()
      char firstLetter = Character.toLowerCase(next.charAt(0));
      switch (firstLetter) {
        case 'n':
          // NEW x y c "Starts a new game with size (x,y)and configuration c"
          newGame(scanner);
          cmdnew = true;
          break;

        case 'a':
          // ANT i j "Place ant in i-th column and j-th row. Only one ant may exist."
          if (cmdnew) {
            placeAnt(scanner);
          }
          else {
            error("Use NEW x y c in order to start a new game.");
          }
          break;

        case 'u':
          // UNANT "Removes the ant. If no ant exists, an error message will be displayed."
          if (cmdnew) {
            removeAnt(scanner);
          }
          else {
            error("Use NEW x y c in order to start a new game.");
          }
          break;

        case 's':
          // STEP [n] "Computes the next step and outputs the (continuous) number of steps
          // after the calculation, or undoes steps."
          computeoutnextStep(scanner);
          break;

        case 'p':
          // PRINT "Plays game field."
          if (cmdnew) {
            playsGame(scanner);
          }
          else {
            error("Use NEW x y c in order to start a new game.");
          }
          break;

        case 'c':
          // CLEAR "Cleans up the entire playing field."
          if (cmdnew) {
            cleansuplayField(scanner);
          }
          else {
            error("Use NEW x y c in order to start a new game.");
          }
          break;

        case 'r':
          // RESIZE x y "Changes the size of the playing field (x,y)"
          if (cmdnew) {
            resizeplayField(scanner);
          }
          else {
            error("Use NEW x y c in order to start a new game.");
          }
          break;

        case 'd':
          // debug - print playing field without ANSI 
          if (scanner.hasNextInt()) {
            error("Does not have the correct input.");
          } else {
            System.out.println(getAntGrid().Print());
          }
          break;

        case 'h':
          // HELP "Show this help command."
          help();
          break;

        case 'q':
          // QUIT "Ends the program."
          quit = true;
          break;

        default:
          error("Not a recognised command. Type 'help' to find out more.");
      }
      scanner.close();
    }
  }
}