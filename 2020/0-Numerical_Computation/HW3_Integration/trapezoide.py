# Initially from <REDACTED>
# Modified to fit Python 3 standards
# as well as function as a module.

# trapezoide.py   trapezoidal integration of a function
def xSquared(x):
  return x*x

def trap_int(f, xmin, xmax, nstep): # integrate f(x) from xmin to xmax
  area=(f(xmin)+f(xmax))/2.0
  h = (xmax-xmin)/nstep
  for i in range(1,nstep):
    x = xmin+i*h
    area = area + f(x)

  return area*h # trapezoidal method

if __name__ == "__main__":
    print("trapezoide.py running")
    xmin = 1.0
    xmax = 2.0
    n = 10
    area = trap_int(xSquared, xmin, xmax, n)
    print("trap_int area under x*x from ",xmin," to ",xmax," = ",area)
    print("trapezoide.py finished")
else:
    print("Loaded trapezoide.py")
