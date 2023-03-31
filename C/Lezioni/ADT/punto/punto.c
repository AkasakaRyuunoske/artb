#include <stdlib.h>
#include <stdio.h>
#include <math.h>

#include "punto.h"

struct Punto{
    double x;
    double y;
};

Punto creaPunto(double x, double y){
    
    Punto punto;

    punto = malloc(sizeof(struct Punto));

    punto->x = x;
    punto->y = y;

    return punto;
}

double ascissa(Punto punto){
    return punto->x;
}

double ordinata(Punto punto){
    return punto->y;
}

double distanza(Punto punto_1, Punto punto_2) {   
    double distance_x = (punto_1->x - punto_2->x) * (punto_1->x - punto_2->x);
    double distance_y = (punto_1->y - punto_2->y) * (punto_1->y - punto_2->y);

    return sqrt(distance_x + distance_y);
}

int coppieDistMinD(Punto point_array[], int length, double distanza_input){
    int i, j, count = 0;

    double distanza_result;

    for(i = 0; i < length; i++){
        for(j = i + 1; j < length; j++){
            distanza_result = distanza(point_array[i], point_array[j]);
            if (distanza_result <= distanza_input) {
                count++;
            }
        }
    }

    return count;
}

double maxDistanza(Punto * array_punti, int length){
    int i, j;
    double max_distanza = 0;
    double distanza_result;

    for( i = 0; i < length; i++){
        for( j = i + 1; j < length; j++){
            distanza_result = distanza(array_punti[i], array_punti[j]);

            if (distanza_result >= max_distanza){
                max_distanza = distanza_result;
            }

        }
    }

    return max_distanza;
}

void spostaPunto(Punto punto, double deltaX, double deltaY){
    punto->x += deltaX;
    punto->y += deltaY;

}

Punto centroide(Punto *punti, int length){
    double sumX = 0.0;
    double sumY = 0.0;
    
    for (int i = 0; i < length; i++) {
        sumX += punti[i]->x;
        sumY += punti[i]->y;
    }
    
    Punto center = creaPunto(sumX / length, sumY / length);
    
    return center;
}

void riempiSequenza(Punto * array_punti, int length){
    double x;
    double y;

    int i;

    for(i = 0; i < length; i++){
        printf("Inserire x e y\n");
        scanf("%f %f", &x, &y);

        array_punti[i] = creaPunto(x,y);
    }
}


