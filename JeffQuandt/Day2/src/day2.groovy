// depending on whether the script is run from IDE or command line, the relative path may be different
def file

try {
  file = new File('../input/day2.txt')
  file.withReader { line = it.readLine() }
} catch( FileNotFoundException e ) {
  file = new File('../../input/day2.txt')
}

def totalSqFt = 0
def ribbonLength = 0
file.readLines().forEach({ line ->

    Gift gift = new Gift(line)
    totalSqFt += gift.findWrappingArea()
    ribbonLength += gift.ribbonCircumference() + gift.bowLength()
    println "$line ${gift.ribbonCircumference()} ${gift.bowLength()}"
})

println "Total Sq. Ft. $totalSqFt."
println "Total ribbon Ft. $ribbonLength."
