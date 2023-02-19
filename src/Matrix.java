import java.util.ArrayList;
import java.util.Random;


public class Matrix {

	private final int rows;
	private final int columns;
	private final ArrayList<ArrayList<Integer>> matrix;

	public Matrix(int rows, int columns) {
		this.rows = rows;
		this.columns = columns;

		matrix = new ArrayList<>(rows);

		for(int i = 0; i < rows; i++) {
			matrix.add(new ArrayList<>(columns));
		}
	}

	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}

	public void fillRandom() {
		Random rand = new Random();
		rand.setSeed(/*System.currentTimeMillis()*/0);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				matrix.get(i).add(rand.nextInt(0, 10));
			}
		}
	}

	public void verticalReflect(int startRow, int finishRow) throws Exception{
		if (startRow < 0 || finishRow < 0 || startRow > rows || finishRow > rows) {
			throw new Exception("Invalid bounds, thread ID: " + Thread.currentThread().getId());
		}

		if (startRow > finishRow) {
			startRow += finishRow;
			finishRow = startRow - finishRow;
			startRow -= finishRow;
		}

		for (int i = startRow; i < finishRow; i++) {
			for (int j = columns / 2 - 1; j >= 0; j--) {
				matrix.get(i).set(j, matrix.get(i).get(columns - j - 1));
			}
		}
	}

	public void print() {
		for (var row : matrix) {
			for (var cell : row) {
				System.out.print(cell + " ");
			}
			System.out.println();
		}
	}
}
