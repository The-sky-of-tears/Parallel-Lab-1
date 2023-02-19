import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		double sumOfTime = 0;

		//the first iteration takes up to 10 times longer,
		// I suppose the reason is init of JVM, so I ignore it for more accurate testing
		new Runner().run(10000, 4);

		for (int i = 0; i < 10; i++) {
			double temp = new Runner().run(10_000, 256);
			sumOfTime += temp;
			System.out.println(temp);
		}

		System.out.println("Tested time: " + sumOfTime / 10);
	}
}
