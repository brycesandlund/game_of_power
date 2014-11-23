package battlesim;

public class Combinatorics {

	public static double factorial( double n )
    {
        if( n <= 1 )     // base case
            return 1;
        else
            return n * factorial( n - 1 );
    }
	
	public static double combination(double n, double k)
	{
		if (k > n)
		{
			return 0;
		}
//		return factorial(n) / (factorial(n - k) * factorial(k));
		double result = 1;
		for (double i = n; i > n - k; --i)
		{
			result *= i;
		}
		return result / factorial(k);
	}
	
	public double binomial (int nTests, double probSuccess, int needed)
	{
		return Math.pow(probSuccess, needed) * Math.pow(1 - probSuccess, nTests - needed) * combination(needed, nTests);
	}
}
