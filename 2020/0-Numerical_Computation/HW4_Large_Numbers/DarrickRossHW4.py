# Darrick Ross
# November 28 2020
# <REDACTED>
# Homework 4

# f'{value:,}' <- print with ###,###,###,### for commas seprating (compatible with python >= 3.6)

def factorial(n):
    if n < 1:
        return 0    #Bad call
    elif n == 1:
        return n
    else:
        return n * factorial(n - 1)
    #END factorial

def nChooseM(n, m):
    #n! / ((n-m)! m!).

    a = factorial(n)
    b = factorial(n-m)
    c = factorial(m)

    return a / (b * c)
    #END nChooseM

if __name__ == "__main__":
    print("Starting Homework 4:")

    #1
    print("52! is,")
    resultA = factorial(52)

    print(f"{resultA:,}")

    print("\n")

    #2
    print("52 choose 5 is,")

    resultB = nChooseM(52, 5)

    print(f"{resultB:,}")

    print("End Homework 4")
