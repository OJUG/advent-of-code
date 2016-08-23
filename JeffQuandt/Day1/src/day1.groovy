def input = new File('../input/day1.txt').text
def part1 = input.count('(') - input.count(')')

def floor = 0
def x = 0
for (step in input) {
  step == '(' ? floor++ : floor--
  x++

  if (floor == -1) {
    break
  }
}

println "Ended up on floor $part1."
println "Found the basement on instruction $x"
