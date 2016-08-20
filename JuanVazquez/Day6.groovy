// javazquez.com
// https://github.com/javazquez/

def lights1 = new int[1000][1000]
def lights2 = new int[1000][1000]

def directions = new File('day6input.txt').readLines()

def slicer ={lights, x1,y1, x2,y2, clos ->
    lights[y1..y2].each{ row->
        (x1..x2).each{ row[it] = clos(row[it]) }}}  //<----- Clojure influence :P  https://xkcd.com/297/

def findAllLightsOn = { lights -> lights.sum { row ->  row.sum() }}

def followdirections = { directionList, lights, Map<String,Closure> actionMap ->   
    directionList.collect { it =~ /(toggle|on|off) (\d+),(\d+) through (\d+),(\d+)/} //returns a collection of matchers
        .each{ matcher ->   
            def slicerCurry = slicer.curry(lights,matcher[0][2].toInteger(), matcher[0][3].toInteger(), matcher[0][4].toInteger(), matcher[0][5].toInteger())
            if(matcher[0][1] == "toggle"){
                slicerCurry(actionMap.toggle)
            }else if(matcher[0][1] == "on"){
                slicerCurry(actionMap.on)
            }else{
                slicerCurry(actionMap.off) }}}  

followdirections(directions,lights1,[on : { item -> 1 }, 
                                    off : { item -> 0}, 
                                 toggle : { item-> item ^= 1} ])

followdirections(directions, lights2,[    on : { item -> item + 1 }, 
                                        off : { item -> (item - 1) >= 0 ? (item - 1) : 0}, 
                                     toggle : { item-> item + 2} ])

assert findAllLightsOn(lights1) == 569999
assert findAllLightsOn(lights2) == 17836115
