// javazquez.com
// https://github.com/javazquez/
List fileText = new File('day5input.txt').readLines()
def atleast3vowels = { String input -> input.findAll(/[aeiouAEIOU]/).size() >= 3}
def twiceInARow = { String input -> input.findAll(/(.)\1{1,}/)}

def isNiceString = { fText -> 
     atleast3vowels(fText) &&
     twiceInARow(fText) &&
     !(fText.findAll(/ab|cd|pq|xy/)) 
}
def isNiceString2 = { fText -> fText.findAll(/(.{2}).*\1/) && fText.findAll(/(.).\1/) }

//part 1 test cases
assert isNiceString('ugknbfddgicrmopn')
assert isNiceString('aaa')
assert !isNiceString('jchzalrnumimnmhp')
assert !isNiceString('haegwjzuvuyypxyu')
assert !isNiceString('dvszwmarrgswjxmb')
//solution part 1 
assert fileText.findAll{ isNiceString(it.trim()) }.size() == 255
//part 2 test cases
assert isNiceString2('qjhvhtzxzqqjkmpb')
assert isNiceString2('xxyxx')
assert !isNiceString2('uurcxstgmygtbstg')
assert !isNiceString2('ieodomkazucvgmuy')
//solution part 2
assert fileText.findAll{ isNiceString2(it.trim()) }.size() == 55

println 'done'
