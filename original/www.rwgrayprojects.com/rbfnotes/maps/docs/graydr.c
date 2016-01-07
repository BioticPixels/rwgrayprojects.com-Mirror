/********************************************************/
/* This program is copyrighted by Robert W. Gray and    */
/* not be used in ANY for-profit project without his    */
/* say so.                                              */
/********************************************************/
/* C program to call Dymaxion Map conversion procedures */
/* It is intended to only show you how to call the      */
/* conversion routine so that you may write your own    */
/* driver.                                              */
/********************************************************/

#include <stdio.h>;
#include <stdlib.h>;
#include <math.h>;

/********************************************************/
/* Define global variables and procedures               */
/********************************************************/

double v_x[13], v_y[13], v_z[13];
double center_x[21], center_y[21], center_z[21];
double garc, gt, gdve, gel;

extern void init_stuff(void);
extern void convert_s_t_p(double lng, double lat, double *x, double *y);

/********************************************************/
/* Define other variables and procedures                */
/********************************************************/

int what_triangle, lcd;
double lng, lat, x, y;
char ch;

main()
{
 init_stuff();
 do{
    printf("\n Enter a longitude and latitude coordinate: ");
    scanf("%lf %lf",&lng,&lat);

    printf("\n lng = %f lat = %f \n",lng,lat);

    convert_s_t_p(lng, lat, &x, &y);

    printf("\n X = %f,  Y = %f \n",x,y);
    printf("\n Do you want to quit (Y/N)? ");

   } while ((ch = getch()) != 'Y');

} /* end MAIN */