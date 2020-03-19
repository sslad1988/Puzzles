
public class PrimeNumber {

	public static void main(String[] args) {

		printPrim(9);

	}

	private static void printPrim(int n) {
		for (int i = 0; i < n; i++) {

			if (isPrime(i)) {
				System.out.println(i);
			}
		}

	}

	private static boolean isPrime(int n) {
		for (int i = 2; i < n; i++) {
			if (n % i == 0)
				return false;

		}
		return true;
	}

}
