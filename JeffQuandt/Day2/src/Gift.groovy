class Gift {

    Integer height
    Integer width
    Integer length

    def Gift(dimensions) {
        def sides = dimensions.tokenize('x')
        height = sides.get(0).toInteger()
        width = sides.get(1).toInteger()
        length = sides.get(2).toInteger()
    }

    def findWrappingArea() {

        def area = 2 * height * width + 2 * height * length + 2 * width * length + findSmallestSide()
    }

    def findSmallestSide() {

        def smallest = height * width

        smallest = Math.min(smallest, height * length)
        smallest = Math.min(smallest, width * length)

        smallest
    }

    def bowLength() {
        height * width * length
    }

    def ribbonCircumference() {
        def shortest = (height + width) * 2

        shortest = Math.min(shortest, (height + length) *2)
        shortest = Math.min(shortest, (width + length) *2)
        shortest
    }
}
