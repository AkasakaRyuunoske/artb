#include <stdio.h>

#include "punto.h"

int main(void){

    Punto punto_1;
    Punto punto_2;
    Punto punto_3;
    Punto punto_4;

    double x = 2.0;
    double y = 4.0;

    punto_1 = newPunto(x, y);
    punto_2 = newPunto(x, y);
    punto_3 = newPunto(4.5, 5.4);
    punto_4 = newPunto(1.2, 2.3);

    printf("punto_1 x = %f\n", getX(punto_1));
    printf("punto_1 y = %f\n", getY(punto_1));
    
    setX(punto_1, 5.0);
    setY(punto_1, 9.0);

    printf("punto_1 x = %f\n", getX(punto_1));
    printf("punto_1 y = %f\n", getY(punto_1));
    
    printf("Result of compare punti: %d\n", compare_punto(punto_1, punto_2));
    
    Punto punto_add = add(punto_1, punto_2);

    printf("Result of add: %f %f", getX(punto_add), getY(punto_add));

    Punto punti_array[4] = {punto_1, punto_2, punto_3, punto_4}; 

    Punto punto_updated = movePunto(punto_1, 10.0, 12.0);
    
    Punto punto_centroide = centroide(punti_array, 4);
    
    printf("punto centroide x = %f\n", getX(punto_centroide));
    printf("punto centroide y = %f\n", getY(punto_centroide));

    return 0;
}