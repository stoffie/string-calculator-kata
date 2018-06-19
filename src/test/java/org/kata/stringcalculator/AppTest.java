package org.kata.stringcalculator;

import org.kata.stringcalculator.App;
import org.kata.stringcalculator.NegativesNotAllowedException;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AppTest
extends TestCase
{
	// I'm using a lambda function to recycle test scenarios
	public interface LambdaAddOperator {
		public int op(String numbers) throws NegativesNotAllowedException;
	}

	// test case initializers
	public AppTest( String testName ) {
		super( testName );
	}

	public static Test suite() {
		return new TestSuite( AppTest.class );
	}

	// test cases, from step 1 to step 6...

	public void testStep1() throws Exception
	{
		simplestThingScenario((a) -> App.addStep1(a));
	}

	public void testStep2() throws Exception
	{
		LambdaAddOperator operator = (a) -> App.addStep2(a);
		simplestThingScenario(operator);
		unknownAmountOfNumbersScenario(operator);
	}

	public void testStep3() throws Exception
	{
		LambdaAddOperator operator = (a) -> App.addStep3(a);
		simplestThingScenario(operator);
		unknownAmountOfNumbersScenario(operator);
		handleNewlineBetweenNumbersScenario(operator);
	}

	public void testStep4() throws Exception
	{
		LambdaAddOperator operator = (a) -> App.addStep4(a);
		simplestThingScenario(operator);
		unknownAmountOfNumbersScenario(operator);
		handleNewlineBetweenNumbersScenario(operator);
		supportDifferentDelimitersScenario(operator);
	}

	public void testStep5() throws Exception
	{
		LambdaAddOperator operator = (a) -> App.addStep5(a);
		simplestThingScenario(operator);
		unknownAmountOfNumbersScenario(operator);
		handleNewlineBetweenNumbersScenario(operator);
		supportDifferentDelimitersScenario(operator);
		negativeNumbersScenario(operator);
	}

	public void testStep6() throws Exception
	{
		LambdaAddOperator operator = (a) -> App.addStep6(a);
		simplestThingScenario(operator);
		unknownAmountOfNumbersScenario(operator);
		handleNewlineBetweenNumbersScenario(operator);
		supportDifferentDelimitersScenario(operator);
		negativeNumbersScenario(operator);
		ignoreBigNumbersScenario(operator);
	}

	// test helpers

	public void simplestThingScenario(LambdaAddOperator operator) throws Exception
	{
		assertEquals(0, operator.op(""));
		assertEquals(1, operator.op("1"));
		assertEquals(3, operator.op("1,2"));
		assertEquals(5, operator.op("2,3"));
	}

	public void unknownAmountOfNumbersScenario(LambdaAddOperator operator) throws Exception
	{
		assertEquals(0, operator.op("0,0,0,0,0,0,0,0,0,0,0,0"));
		assertEquals(1, operator.op("0,0,0,0,0,0,0,0,0,0,0,1"));
		assertEquals(5, operator.op("1,1,1,1,1,0,0,0,0,0,0,0"));
		assertEquals(111, operator.op("1,10,100,0"));
	}

	public void handleNewlineBetweenNumbersScenario(LambdaAddOperator operator) throws Exception
	{
		assertEquals(6, operator.op("1\n2,3"));
		assertEquals(6, operator.op("1\n2\n3"));
		assertEquals(10, operator.op("1\n2\n3,4"));
		// the following input is NOT ok: "1,\n" (not need to prove it - just clarifying)
	}

	public void supportDifferentDelimitersScenario(LambdaAddOperator operator) throws Exception
	{
		assertEquals(3, operator.op("//;\n1;2"));
		assertEquals(10, operator.op("//;|,\n1;2,3,4"));
		assertEquals(10, operator.op("//\\n\n1\n2\n3\n4"));
		assertEquals(10, operator.op("//\\n|;\n1;2\n3;4"));
	}

	public void negativeNumbersScenario(LambdaAddOperator operator)
	{
		try{
			operator.op("1,4,-1");
			fail("Exception not thrown");
		} catch (NegativesNotAllowedException e){
			assertEquals("negatives not allowed: -1", e.getMessage());
		}

		try{
			operator.op("-1,-2,-3");
			fail("Exception not thrown");
		} catch (NegativesNotAllowedException e){
			assertTrue(e.getMessage().startsWith("negatives not allowed:"));
			assertTrue(e.getMessage().contains("-1"));
			assertTrue(e.getMessage().contains("-2"));
			assertTrue(e.getMessage().contains("-3"));
		}
	}

	public void ignoreBigNumbersScenario(LambdaAddOperator operator) throws Exception {
		assertEquals(0, operator.op("1001"));
		assertEquals(1000, operator.op("1000"));
		assertEquals(3, operator.op("1,2,1001"));
	}
}
