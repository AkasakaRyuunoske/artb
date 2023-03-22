#include <stdio.h>

#include "punto.h"

int main(void){

    Punto punto_1, punto_2;
    double x = 2.0;
    double y = 4.0;

    punto_1 = newPunto(x,y);

    punto_2 = newPunto(x,y);

    printf("punto_1 x = %f\n", punto_1->x);
    return 0;
}