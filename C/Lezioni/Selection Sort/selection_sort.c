#include <stdio.h>

#define N 5

void swap(int *a, int *b){
	int temp=*a;
	*a=*b;
	*b=temp;
}

int minimo(int *array, int n){
	int i;
	int minimo = 0;

	for(i = 1; i < n; i++){
		if (array[minimo] > array[i]){
			minimo = i;
		}
	}

	return minimo;
}

void inputArray(int *array, int n){
	int i;

	for(i = 0; i < n; i++){
		scanf("%d",&array[i]);
	}
}

void printArray(int *array, int n){
	int i;

	for(i = 0; i < n; i++){
		printf("%d ", array[i]);
	}
}

void selectionSort(int *array, int n){
	int i;
	int min;

	for(i = 0; i < n-1; i++){
		min = minimo(array + i, n-i) + i;
		swap(&array[min], &array[i]);
	}	
}

int main(){
	int array[N];

	inputArray(array, N);
	selectionSort(array, N);
	printArray(array, N);

	return 0;
}
