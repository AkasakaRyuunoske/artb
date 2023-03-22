#include <stdlib.h>
#include <math.h>

#include "punto.h"

struct Punto{
    double x;
    double y;
};

Punto newPunto(double x, double y){
    
    struct Punto punto;

    punto = malloc(sizeof(struct Punto));

    punto.x = x;
    punto.y = y;

    return punto;
}

double getX(Punto punto){
    return punto->x;
}

double getY(Punto punto){
    return punto->y;
}

double distance(Punto punto_1, Punto punto_2)
{   
    double distance_x = (punto_1->x - punto_2->x) * (punto_1->x - punto_2->x);
    double distance_y = (punto_1->y - punto_2->y) * (punto_1->y - punto_2->y);

    return sqrt(distance_x + distance_y);
}