/* simeq.h  solve A[n,n]*X[n]=Y[n] for vector X */
/* A preserved */
void simeq(int n, double A[], double Y[], double X[]);
void simeq2(int n, double A[n][n], double Y[], double X[]);
/* last column of B is Y, all overwritten */
void simeqb(int n, double B[], double X[]);
void simeq2b(int n, double B[n][n+1], double X[]);

