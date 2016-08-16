package adventInAugust;

import java.util.HashSet;
import java.util.Set;

public class Day3 {
	
	public static void main(String[] args){
		String input = args[0];
		int curX = 0;
		int curY = 0;
		Set<Coordinate> visitedSpotsYear1 = new HashSet<>();
		visitedSpotsYear1.add(new Coordinate(curX, curY));
		for(int index = 0; index < input.toCharArray().length; index++){
			char direction = input.toCharArray()[index];
			if(direction == '>'){
				curX++;
			}else if(direction == '<'){
				curX--;
			}else if(direction == '^'){
				curY++;
			}else if(direction == 'v'){
				curY--;
			}
			visitedSpotsYear1.add(new Coordinate(curX, curY));
		}
		System.out.println("Santa from year 1 visited " + visitedSpotsYear1.size() + " houses");
	
		int curXSanta = 0;
		int curYSanta = 0;
		int curXRobo = 0;
		int curYRobo = 0;
		Set<Coordinate> visitedSpotsYear2 = new HashSet<>();
		for(int index = 0; index < input.toCharArray().length; index++){
			char direction = input.toCharArray()[index];
			if(index % 2 == 0){
				if(direction == '>'){
					curXSanta++;
				}else if(direction == '<'){
					curXSanta--;
				}else if(direction == '^'){
					curYSanta++;
				}else if(direction == 'v'){
					curYSanta--;
				}
				visitedSpotsYear2.add(new Coordinate(curXSanta, curYSanta));
			}
			else{
				if(direction == '>'){
					curXRobo++;
				}else if(direction == '<'){
					curXRobo--;
				}else if(direction == '^'){
					curYRobo++;
				}else if(direction == 'v'){
					curYRobo--;
				}
				visitedSpotsYear2.add(new Coordinate(curXRobo, curYRobo));
			}
		}
		System.out.println("Santa + Robo Santa from year 2 visited " + visitedSpotsYear2.size() + " houses.");
	
	}

}
