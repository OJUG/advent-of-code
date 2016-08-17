class Santa {
  int x
  int y

  def move(direction) {
    switch (direction) {
      case '<':
        moveWest()
        break
      case 'v':
        moveSouth()
        break
      case '>':
        moveEast()
        break
      case '^':
        moveNorth()
        break
    }
    dropPresent()
  }

  def moveNorth(){this.y++}

  def moveSouth(){this.y--}

  def moveEast(){this.x++}

  def moveWest(){this.x--}

  def dropPresent(){
    "${x}:${y}".toString()
  }

}
