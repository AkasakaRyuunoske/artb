#include <stdio.h>
#include <stdlib.h>

#include "punto.h"

int main(void){

    int scelta, length;
    int m;

    double distanza_input;
    double max_distanza;

    Punto * array_punti;

    printf("Quanti nodi vuoi inserire? ");
    scanf("%d", &length);

    array_punti = (Punto*) calloc(length, sizeof(Punto));
    riempiSequenza(array_punti, length);

    printf("Inserire la distanza\n");
    scanf("%f", &distanza_input);

    m = coppieDistMinD(array_punti, length, distanza_input);

    printf("Numero di coppie: %d\n", m);

    max_distanza = maxDistanza(array_punti, length);

    printf("Max distanza: %f\n", max_distanza);

    free(array_punti);
    
    return 0;
}