package adventInAugust;

public class Day5 {
	
	public static void main(String[] words){
		int niceCount = 0;
		int niceCountPart2 = 0;
		for(String word : words){
			if(isNice(word)){
				niceCount++;
			}
			if(isNice2(word)){
				niceCountPart2++;
			}
		}
		System.out.println(niceCount + " words are nice");
		System.out.println(niceCountPart2 + " words are nice for part 2");
	}
	
	public static boolean isNice(String str){
		return str.matches(".*(\\w)\\1.*") 
				&& !str.matches(".*ab.*")
				&& !str.matches(".*cd.*")
				&& !str.matches(".*pq.*")
				&& !str.matches(".*xy.*")
				&& str.matches("(.*[aeiou].*){3,}");
	}

	public static boolean isNice2(String str) {
		return str.matches(".*(\\w).\\1.*") &&
				str.matches(".*(\\w)(\\w).*(\\1)(\\2).*"); 
	}

}
