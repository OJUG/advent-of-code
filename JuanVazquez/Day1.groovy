// javazquez.com
// https://github.com/javazquez/

String fileText = new File('day1input.txt').text?.trim()
Map ops = ['(': 1, ')': -1]

def findPositionOfBasement = {String floorText->
    int counter = 0
    floorText.collect{ops[it]}.takeWhile { (counter += it) != -1 }.size() + 1
}
def findPosition ={ String floorText ->
        floorText.collect{ops[it]}.sum()
}

//====== TESTS 
assert findPosition('()()') == 0
assert findPosition('(((') == 3 
assert findPosition('(()(()(') == 3
assert findPosition('))(((((') == 3
assert findPosition('())') == -1
assert findPosition('))(') == -1
assert findPosition(')))') == -3 
assert findPosition(')())())') == -3
//part 1
assert findPosition(fileText) == 280
//part 2
assert findPositionOfBasement(fileText) == 1797

println "no errors, worked correctly"