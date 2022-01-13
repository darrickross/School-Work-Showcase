/*
 Darrick Ross
 <REDACTED>
 HW 6
 December 1st 2020

    Running it on GL twice at two different times of the day yielded mostly the same results
    I did notices some major differences between the 2 GL results at N=200 which I would attribute
    to cpu cycles going towards other processes at the time.

    Running it on my computer and comparing the host os to a virtualized Windows Subsystem Linux saw some
    differences that I would attribute to either the linux based system had likely a better optimized for C
    then the Host OS mingw C version. The other idea I have is that the virtualized system only needed to
    focus on running the C code and did not have to (virtually) context switch what the (virtualized) cpu
    needed to do.
*/

/*
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
 */



/* time_matmul.c  fine how large matrix multiple can be */
/*                check that time increases order n^3   */
/*                doubling N takes 8 times as long      */
/*                can be days for 10,000 by 10,000      */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

static double *a; /* input matrix */
static double *b; /* input matrix */
static double *c; /* result matrix */

int main(int argc, char *argv[])
{
  int N;
  int i,j,k;
  double t1, t2;

  for(N=100; N<=1600; N=N*2) /* N<=3200 is OK, takes minutes */
  {
    printf("multiply N by N matrices, N=%d \n", N);
    a = (double *)malloc(N*N*sizeof(double));
    b = (double *)malloc(N*N*sizeof(double));
    c = (double *)malloc(N*N*sizeof(double));
    for(i=0; i<N; i++)
    {
      for(j=0; j<N; j++)
      {
        a[i*N+j] = (double)i;
        b[i*N+j] = (double)j;
      }
    }
    printf("initialized \n");
    t1 = (double)clock()/(double)CLOCKS_PER_SEC;

    for(i=0; i<N; i++)
    {
      for(j=0; j<N; j++)
      {
        c[i*N+j] = 0.0;
        for(k=0; k<N; k++)
          c[i*N+j] = c[i*N+j] + a[i*N+k]*b[k*N+j]; /* most time spent here! */
      }
    }
    t2 = (double)clock()/(double)CLOCKS_PER_SEC;
    printf("N=%d, c=%g, raw time=%g seconds\n", N, c[5], t2-t1);
    t2 = 1.0e9*(t2-t1)/((double)N*(double)N*(double)N);
    printf("order N^3 normalized time=%g \n\n", t2);
    free(a);
    free(b);
    free(c);
  }
  return 0;
} /* end time_matmul.c */
