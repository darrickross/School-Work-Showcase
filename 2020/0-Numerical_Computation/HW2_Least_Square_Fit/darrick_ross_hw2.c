/*
 Darrick Ross
 HW 2

 The following is from simeq.c which is from <REDACTED>
    - abs() #define
    - polyfit()
    - polyval()

    Some modification have been made including comments and clearing unused variables.

    Compiled using the following command
        gcc -o darrickRoss darrick_ross_hw2.c simeq.c -lm

 */

#include <stdio.h>
#include <math.h>
#include "simeq.h"

#undef  abs
#define abs(x) (((x)<0.0)?(-(x)):(x))

const int ARRAY_SIZE = 20;
const int START_DEGREE = 3;
const int MAX_DEGREE = 17;

void polyfit(int order, int n, const double x[], const double y[], double p[]);

double polyval(int order, const double p[], double x);

void clearArray(double arr[], int size);

void printData(int order, double max, double avg, double rms);

double rmsError(int n, const double values[]);

int main() {
    double xArray[] = {0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0,
      1.1, 1.2, 1.3, 1.4, 1.5, 1.6, 1.7, 1.8, 1.9};
    double yArray[] = {0.0, 6.0, 7.0, 9.0, 10.0, 10.0, 8.0, 7.0, 6.0, 5.0, 5.0,
      4.0, 3.0, 3.0, 2.0, 2.0, 1.0, 1.0, 1.0, 0.0};
    double polynomialArray[ARRAY_SIZE];

    double *max;
    double errorArray[ARRAY_SIZE];
    double rms;
    double avg;
    double sum;
    printf("Running Darrick Ross HW 2\n\n");


    for (int d = START_DEGREE; d <= MAX_DEGREE; d++) {
        clearArray(polynomialArray, ARRAY_SIZE);
        clearArray(errorArray, ARRAY_SIZE);
        max = errorArray;   //point to first item
        sum = 0.0;

        //Generate Polynomial Array
        polyfit(d, ARRAY_SIZE, xArray, yArray, polynomialArray);

        for (int i = 0; i < ARRAY_SIZE; i++) {
            //Determine the estimated Y value given the polynomial and the X value
            double calcY = polyval(d, polynomialArray, xArray[i]);

            errorArray[i] = abs(calcY - yArray[i]);

            sum += errorArray[i];

            if (errorArray[i] > *max) {
                max = errorArray + i;   //move the pointer
            }
        }

        avg = sum / (double)(ARRAY_SIZE);

        rms = rmsError(ARRAY_SIZE, errorArray);

        printData(d, *max, avg, rms);
    }

    return (0);
}

//Set all indices in the provided array of length `size` to 0.0
void clearArray(double arr[], int size) {
    for (int i = 0; i < size; i++) {
        arr[i] = 0.0;
    }
}

/*
Print the data out in the format
n=##, max=#.##########, avg=#.##########, rms=#.##########
*/
void printData(int order, double max, double avg, double rms) {
    printf("n=%2d, max=%.10f, avg=%.10f, rms=%.10f\n", order, max, avg, rms);
}

//calculates RMS error
double rmsError(int n, const double values[])
{
    double sumSquared = 0.0;

    for (int i = 0; i < n; i++)
    {
        sumSquared += pow(values[i], 2);
    }
    //mean = (square / (float)(n));
    //return sqrt(mean);
    return sqrt((sumSquared / (double)(n)));
}

//Call this to generate the polynomial vector and store it in `p[]`
void polyfit(int order, int n, const double x[], const double y[], double p[])
// x and y input arrays length n, p polynomial coef of order
{
    double a[order + 1][order + 1];
    //double xx[order + 1];
    double pwr[order + 1];
    double yy[order + 1];
    int i, j, k, kk;

    //clear a[][] and yy[]
    for (i = 0; i <= order; i++) {
        for (j = 0; j <= order; j++) {
            a[i][j] = 0.0;
        }
        yy[i] = 0.0;
    }

    /*
        Given a value of Y for each value of X,
        Y_approximate =  C0 + C1 * X + C2 * X^2 +  C3 * X^3
        Then the linear equations would be:

            | SUM(1  *1) SUM(1  *X) SUM(1  *X^2) SUM(1  *X^3) | | C0 |   | SUM(1  *Y) |
            | SUM(X  *1) SUM(X  *X) SUM(X  *X^2) SUM(X  *X^3) | | C1 |   | SUM(X  *Y) |
            | SUM(X^2*1) SUM(X^2*X) SUM(X^2*X^2) SUM(X^2*X^3) |x| C2 | = | SUM(X^2*Y) |
            | SUM(X^3*1) SUM(X^3*X) SUM(X^3*X^2) SUM(X^3*X^3) | | C3 |   | SUM(X^3*Y) |

        Note that index [i][j] of the matrix has SUM(X^(i+j))

    */
    for (k = 0; k < n; k++) {
        //For each pwr[] current = next * x, thus 1, x, x^2, ... x^order
        pwr[0] = 1.0;
        for (kk = 1; kk <= order; kk++) {
            pwr[kk] = pwr[kk - 1] * x[k]; // 1, x, x^2, x^3, ...
        }


        for (i = 0; i <= order; i++) {
            for (j = 0; j <= order; j++) {
                a[i][j] = a[i][j] + pwr[i] * pwr[j];
            }
            yy[i] = yy[i] + pwr[i] * y[k];
        }
    }

    /*
        Solve the simultaneous equations for C0, C1, C2, C3
        using some version of "simeq". Remember 4 C values for power 3.
        This is equivalent to polyfit.
     */
    simeq2(order + 1, a, yy, p);
} // polyfit

//Call this for every value of x
double polyval(int order, const double p[], double x) // return y = p(x)
{             // using Horner's rule
    double y;
    int i;

    y = p[order] * x; // p one larger than order
    for (i = order - 1; i > 0; i--)
        y = (p[i] + y) * x;
    y = p[0] + y;
    return y;
} // end polyval
