/**
 * @author Mr. Rasmussen
 */

package fracCalc;

import java.util.Scanner;

public class FracCalc {

	public static void main(String[] args) {
		// TODO: Read the input from the user and call produceAnswer with an equation
		Scanner s = new Scanner(System.in);

		System.out.println("FRACION CALCULATOR");
		System.out.println("------------------");
		System.out.println("Type QUIT to stop\n");

		String input = "";

		while (!input.equals("QUIT")) {
			System.out.print("INPUT: ");
			input = s.nextLine();
//			System.out.println("OUTPUT: ");
//			System.out.println("------------------");
			System.out.println(produceAnswer(input));
		}
	}

	// ** IMPORTANT ** DO NOT DELETE THIS FUNCTION. This function will be used to
	// test your code
	// This function takes a String 'input' and produces the result
	//
	// input is a fraction string that needs to be evaluated. For your program, this
	// will be the user input.
	// e.g. input ==> "1/2 + 3/4"
	//
	// The function should return the result of the fraction after it has been
	// calculated
	// e.g. return ==> "1_1/4"
	public static String produceAnswer(String input) {
		// TODO: Implement this function to produce the solution to the input
		int begin = 0;
		String fraction1 = "";
		String op = "";
		String fraction2 = "";
		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c == ' ' && fraction1 == "") {
				fraction1 = input.substring(begin, i);
				begin = i + 1;
			} else if ((c == '+' || c == '-' || c == '/' || c == '*') && input.charAt(i + 1) == ' ' && op == "") {
				op = "" + c;
				begin = i + 2;
//				i += 2;
//			} else if (c == ' ' && fraction2 == "") {
//				fraction2 = input.substring(begin, i);
			} else if (i == input.length() - 1 || c == ' ' && fraction2 == "") {
				fraction2 = input.substring(begin);
			}
		}

//		return fraction2;
//		int w = getWhole(fraction2);
//		int n = getNumerator(fraction2);
//		int d = getDenomenator(fraction2);
//		return "whole:" + w  + " numerator:" + n  + " denominator:" + d;
		fraction1 = reduceFraction(fraction1);
		fraction2 = reduceFraction(fraction2);

		fraction1 = toImproper(fraction1);
		fraction2 = toImproper(fraction2);

		String answer = preformOperation(fraction1, fraction2, op);

		if (Math.signum(getWhole(answer)) == -1) {
			return toString(getWhole(answer), Math.abs(getNumerator(answer)), getDenomenator(answer));
		}
		return answer;
	}

	public static String toString(int w, int n, int d) {
		if (w == 0) {
			if (n == 0) {
				return w + "";
			}
			return n + "/" + d;
		} else if (n == 0) {
			return w + "";
		}
//		else if(w < 0) {
//			return w + "_" + Math.abs(n) + "/" + d;
//		}
		return w + "_" + n + "/" + d;
	}

	public static String toImproper(String fraction) {
		int w1 = getWhole(fraction);
		int n1 = getNumerator(fraction);
		int d1 = getDenomenator(fraction);
		if (w1 < 0 && n1 > 0)
			n1 *= Math.signum(w1);
		n1 += w1 * d1;
		return toString(0, n1, d1);
	}

	public static String preformOperation(String fraction1, String fraction2, String op) {
		if (op.equals("+"))
			return add(fraction1, fraction2);
		else if (op.equals("-"))
			return sub(fraction1, fraction2);
		else if (op.equals("*"))
			return mult(fraction1, fraction2);
		else if (op.equals("/"))
			return div(fraction1, fraction2);
		return fraction1;
	}

	public static String add(String fraction, String fraction2) {
		int w1 = getWhole(fraction);
		int n1 = getNumerator(fraction);
		int d1 = getDenomenator(fraction);
		int w2 = getWhole(fraction2);
		int n2 = getNumerator(fraction2);
		int d2 = getDenomenator(fraction2);

		int frac1Mult = d2;
		int frac2Mult = d1;

		n1 *= frac1Mult;
		d1 *= frac1Mult;
		n2 *= frac2Mult;
		d2 *= frac2Mult;
		d1 *= d2;

		n1 += n2;
		return reduceFraction(w1, n1, d2);
	}

	public static String sub(String fraction, String fraction2) {
		int w1 = getWhole(fraction);
		int n1 = getNumerator(fraction);
		int d1 = getDenomenator(fraction);
		int w2 = getWhole(fraction2);
		int n2 = getNumerator(fraction2);
		int d2 = getDenomenator(fraction2);

		int frac1Mult = d2;
		int frac2Mult = d1;

		n1 *= frac1Mult;
		d1 *= frac1Mult;
		n2 *= frac2Mult;
		d2 *= frac2Mult;

		n1 -= n2;

		return reduceFraction(w1, n1, d1);
	}

	public static String mult(String fraction, String fraction2) {
		int w1 = getWhole(fraction);
		int n1 = getNumerator(fraction);
		int d1 = getDenomenator(fraction);
		int w2 = getWhole(fraction2);
		int n2 = getNumerator(fraction2);
		int d2 = getDenomenator(fraction2);

		n1 *= n2;
		d1 *= d2;
		return reduceFraction(w1, n1, d1);
	}

	public static String div(String fraction, String fraction2) {
		int w1 = getWhole(fraction);
		int n1 = getNumerator(fraction);
		int d1 = getDenomenator(fraction);
		int w2 = getWhole(fraction2);
		int n2 = getNumerator(fraction2);
		int d2 = getDenomenator(fraction2);
		n1 *= d2;
		d1 *= n2;
		return reduceFraction(w1, n1, d1);
	}

	public static int getWhole(String f) {
		boolean den = false;

		for (int i = 0; i < f.length(); i++) {
			char c = f.charAt(i);
			if (c == '/')
				den = true;
			if (c == '_')
				return Integer.parseInt(f.substring(0, i));
			else if (i == f.length() - 1 && !den)
				return Integer.parseInt(f.substring(0));
		}
		return 0;
	}

	public static int getNumerator(String f) {
		int begin = 0;
		for (int i = 0; i < f.length(); i++) {
			char c = f.charAt(i);
			if (c == '_')
				begin = i + 1;
			if (c == '/')
				return Integer.parseInt(f.substring(begin, i));
		}
		return 0;
	}

	public static int getDenomenator(String f) {
		int begin = 0;
		for (int i = 0; i < f.length(); i++) {
			char c = f.charAt(i);
			if (c == '/')
				begin = i + 1;
			if (i == f.length() - 1 && begin > 0) {
				return Integer.parseInt(f.substring(begin, f.length()));
			}
		}
		return 1;
	}

	public static String reduceFraction(String fraction) {
		int w = getWhole(fraction);
		int n = getNumerator(fraction);
		int d = getDenomenator(fraction);

		return reduceFraction(w, n, d);

	}

	public static String reduceFraction(int w, int n, int d) {
		if (Math.signum(w) != 0)
			w += Math.signum(w) * (n / d);
		else
			w += (n / d);
		n %= d;
		for (int f = 2; f <= d / 2;) {
			if (d % f == 0 && n % f == 0) {
				n /= f;
				d /= f;
			} else
				f++;
		}
		if (Math.signum(n) == -1 && Math.signum(d) == -1)
			return toString(w, Math.abs(n), Math.abs(d));
		else if (Math.signum(d) == -1) {
			return toString(w, n * -1, Math.abs(d));
		}
		return toString(w, n, d);

	}

	// TODO: Fill in the space below with any helper methods that you think you will
	// need
}
