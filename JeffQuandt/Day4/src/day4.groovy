import java.security.MessageDigest

def key = 'yzbqklnj'

MessageDigest hasher = MessageDigest.getInstance('MD5')

def input = 1
def md5 = ''
def coinPtrnP1 = ~/^0{5}/
def coinPtrnP2 = ~/^0{6}/
def foundPt1 = false
def foundPt2 = false
def coin1
def coin2
while (true) {

    md5 = hasher.digest((key + input.toString()).getBytes()).encodeHex().toString()

    if (!foundPt1 && md5.find(coinPtrnP1)) {
        foundPt1 = true
        coin1 = input
    }

    if (!foundPt2 && md5.find(coinPtrnP2)) {
        foundPt2 = true
        coin2 = input
    }

    if (foundPt1 && foundPt2) {
        break
    }
    input++
}

println "First coin input $coin1"
println "Second coin input $coin2"


