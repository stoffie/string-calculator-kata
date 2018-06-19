package org.kata.stringcalculator;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.kata.stringcalculator.NegativesNotAllowedException;

public class App
{
	public static int addStep1(String numbers) {
		if (numbers.length() == 0) {
			return 0;
		}

		List<String> stringList = Arrays.asList(numbers.split(","));
		// map each string to an int, then sum all the elements
		int sum = stringList.stream().mapToInt(Integer::parseInt).sum();
		return sum;
	}

	public static int addStep2(String numbers) {
		return addStep1(numbers);
	}

	public static int addStep3(String numbers) {
		if (numbers.length() == 0) {
			return 0;
		}
		// use `\n` and `,` as string separators
		List<String> stringList = Arrays.asList(numbers.split(",|\\n"));
		int sum = stringList.stream().mapToInt(Integer::parseInt).sum();
		return sum;
	}

	static class DelimiterNumbersPair {
		public String delimiter;
		public String numbers;
	}

	private static DelimiterNumbersPair separateDelimiterAndNumbers(String numbers) {
		// default split pattern
		String splitPattern = ",|\\n";
		// Pattern used to check for a delimiter at the start of the string
		// Quick regex reference:
		// \D -> A non-digit: [^0-9]
		// \w -> A word character: [a-zA-Z_0-9]
		// \W -> A non-word character: [^\w]
		// X+? -> X, one or more times (Reluctant quantifiers)
		String delimiterPattern = "^//([\\D]+?)\\n([\\w\\W]*)$";
		Pattern r = Pattern.compile(delimiterPattern);
		Matcher m = r.matcher(numbers);
		// if a match is found, update splitPattern and numbers list
		if (m.find()) {
			splitPattern = m.group(1);
			numbers = m.group(2);
		}
		DelimiterNumbersPair pair = new DelimiterNumbersPair();
		pair.delimiter = splitPattern;
		pair.numbers = numbers;
		return pair;
	}

	public static int addStep4(String numbers) {
		if (numbers.length() == 0) {
			return 0;
		}
		DelimiterNumbersPair pair = separateDelimiterAndNumbers(numbers);
		return addWithDelimiterStep4(pair.delimiter, pair.numbers);
	}

	private static int addWithDelimiterStep4(String delimiter, String numbers) {
		List<String> stringList = Arrays.asList(numbers.split(delimiter));
		int sum = stringList.stream().mapToInt(Integer::parseInt).sum();
		return sum;
	}

	public static int addStep5(String numbers)
		throws NegativesNotAllowedException
	{
		if (numbers.length() == 0) {
			return 0;
		}
		DelimiterNumbersPair pair = separateDelimiterAndNumbers(numbers);
		return addWithDelimiterStep5(pair.delimiter, pair.numbers);
	}

	private static int addWithDelimiterStep5(String delimiter, String numbers)
		throws NegativesNotAllowedException
	{
		List<String> stringList = Arrays.asList(numbers.split(delimiter));
		List<Integer> negativeList = new ArrayList<>();
		int sum = 0;
		for (String e : stringList) {
			int i = Integer.parseInt(e);
			if (i < 0) {
				negativeList.add(i);
			}
			sum += i;
		}

		if (!negativeList.isEmpty()) {
			throw new NegativesNotAllowedException(negativeList);
		}
		return sum;
	}

	public static int addStep6(String numbers)
		throws NegativesNotAllowedException
	{
		if (numbers.length() == 0) {
			return 0;
		}
		DelimiterNumbersPair pair = separateDelimiterAndNumbers(numbers);
		return addWithDelimiterStep6(pair.delimiter, pair.numbers);
	}

	private static int addWithDelimiterStep6(String delimiter, String numbers)
		throws NegativesNotAllowedException
	{
		List<String> stringList = Arrays.asList(numbers.split(delimiter));
		List<Integer> negativeList = new ArrayList<>();
		int sum = 0;
		for (String e : stringList) {
			int i = Integer.parseInt(e);
			if (i > 1000) {
				// skip all numbers bigger than 1000
				continue;
			}
			if (i < 0) {
				negativeList.add(i);
			}
			sum += i;
		}

		if (!negativeList.isEmpty()) {
			throw new NegativesNotAllowedException(negativeList);
		}

		return sum;
	}
}
