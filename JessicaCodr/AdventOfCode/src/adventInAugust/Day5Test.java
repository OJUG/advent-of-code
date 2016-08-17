package adventInAugust;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Day5Test {
	
	@Test
	public void longerNiceWord(){
		assertTrue(Day5.isNice("ugknbfddgicrmopn"));
	}
	
	@Test
	public void shortNiceWord(){
		assertTrue(Day5.isNice("aaa"));
	}
	
	@Test
	public void naughtyDueToNoDoubleLetter(){
		assertFalse(Day5.isNice("jchzalrnumimnmhp"));
	}
	
	@Test
	public void naughtyDueToContainingForbiddenLetters(){
		assertFalse(Day5.isNice("haegwjzuvuyypxyu"));
		// TODO test for other forbidden letter pairs
	}
	
	@Test
	public void naughtyDueToNotEnoughVowels(){
		assertFalse(Day5.isNice("dvszwmarrgswjxmb"));
	}
	
	@Test
	public void naughtyDueToStillNotEnoughVowels(){
		assertFalse(Day5.isNice("dvszwmaarrgswjxmb"));
	}
	
	@Test
	public void niceWordForPart2(){
		assertTrue(Day5.isNice2("qjhvhtzxzqqjkmpb"));
	}
	
	@Test
	public void shorterNiceWordForPart2(){
		assertTrue(Day5.isNice2("xxyxx"));
	}
	
	@Test
	public void naughtyDueToNoSingleLetterRepeatingWithExactlyOneLetterBetweenPart2(){
		assertFalse(Day5.isNice2("uurcxstgmygtbstg"));
	}
	
	@Test
	public void naughtyDueToNoRepeatingPairOfLettersWithoutOverlappingPart2(){
		assertFalse(Day5.isNice2("ieodomkazucvgmuy"));
	}
	
}
