#include <stdio.h>

#include "vettore.h"
#include "utils.h"

#define MAX_LENGTH 100

int main() {
    int scelta;
    int dimensione;
    int array[MAX_LENGTH];
    int elemento, minimo, indice;

    // Richiesta della dimensione del vettore dal utente
    printf("Inserisci la dimensione del vettore (max 100): ");
    scanf("%d", &dimensione);

    do {
        // Menu
        printf("  1. Immissione elementi in un dato array\n");
        printf("  2. Stampare il contenuto di un dato array\n");
        printf("  3. Ricerca di un elemento in un dato array\n");
        printf("  4. Ricercare il minimo in un dato array\n");
        printf("  5. Ordinare un dato array\n");
        printf("  6. Eliminare un elemento in un dato array\n");
        printf("  7. Inserimento di un elemento in un dato array\n");
        printf("  0. Uscire\n");
        printf("\nScelta: ");
        
        scanf("%d", &scelta);

        switch (scelta) {
            case 1:
                inputArray(array, dimensione);
                
                break;

            case 2:
                printArray(array, dimensione);
                
                break;

            case 3:
                printf("Inserisci l'elemento da cercare: ");
                scanf("%d", &elemento);

                indice = indexOf(array, dimensione, elemento);

                if (indice != -1) {
                    printf("L'elemento %d si trova alla posizione %d.\n", elemento, indice);
                } else {
                    printf("L'elemento %d non e' presente nell'array.\n", elemento);
                }

                break;

            case 4:
                minimo = min(array, dimensione);

                printf("posizione del minimo dell'array e' %d.\n", minimo);
                printf("Il minimo dell'array e' %d.\n", array[minimo]);

                break;

            case 5:
                ordina_array(array, dimensione);
                
                printf("L'array e' stato ordinato.\n");
                
                break;

            case 6:
                printf("Inserisci l'indice dell'elemento da eliminare: ");
                scanf("%d", &indice);

                delete(array, &dimensione, indice);
                
                break;

            case 7:
                printf("Inserisci l'indice in cui inserire l'elemento: ");
                scanf("%d", &indice);

                printf("Inserisci l'elemento da inserire: ");
                scanf("%d", &elemento);                

                add(array, &dimensione, elemento, indice);
                
                break;

            case 0:
                printf("Programma terminato.\n");
                
                break;

            default:
                printf("Scelta non valida.\n");
                
                break;
        }

    } while (scelta != 0);

    return 0;
}
