
def pattern1 = ~/(.{2}).*\1/
def pattern2 = ~/(.).\1/
def niceWords = 0

assert "qjhvhtzxzqqjkmpb".find(pattern1)
assert "qjhvhtzxzqqjkmpb".find(pattern2)
assert !"ieodomkazucvgmuy".find(pattern1)?.size()
assert !"uurcxstgmygtbstg".find(pattern2)?.size()

new File('../input/day5.txt').eachLine{line ->
  if(line.find(pattern1) && line.find(pattern2) ) {
    niceWords++
  }
}

println niceWords
