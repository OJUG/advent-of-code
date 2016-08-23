package part2

def lightGrid = new LightGrid()
// depending on whether the script is run from IDE or command line, the relative path may be different
def file

try {
    file = new File('../input/day6.txt')
    file.withReader { line = it.readLine() }
} catch( FileNotFoundException e ) {
    file = new File('../../input/day6.txt')
}
file.eachLine { line ->
    lightGrid.processIntstruction(line)
}

def totalBrightness = 0
lightGrid.lights.each { List lightString ->
    lightString.each { light ->
        totalBrightness += light
    }
}

println "Total brightness on: $totalBrightness"