import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		final int squareMatrixSize = 100;
		final int numOfThreads = 1;

		Matrix matrix = new Matrix(squareMatrixSize, squareMatrixSize);
		matrix.fillRandom();

		ArrayList<Thread> threadArr = new ArrayList<>(numOfThreads);

		final int numOfRowsForThread;
		int rest;
		if (matrix.getRows() < numOfThreads) {
			numOfRowsForThread = 1;
			rest = 0;
		} else {
			numOfRowsForThread = matrix.getRows() / numOfThreads;
			rest = matrix.getRows() % numOfThreads;
		}

		long start = System.nanoTime(); // start timer
		for (int i = 0, shift = 0; i < numOfThreads; i++) {
			final int startRow;
			final int finishRow;

			if (rest-- > 0) {
				startRow = i * numOfRowsForThread + shift;
				shift++;
				finishRow = (i + 1) * numOfRowsForThread + shift;
			} else {
				startRow = i * numOfRowsForThread + shift;
				finishRow = (i + 1) * numOfRowsForThread + shift;
			}

			threadArr.add(new Thread(() -> {
				try {
					matrix.verticalReflect(startRow, finishRow);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}));
			threadArr.get(i).start();
		}

		try {
			for (Thread thread : threadArr) {
				thread.join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long finish = System.nanoTime(); //stop timer

		System.out.println("Required time (ms): " + (double)(finish - start) / 1_000_000);

		/*matrix.print();*/
	}
}