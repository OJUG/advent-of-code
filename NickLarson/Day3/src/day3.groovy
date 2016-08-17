def input = new File('../input/day3.txt').text

def santa = new Santa()
Set<String> houses1 = ['0:0']
for (step in input) {
  houses1 << santa.move(step)
}

println "Day3 Part 1 - ${houses1.size()}"

def robotSanta = new Santa()
def realSanta = new Santa()

Set<String> houses2 = ['0:0']
input.eachWithIndex{val, index ->
  houses2 << (index % 2 ? robotSanta.move(val) : realSanta.move(val))
}

println "Day3 Part 2 - Houses Delivered To: ${houses2.size()}"
