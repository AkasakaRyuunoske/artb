#include <stdlib.h>
#include <math.h>

#include "punto.h"

struct Punto{
    double x;
    double y;
};

Punto newPunto(double x, double y){
    
    Punto punto;

    punto = malloc(sizeof(struct Punto));

    punto->x = x;
    punto->y = y;

    return punto;
}


// Getters
double getX(Punto punto){
    return punto->x;
}

double getY(Punto punto){
    return punto->y;
}

// Setters
void setX(Punto punto, double newX){
    punto->x = newX;
}

void setY(Punto punto, double newY){
    punto->y = newY;
}

// Cringers
double distance(Punto punto_1, Punto punto_2) {   
    double distance_x = (punto_1->x - punto_2->x) * (punto_1->x - punto_2->x);
    double distance_y = (punto_1->y - punto_2->y) * (punto_1->y - punto_2->y);

    return sqrt(distance_x + distance_y);
}

Punto add(Punto punto_a, Punto punto_b){
    Punto result_punto = newPunto(getX(punto_a) + getX(punto_b), getY(punto_a) + getY(punto_b));

    return result_punto;
}

int compare_punto(Punto punto_a, Punto punto_b){

    if ((getX(punto_a) == getX(punto_b)) &&
        (getY(punto_a) == getY(punto_b))) {
        return 0;
    }
 
    return 1;
}

void inputPunto(Punto punto){
    double x, y;

    scanf("%f", &x);
    scanf("%f", &y);

    setX(punto, x);
    setY(punto, y);
}   

void outputPunto(Punto punto){
    printf("%f %f", getX(punto), getY(punto));
}

Punto movePunto(Punto punto, double deltaX, double deltaY){
    punto->x += deltaX;
    punto->y += deltaY;

    return punto;
}

Punto centroide(Punto *punti, int length){
    double sumX = 0.0;
    double sumY = 0.0;
    
    for (int i = 0; i < length; i++) {
        sumX += punti[i]->x;
        sumY += punti[i]->y;
    }
    
    Punto center = newPunto(sumX / length, sumY / length);
    
    return center;
}


