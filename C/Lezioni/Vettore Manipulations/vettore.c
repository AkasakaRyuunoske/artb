#include <stdio.h>

#include "vettore.h"
#include "utils.h"

// Funzione per l'immissione degli elementi nell'array
void inputArray(int array[], int dimensione) {
    int i;

    printf("Inserisci gli elementi dell'array:\n");

    for (i = 0; i < dimensione; i++) {
        printf("Elemento %d: ", i);
        scanf("%d", &array[i]);
    }
}

void printArray(int array[], int dimensione) {
    int i;

    printf("Contenuto dell'array:\n");

    for (i = 0; i < dimensione; i++) {
        printf("%d ", array[i]);
    }

    printf("\n");
 }


int indexOf(int array[], int dimensione, int elemento) {
    int i;

    for (i = 0; i < dimensione; i++) {
        if (array[i] == elemento) {
            return i;
        }
    }

    return -1;
}

int min(int array[], int dimensione) {
    int i;
    int minimo = 0;

    for (i = 1; i < dimensione; i++) {
        if (array[i] < array[minimo]) {
            minimo = i;
        }
    }

    return minimo;
}

void ordina_array(int array[], int dimensione) {
    int i;
    int minimo;

    for(i = 0; i < dimensione-1; i++){
        minimo = min(array + i, dimensione-i) + i;    
        swap(&array[minimo], &array[i]);
    }
    // for (i = 0; i < dimensione - 1; i++) {
    //     for (j = i + 1; j < dimensione; j++) {
    //         if (array[i] > array[j]) {
    //             swap(&array[i], &array[j]);
    //         }
    //     }   
    // }
}

void add(int array[], int *dimensione, int elemento, int posizione) {
    int i;

    // Shift degli elementi a destra dalla posizione indicata
    for (i = *dimensione - 1; i >= posizione; i--) {
        array[i + 1] = array[i];
    }

    // Inserimento del elemento nella posizione indicata
    array[posizione] = elemento;
}


void delete(int array[], int *dimensione, int posizione) {
    int i;

    // Shift degli elementi a sinistra dalla posizione indicata
    for (i = posizione; i < *dimensione - 1; i++) {
        array[i] = array[i + 1];
    }

    // Azzeramento dell'ultimo elemento
    array[*dimensione - 1] = 0;
}


