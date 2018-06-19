package org.kata.stringcalculator;

import java.util.List;
import java.util.stream.Collectors;

public class NegativesNotAllowedException extends Exception {
	public List<Integer> negativeList;

	public NegativesNotAllowedException(List<Integer> negativeList) {
		this.negativeList = negativeList;
	}

	@Override
	public String getMessage() {
		List<String> stringList = negativeList.stream().map(Object::toString).collect(Collectors.toList());
		String negatives = String.join(",", stringList);
		return "negatives not allowed: " + negatives;
	}
}
