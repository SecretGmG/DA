import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * File: SortTester.java
 *
 * Einfaches Testprogramm der Sortieralgorithmen.
 */

public class SortTester {

	public static void printArray(int[] array) {
		// Ausgabe des Arrays:
		for (int i = 0; i < array.length; i++)
			System.out.print(array[i] + " ");
		System.out.println();
	}

	public static void main(String[] args) {

		final int MIN_LENGTH = 100_000;
		final int MAX_LENGTH = 5_000_000;
		final int STEP_SIZE = 100_000;
		final int SAMPLE_SIZE = 5;

		for (int arrayLength = MIN_LENGTH; arrayLength < MAX_LENGTH; arrayLength += STEP_SIZE) {
			System.out.println(String.format("Input Length: %d", arrayLength));
			
			//System.out.println(String.format("InsertSort: %d ms", averageInsertSort(SAMPLE_SIZE, arrayLength)));
			System.out.println(String.format("MergeSort:  %d ms", averageMergeSort(SAMPLE_SIZE, arrayLength)));
			System.out.println(String.format("JavaSort:   %d ms", averageJavaSort(SAMPLE_SIZE, arrayLength)));

		}
	}
	public static long averageInsertSort(int sampleSize, int length) {
		Timer timer = new Timer();
		for (int i = 0; i < sampleSize; i++) {
			Sorting.insertionSort(getRandomIntArray(length));
		}
		return timer.timeElapsed() / sampleSize;
	}
	public static long averageMergeSort(int sampleSize, int length) {
		Timer timer = new Timer();
		for (int i = 0; i < sampleSize; i++) {
			MergeSort.mergeSort(getRandomIntArray(length));
		}
		return timer.timeElapsed() / sampleSize;
	}
	public static long averageJavaSort(int sampleSize, int length) {
		Timer timer = new Timer();
		for (int i = 0; i < sampleSize; i++) {
			java.util.Arrays.sort(getRandomIntArray(length));
		}
		return timer.timeElapsed() / sampleSize;
	}

	public static int[] getRandomIntArray(int length) {
		int[] array = new int[length];
		Random rand = new Random();
		for (int i = 0; i < length; i++) {
			array[i] = rand.nextInt();
		}
		return array;
	}
}
