package part2
/**
 * Created by req89790 on 08/17/2016.
 */
class LightGrid {

    List lights = []

    def togglePattern = ~/toggle/
    def lightOnPattern  = ~/on/
    def lightOffPattern  = ~/off/
    def lightGroupPattern = ~/([0-9]+?),([0-9]+?) .* ([0-9]+?),([0-9]+)/

    LightGrid() {
        1000.times{ x ->
            lights << []
            1000.times{ y ->
                lights[x] << 0L
            }
        }
    }

    def processIntstruction(String instruction) {

        instruction.eachMatch(lightGroupPattern) {match, startX, startY, endX, endY ->
            if (instruction.find(togglePattern)) {
                toggle(startX.toInteger(), startY.toInteger(), endX.toInteger(), endY.toInteger())
            } else if (instruction.find(lightOnPattern)) {
                lightOn(startX.toInteger(), startY.toInteger(), endX.toInteger(), endY.toInteger())
            } else if (instruction.find(lightOffPattern)) {
                lightOff(startX.toInteger(), startY.toInteger(), endX.toInteger(), endY.toInteger())
            }
        }
    }

    def toggle(startX, startY, endX, endY) {

        looper((startX..endX), (startY..endY)) { x, y ->

            lights[x][y] += 2
        }

    }

    def lightOn(startX, startY, endX, endY) {
        looper((startX..endX), (startY..endY)) { x, y ->
            lights[x][y] += 1
        }
    }

    def lightOff(startX, startY, endX, endY) {
        looper((startX..endX), (startY..endY)) { x, y ->
            lights[x][y] = Math.max(0, (lights[x][y]-1))

        }
    }

    def looper(rangeX, rangeY, Closure value) {

        rangeX.each { x ->
            rangeY.each { y ->
                value(x, y)
            }
        }
    }
}
