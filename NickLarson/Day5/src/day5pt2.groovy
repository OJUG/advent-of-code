
def threeVowels = ~/(.*[aeiou].*){3,}/
def doubleLetter = ~/(.)\1/
def naughtyLetterGroups = ~/(ab)|(cd)|(pq)|(xy)/
def niceWords = 0

assert "aetrfi".find(threeVowels)
assert "abcc".find(doubleLetter)
assert !"accr".find(naughtyLetterGroups)?.size()
assert "abccr".find(naughtyLetterGroups).size()

new File('../input/day5.txt').eachLine{line ->
  if(line.find(threeVowels) && line.find(doubleLetter) && !line.find(naughtyLetterGroups) ) {    
    niceWords++
  }
}

println niceWords
