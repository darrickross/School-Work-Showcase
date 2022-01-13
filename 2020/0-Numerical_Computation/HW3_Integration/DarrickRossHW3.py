# Darrick Ross
# November 28 2020
# <REDACTED>
# Homework 3

from trapezoide import trap_int
from gaulegf import gausLegInt
import math
# math.sin(x)


def exactSolution(lower, upper):
    return (math.cos(lower)) - (math.cos(upper))



def compareTrapInt(func, lowerBound, upperBound, numberOfTimes, points, exactResultFunc):
    print("Comparing Trapezodial Integration to Exact Integration")

    while numberOfTimes > 0:
        #compute values
        trapzResult = trap_int(func, lowerBound, upperBound, points)
        exactResult = exactResultFunc(lowerBound, upperBound)
        error = abs(trapzResult - exactResult)

        print("\tResults for points: {}".format(points))
        print("\t\tT: {}, M: {}, Error: {}".format(trapzResult, exactResult, error))

        #Update Loop
        numberOfTimes = numberOfTimes - 1
        points = points * 2
        #End Loop
        pass
    #END compareTrapInt

def compareGaussLegInt(lowerBound, upperBound, numberOfTimes, points, exactResultFunc):
    print("Comparing Gauss Legendre numerical quadrature to Exact Integration")

    while numberOfTimes > 0:
        #compute values
        gauslResult = gausLegInt(lowerBound, upperBound, points)
        exactResult = exactResultFunc(lowerBound, upperBound)
        error = abs(gauslResult - exactResult)

        print("\tResults for points: {}".format(points))
        print("\t\tG: {}, M: {}, Error: {}".format(gauslResult, exactResult, error))

        #Update Loop
        numberOfTimes = numberOfTimes - 1
        points = points * 2
        #End Loop

    pass
    #END compareGaussLegendre

def isInCircle(x,y, xC, yC, rC):
    # (x - xC)^2 + (y - yC)^2 = rC^2

    leftHandSide = (x-xC)**2 + (y-yC)**2
    rightHandSide = rC**2

    #print("({:.3f} - {:.3f})^2 + ({:.3f} - {:.3f})^2 = {:.3f}^2".format(x, xC, y, yC, rC))
    #print("{:.3f} <= {:.3f}".format(leftHandSide, rightHandSide))


    if leftHandSide <= rightHandSide:
        #print("TRUE")
        return True

    return False


if __name__ == "__main__":
    print("Starting Homework 3:")

    print("Part 1:")
    a = 0 # lower bound
    b = 1 # upper bound
    numPointsTrap = 16
    numPointsGaus = 8
    numTimesTrap = 4
    numTimesGaus = 2

    compareTrapInt(math.sin, a, b, numTimesTrap, numPointsTrap, exactSolution)

    compareGaussLegInt(a, b, numTimesGaus, numPointsGaus, exactSolution)

    print("Part 2:")

    print("For the given circles")
    print("\tCircle 1 center at (2,2) radius 1   (x-2)^2 + (y-2)^2 = 1^2")
    print("\tCircle 2 center at (0,2) radius 2    x^2    + (y-2)^2 = 2^2")
    print("\tCircle 3 center at (0,0) radius 3    x^2    + y^2     = 3^2")
    print("The estimated area inside Circle 2 and 3, but outside Circle 1 is:")

    differentStepSizes = [0.1, 0.01, 0.001]

    #Given the problem, -2 <= x <= 2 (its really )
    xMin = -2.0
    xMax = 2.0
    #Given the problem,  0 <= y <= 3
    yMin = 0.0
    yMax = 3.0

    # Circle 1 center at (2,2) radius 1   (x-2)^2+(y-2)^2=1^2
    xC1 = 2
    yC1 = 2
    rC1 = 1

    # Circle 2 center at (0,2) radius 2    x^2+(y-2)^2=2^2
    xC2 = 0
    yC2 = 2
    rC2 = 2

    # Circle 3 center at (0,0) radius 3    x^2+y^2=3^2
    xC3 = 0
    yC3 = 0
    rC3 = 3

    for step in differentStepSizes:

        numPoints = 0
        areaOfASquare = step**2

        #count number of dots in the square
        x = xMin
        while x <= xMax:
            y = yMin
            while y <= yMax:
                if not isInCircle(x, y, xC1, yC1, rC1):     # Not in C1
                    if isInCircle(x, y, xC2, yC2, rC2):     # Is in C2
                        if isInCircle(x, y, xC3, yC3, rC3): # Is in C3
                            numPoints = numPoints + 1
                y = y + step
                #END While Loop Y
            x = x + step
            #END While Loop X

        #multiply the number of dots by the size of the squares
        area = numPoints * areaOfASquare

        print("\tFor grid size {:.3f}, the area is estimated as {:f}".format(step, area))

        #END For loop
