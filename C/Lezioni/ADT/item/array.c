#include <stdio.h>
#include "item.h"
#include "utils.h"

void inputArray(Item arr[], int n){
	int i;

	for(i = 0; i < n; i++){
		arr[i] = inputItem();
	}
}

void printArray(Item arr[], int n){
	int i;

	for(i = 0; i < n; i++){
		outputItem(arr[i]);
	}
}


void bubbleSort(Item arr[], int n){
	int i, j;
	for(i = 1; i < n; i++){
		for(j = 0; j < n - i; j++){
			if( cmpItem(arr[j], arr[j + 1]) >0 ){
				swap(&arr[j], &arr[j + 1]);
			}
		}
	}
}


