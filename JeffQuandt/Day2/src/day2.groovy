// depending on whether the script is run from IDE or command line, the relative path may be different
def file

try {
  file = new File('../input/day2.txt')
  file.withReader { line = it.readLine() }
} catch( FileNotFoundException e ) {
  file = new File('../../input/day2.txt')
}

def totalSqFt = 0
file.readLines().forEach({ line ->
    totalSqFt += new Gift(line).findWrappingArea()
})

println "Total Sq. Ft. $totalSqFt."
