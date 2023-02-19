import java.util.ArrayList;

public class Runner {
	public double run(final int squareMatrixSize, int numOfThreads) {
		Matrix matrix = new Matrix(squareMatrixSize, squareMatrixSize);
		matrix.fillRandom();

		/*		matrix.print();*/

		ArrayList<Thread> threadArr = new ArrayList<>(numOfThreads);

		final int numOfRowsForThread;
		int rest;
		if (matrix.getRows() < numOfThreads) {
			numOfThreads = matrix.getRows();
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
					e.printStackTrace();
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

		double requiredTime = (double)(finish - start) / 1_000_000;

	/*	System.out.println("Required time (ms): " + requiredTime);

		matrix.print();*/

		return requiredTime;
	}
}
