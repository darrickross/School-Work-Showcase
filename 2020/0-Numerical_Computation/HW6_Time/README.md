# Overview:

- `README.md` : This file
- `time_matmul.c` : The driver program.



The assignment was a simple test of timing of programs on different systems.



# Output from Different Systems. 
```
PC: Desktop                         | PC: Desktop Virtualized
OS: Windows 10 64bit                | OS: Windows Subsystem Linux Ubuntu 20.04.1
Compiled on CLion 2020.2.5          | Compiled using gcc 9.3.0
C version: C99                      | C version: UNKNOWN
                                    |
multiply N by N matrices, N=100     | multiply N by N matrices, N=100
initialized                         | initialized
N=100, c=0, raw time=0.004 seconds  | N=100, c=0, raw time=0 seconds
order N^3 normalized time=4         | order N^3 normalized time=0
                                    |
multiply N by N matrices, N=200     | multiply N by N matrices, N=200
initialized                         | initialized
N=200, c=0, raw time=0.026 seconds  | N=200, c=0, raw time=0.03125 seconds
order N^3 normalized time=3.25      | order N^3 normalized time=3.90625
                                    |
multiply N by N matrices, N=400     | multiply N by N matrices, N=400
initialized                         | initialized
N=400, c=0, raw time=0.22 seconds   | N=400, c=0, raw time=0.203125 seconds
order N^3 normalized time=3.4375    | order N^3 normalized time=3.17383
                                    |
multiply N by N matrices, N=800     | multiply N by N matrices, N=800
initialized                         | initialized
N=800, c=0, raw time=1.696 seconds  | N=800, c=0, raw time=1.65625 seconds
order N^3 normalized time=3.3125    | order N^3 normalized time=3.23486
                                    |
multiply N by N matrices, N=1600    | multiply N by N matrices, N=1600
initialized                         | initialized
N=1600, c=0, raw time=20.729 seconds| N=1600, c=0, raw time=21.9062 seconds
order N^3 normalized time=5.06079   | order N^3 normalized time=5.34821
                                    |
-------------------------------------------------------------------------------

GL                                   |  GL
Compiled using gcc 10.2.1            |  Compiled using gcc 10.2.1

multiply N by N matrices, N=100      |  multiply N by N matrices, N=100
initialized                          |  initialized
N=100, c=0, raw time=0.005164 seconds|  N=100, c=0, raw time=0.005562 seconds
order N^3 normalized time=5.164      |  order N^3 normalized time=5.562
                                     |
multiply N by N matrices, N=200      |  multiply N by N matrices, N=200
initialized                          |  initialized
N=200, c=0, raw time=0.037439 seconds|  N=200, c=0, raw time=0.049464 seconds
order N^3 normalized time=4.67988    |  order N^3 normalized time=6.183
                                     |
multiply N by N matrices, N=400      |  multiply N by N matrices, N=400
initialized                          |  initialized
N=400, c=0, raw time=0.398732 seconds|  N=400, c=0, raw time=0.412821 seconds
order N^3 normalized time=6.23019    |  order N^3 normalized time=6.45033
                                     |
multiply N by N matrices, N=800      |  multiply N by N matrices, N=800
initialized                          |  initialized
N=800, c=0, raw time=2.99144 seconds |  N=800, c=0, raw time=3.09185 seconds
order N^3 normalized time=5.84266    |  order N^3 normalized time=6.03877
                                     |
multiply N by N matrices, N=1600     |  multiply N by N matrices, N=1600
initialized                          |  initialized
N=1600, c=0, raw time=50.0734 seconds|  N=1600, c=0, raw time=49.4139 seconds
order N^3 normalized time=12.225     |  order N^3 normalized time=12.064
```
