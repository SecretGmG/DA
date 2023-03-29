/**
 * File: SortTester.java
 *
 * Einfaches Testprogramm der Sortieralgorithmen.
 */

import java.io.Console;
import java.util.ArrayList;

public class SortTestApp {

	// Fuer diese Anzahl Records wird der Test durchgefuehrt:
	public static final int[] NUM_OF_RECORDS = { 100, 200, 400, 800, 1600, 3200, 
		6400, 12800, 25600, 51200, 102400, 204800, 409600, 809600, 1619200};
  
	public static void main(String[] args) {

		Timer timer = new Timer();

		DataGenerator generator = new DataGenerator();

		for (int i=0; i<NUM_OF_RECORDS.length; i++) {
      
			int numOfRecords = NUM_OF_RECORDS[i];
			System.out.println("\nAnzahl zu sortierende Records: " + numOfRecords);

			// zu sortierenden Array erstellen:
			generator.generateNewData(numOfRecords);

			// Comparator erstellen:
			java.util.Comparator<StudentIn> comp = new MatrikelNrComparator();

			// *** QuickSort:
			// zu sortierenden Array erstellen (Kopie der urspruenglichen Daten):
			ArrayList<StudentIn> array = generator.getCopyOfData();

			// Den Array sortieren und Zeit nehmen:
			timer.reset();
			QuickSort.quickSort(array, 0, array.size()-1, comp);
			long sortDuration = timer.timeElapsed();

			System.out.print("   Erster QuickSort: " + sortDuration + " ms");

			// Den Array nochmals sortieren und Zeit nehmen:
			timer.reset();
			QuickSort.quickSort(array, 0, array.size()-1, comp);
			sortDuration = timer.timeElapsed();

			System.out.print("   QuickSort auf den bereits sortierten Daten: " + sortDuration + " ms");


			if(!isSorted(comp, array)){
				System.out.println("Das Array wurde nicht korrekt sortiert");
			}
		}
	}

	/**
	 * checks if an ArrayList is Sorted
	 * @param comp the comparator by which the list should be sorted
	 * @param array the array to check
	 * @return true if the array is sorted
	 * @param <T> the type of the array
	 */
	public static <T> boolean isSorted(java.util.Comparator<T> comp, ArrayList<T> array){
		for(int i = 0; i<array.size()-1; i++){
			//if the next element is smaller than the current, return true
			if(comp.compare(array.get(i), array.get(i+1)) > 0){
				return false;
			}
		}
		return true;
	}
}

