
public class Fibonacci {
	public static void main(String args[]) {
		int n = 9;
		System.out.println(fib(n));
	}

	private static int fib(int n) {
		if (n < 2)
			return n;
		return fib(n - 1) + fib(n - 2);

	}
}
