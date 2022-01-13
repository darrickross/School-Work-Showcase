/* simeq.c  solve simultaneous equations AX=Y */
#include <stdio.h>
#include <stdlib.h>
#undef  abs
#define abs(x) (((x)<0.0)?(-(x)):(x))


/*      PURPOSE : SOLVE THE LINEAR SYSTEM OF EQUATIONS WITH REAL     */
/*                COEFFICIENTS   [A] * |X| = |Y|                     */
/*                                                                   */
/*      INPUT  : THE NUMBER OF EQUATIONS  n                          */
/*               THE REAL MATRIX  A   should be A[i][j] but A[i*n+j] */
/*               THE REAL VECTOR  Y                                  */
/*      OUTPUT : THE REAL VECTOR  X                                  */
/*                                                                   */
/*      METHOD : GAUSS-JORDAN ELIMINATION USING MAXIMUM ELEMENT      */
/*               FOR PIVOT.                                          */
/*                                                                   */
/*      USAGE  :     simeq(n,A,X,Y);                                 */
/*                                                                   */
/*                                                                   */
/*    WRITTEN BY : JON SQUIRE , 28 MAY 1983                          */
/*    ORIGONAL DEC 1959 for IBM 650, TRANSLATED TO OTHER LANGUAGES   */
/*    e.g. FORTRAN converted to Ada converted to C                   */
/*    modified to have simeqb                                        */

#include "simeq.h"

void simeq(int n, double A[], double Y[], double X[])
{
  double *B;           /* [n][n+1]  WORKING MATRIX */
  int i, j, m;

  B = (double *)calloc(n*(n+1), sizeof(double));
  m = n+1;
  /* BUILD WORKING DATA STRUCTURE */
  for(i=0; i<n; i++){
    for(j=0; j<n; j++){
      B[i*m+j] = A[i*n+j];
    }
    B[i*m+n] = Y[i];
  }
  simeqb(n, B, X);
  free(B);
} /* end simeq */

void simeq2(int n, double A[n][n], double Y[], double X[])
{
  double *B;           /* [n][n+1]  WORKING MATRIX */
  int i, j, m;

  B = (double *)calloc(n*(n+1), sizeof(double));
  m = n+1;
  /* BUILD WORKING DATA STRUCTURE */
  for(i=0; i<n; i++){
    for(j=0; j<n; j++){
      B[i*m+j] = A[i][j];
    }
    B[i*m+n] = Y[i];
  }
  simeqb(n, B, X);
  free(B);
} /* end simeq2 */

void simeq2b(int n, double A[n][n+1], double X[])
{
  double *B;           /* [n][n+1]  WORKING MATRIX */
  int i, j, m;

  B = (double *)calloc(n*(n+1), sizeof(double));
  m = n+1;
  /* BUILD WORKING DATA STRUCTURE */
  for(i=0; i<n; i++){
    for(j=0; j<n; j++){
      B[i*m+j] = A[i][j];
    }
    B[i*m+n] = A[i][n];
  }
  simeqb(n, B, X);
  free(B);
} /* end simeq2 */

void simeqb(int n, double B[], double X[])
{
    int *ROW;            /* ROW INTERCHANGE INDICIES */
    int HOLD , I_PIVOT;  /* PIVOT INDICIES */
    double PIVOT;        /* PIVOT ELEMENT VALUE */
    double ABS_PIVOT;
    int i,j,k,m;

    ROW = (int *)calloc(n, sizeof(int));
    m = n+1;

    /* SET UP ROW  INTERCHANGE VECTORS */
    for(k=0; k<n; k++){
      ROW[k] = k;
    }

    /* BEGIN MAIN REDUCTION LOOP */
    for(k=0; k<n; k++){

      /* FIND LARGEST ELEMENT FOR PIVOT */
      PIVOT = B[ROW[k]*m+k];
      ABS_PIVOT = abs(PIVOT);
      I_PIVOT = k;
      for(i=k; i<n; i++){
        if( abs(B[ROW[i]*m+k]) > ABS_PIVOT){
          I_PIVOT = i;
          PIVOT = B[ROW[i]*m+k];
          ABS_PIVOT = abs ( PIVOT );
        }
      }

      /* HAVE PIVOT, INTERCHANGE ROW POINTERS */
      HOLD = ROW[k];
      ROW[k] = ROW[I_PIVOT];
      ROW[I_PIVOT] = HOLD;

      /* CHECK FOR NEAR SINGULAR */
      if( ABS_PIVOT < 1.0E-64 ){
        for(j=k+1; j<n+1; j++){
          B[ROW[k]*m+j] = 0.0;
        }
        printf("redundant row (singular) %d \n", ROW[k]);
      } /* singular, delete row */
      else{

        /* REDUCE ABOUT PIVOT */
        for(j=k+1; j<n+1; j++){
          B[ROW[k]*m+j] = B[ROW[k]*m+j] / B[ROW[k]*m+k];
        }

        /* INNER REDUCTION LOOP */
        for(i=0; i<n; i++){
          if( i != k){
            for(j=k+1; j<n+1; j++){
              B[ROW[i]*m+j] = B[ROW[i]*m+j] - B[ROW[i]*m+k] * B[ROW[k]*m+j];
            }
          }
        }
      }
      /* FINISHED INNER REDUCTION */
    }

    /* END OF MAIN REDUCTION LOOP */
    /* BUILD  X  FOR RETURN, UNSCRAMBLING ROWS */
    for(i=0; i<n; i++){
      X[i] = B[ROW[i]*m+n];
    }
    free(ROW);
} /* end simeqb */

