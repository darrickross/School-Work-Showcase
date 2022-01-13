# Darrick Ross
# CMSC 455
# Project
# December 5 2020

# from mpmath import mpf, mpc, sin, cos, exp
import mpmath as mpmath
from mpmath import *

prec = 150
divDPS = 2 * prec
mpf.dps = prec
mp.dps  = prec
HALF = mpf('0.5')
counter = mpf('2.0')
dy = mpf('.001')

# z =   exp(sin(50.0*x)) + sin(60.0*exp(y)) +
#       sin(80.0*sin(x)) + sin(sin(70.0*y)) -
#       sin(10.0*(x+y)) + (x*x+y*y)/4.0
def f(x, y):
    #exp(sin(50.0*x))
    result = mpmath.exp(mpmath.sin(fmul(50.0,x, prec=inf)))

    #sin(60.0*exp(y))
    #bb = mpmath.sin(fmul(60.0, mpmath.exp(y), prec=inf))              #Not good together for some reason
    b1 = mpmath.exp(y)
    b2 = fmul(60.0, b1)
    b3 = mpmath.sin(b2)
    result = fadd(result, b3, prec=inf)

    #sin(80.0*sin(x))
    #cc = mpmath.sin(fmul(80.0,mpmath.sin(x), prec=inf))               #Not good together for some reason
    c1 = mpmath.sin(x)
    c2 = fmul(80.0, c1)
    c3 = mpmath.sin(c2)
    result = fadd(result, c3, prec=inf)

    #sin(sin(70.0*y))
    dd = mpmath.sin(mpmath.sin(fmul(70.0, y, prec=inf)))
    result = fadd(result, dd, prec=inf)
    
    #-sin(10.0*(x+y))
    ee = mpmath.sin(fmul(10.0, fadd(x,y, prec=inf), prec=inf))
    result = fsub(result, ee, prec=inf)                                 #Formula subtracts this component

    #(x*x+y*y)/4.0
    f1 = fmul(x, x, prec=inf)
    f2 = fmul(y, y, prec=inf)
    f3 = fadd(f1, f2, prec=inf)
    ff = fdiv(f3, 4.0, prec=prec, dps=divDPS)
    result = fadd(result, ff, prec=inf)


    return result
    
   #return fadd(exp(sin(fmul(50.0,x, prec=inf)))  , sin(fmul(60.0, exp(y), prec=inf))   , sin(fmul(80.0,sin(x), prec=inf))    , sin(sin(fmul(70.0, y, prec=inf))) , fneg(sin(fmul(10.0, fadd(x,y, prec=inf), prec=inf)))    , fdiv(fmul(x,fadd(x, y, prec=inf), y, prec=inf), 4)    , prec=inf)
   #return exp(sin(50.0*x))                         + sin(60.0*exp(y))                      + sin(80.0*sin(x))                      + sin(sin(70.0*y))                    - sin(10.0*(x+y))                                             + (x*x+y*y)/4.0


def findNewBest(x, y, dx):
    z = f(x,y)
    option = 0

    #top left
    testMin = f(x-dx , y+dx) 
    if testMin < z:
        z = testMin
        option = 1
    

    #test up
    testMin = f(x, y+dx) 
    if testMin < z:
        z = testMin 
        option = 2 
    
    
    #test top right
    testMin = f(x+dx, y+dx) 
    if testMin < z:
        z = testMin 
        option = 3 
    
    
    #test right
    testMin = f(x+dx, y) 
    if testMin < z:
        z = testMin 
        option = 4 
    
    
    #test bottom right
    testMin = f(x+dx, y-dx) 
    if testMin < z:
        z = testMin 
        option = 5 
    
    
    #test bottom
    testMin = f(x, y-dx) 
    if testMin < z:
        z = testMin 
        option = 6 
    
    
    #test bottom left
    testMin = f(x-dx, y-dx) 
    if testMin < z:
        z = testMin 
        option = 7 
    
    
    #test left
    testMin = f(x-dx, y) 
    if testMin < z:
        z = testMin 
        option = 8 
    

    if option == 1:         # Top Left
        x  = fsub(x, dx, prec=inf)
        y  = fadd(y, dx, prec=inf)
        #dx = fmul(dx, HALF, prec=inf)
        #dx = fmul(dx, HALF, prec=inf)
    elif option == 2:       # Top
        # x doesnt change
        y  = fadd(y, dx, prec=inf)
        # dx doesnt change
        #dx = fmul(dx, HALF, prec=inf)
    elif option == 3:       # Top Right
        x  = fadd(x, dx, prec=inf)
        y  = fadd(y, dx, prec=inf)
        #dx = fmul(dx, HALF, prec=inf)
        #dx = fmul(dx, HALF, prec=inf)
    elif option == 4:       # Right
        x  = fadd(x, dx, prec=inf)
        # y doesnt change
        #dx = fmul(dx, HALF, prec=inf)
        # dx doesnt change
    elif option == 5:       # Bottom Right
        x  = fadd(x, dx, prec=inf)
        y  = fsub(y, dx, prec=inf)
        #dx = fmul(dx, HALF, prec=inf)
        #dx = fmul(dx, HALF, prec=inf)
    elif option == 6:       # Bottom
        # x doesnt change
        y  = fsub(y, dx, prec=inf)
        # dx doesnt change
        #dx = fmul(dx, HALF, prec=inf)
    elif option == 7:       # Bottom Left
        x  = fsub(x, dx, prec=inf)
        y  = fsub(y, dx, prec=inf)
        #dx = fmul(dx, HALF, prec=inf)
        #dx = fmul(dx, HALF, prec=inf)
    elif option == 8:       # Left
        x  = fsub(x, dx, prec=inf)
        # y doesnt change
        #dx = fmul(dx, HALF, prec=inf)
        # dx doesnt change
    else:
        #dx = fmul(dx, HALF, prec=inf, round='u')
        #dx = fmul(dx, HALF, prec=inf)


        global counter, dy
        counter = fmul(counter, mpf('2.0'))
        #dy = fdiv(dy, counter, prec=divDPS)
        dx = fdiv(mpf('1.0'), chop(counter), prec=divDPS)
        pass

    return x, y, dx, z



if __name__ == "__main__":
    print("Starting Project")
    # Known location to start from
    # x ~=  0.469
    # y ~= -0.923
    x = mpf('0.469')
    y = mpf('-0.923')
    z = f(x, y)
    dx = mpf('1e-3')

    constraint = mpf('1e-' + str(prec))

    print("starting x= ", x)
    print("starting y= ", y)
    
    while dx > constraint or dx > constraint:
        x, y, dx, z = findNewBest(x, y, dx)


    print("Global Minimum Calculated as:")
    print("x=  ", x)
    print("y= ", y)
    print("z= ", z)
    print("dx = ", dx)


