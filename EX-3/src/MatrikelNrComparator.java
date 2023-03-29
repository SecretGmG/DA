/**
 * File: MatrikelNrComparator.java
 *
 * Klasse zum Vergleichen zweier Objekte (Records) vom Typ StudentIn
 * bezueglich der Matrikelnummer.
 *
 */

public class MatrikelNrComparator implements java.util.Comparator<StudentIn> {

  /** Vergleicht Objekt a mit Objekt b und
   *  liefert -1 (wenn a<b), 0 (wenn a=b) oder +1 (wenn a>b)
   */
  public int compare(StudentIn a, StudentIn b) {
    return signum(a.getMatrikelNr() - b.getMatrikelNr());
  }

  /** Berechnet die Signum-Funktion einer ganzen Zahl */
  private static final int signum (long value) { 
    if (value > 0) return 1; 
    if (value < 0) return -1 ; 
    else return 0; 
  }

}
