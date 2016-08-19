// javazquez.com
// https://github.com/javazquez/

String fileText = new File('day3input.txt').text?.trim()
//use this to translate move string to coordinates
def moves =[  '>':[1, 0]  ,
              '<': [-1, 0],
              '^': [0, 1 ],
              'v': [0,-1 ]]
//subscript 0 and 1 represent x,y coords
def calculateHouses = { List coords ->
    List newCoords = [[0,0]]
    coords.each{ xyPair ->             
           newCoords << [ xyPair[0] + newCoords[-1][0] , xyPair[1] + newCoords[-1][1] ] //get last point in newCoords then pull out x and y
        } 
    return newCoords as Set
}
//split string up among santas and return all houses that got packages 
def calculateHousesForAllSantas = { int santaCnt, String rawMoves ->
    def santasMap = [:]
    santaCnt.times { santasMap["santa${it}"] = [] } //init all the santasMap to empty lists
    rawMoves.eachWithIndex { entry, idx -> 
        santasMap[ "santa${idx % santaCnt}"] << moves[entry] 
    }
    santasMap.each{ k,v -> santasMap[k] = calculateHouses(v) }
    santasMap.values().inject{ res, i -> res + i }.toSet().size()
}

// > delivers presents to 2 houses: one at the starting location, and one to the east.
assert calculateHousesForAllSantas(1,'>') == 2
// ^>v< delivers presents to 4 houses in a square, including twice to the house at his starting/ending location.
assert calculateHousesForAllSantas(1,'^>v<') == 4
// ^v^v^v^v^v delivers a bunch of presents to some very lucky children at only 2 houses.
assert calculateHousesForAllSantas(1,'^v^v^v^v^v') == 2
//part 1 at least one present
assert calculateHousesForAllSantas(1,fileText) == 2565
//part 2 robo santa 
assert calculateHousesForAllSantas(1,fileText)== 2565
assert calculateHousesForAllSantas(2, '^v') == 3
assert calculateHousesForAllSantas(2, '^>v<') == 3
assert calculateHousesForAllSantas(2, '^v^v^v^v^v') == 11
assert calculateHousesForAllSantas(2, fileText) == 2639

println "done "

