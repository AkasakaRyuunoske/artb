#include <stdlib.h> // calloc/malloc/realloc/free/NULL
#include <stdio.h>  // Printf/Scanf

#include "utils.h"  // Swap

/**
 * Contains all functionality needed to work with Arrays (Only int for now)
 * 
 * @Akasaka
 * 
 * */

//TODO buble, insertion, selection, quick, merge SORTS
//TODO Search + Binary Search
//TODO input arrays from file
//TODO testing with files, oracles n voidrays
//TODO compare arrays
//TODO Add/sub/mul/div all elements of array together
//TODO add/sub/mul/div two arrays

int * input_int_array_dyn(int length){
	int * array = calloc(length, sizeof(int));

	return array;
}

void input_int_elements(int length, int * array){
	int i;
	for(i = 0; i < length; i++){
		printf("array[%d] = ", i);
		scanf("%d", &array[i]);
		printf("\n");
	}
}

void print_array(int length, int array[]){
	int i;

	for(i = 0; i < length; i++){
		printf("array[%d] = %d\n", i, array[i]);
	}
}

// Add / Delete / Update By element
void add(int * length, int array[], int element, int position){

	if (position > *length || position < 0) {

		printf("Error: Position is greater than length.\n");

	} else {
		
		int i;
		(*length)++;

		for (i = *length - 1; i >= position; i--){
			array[i] = array[i - 1];
		}

		array[position - 1] = element;
	}
}

void delete(int * length, int array[], int position){
	if (position > *length || position < 0) {

		printf("Error: Position is greater than length.\n");

	} else {

		int i;

		for (i = position - 1; i < *length - 1; i++){
			array[i] = array[i + 1];
		}

		(*length)--;

	}
}

void update(int length, int array[], int position, int element){
	int i;
	array[position] = element;
}


// Search / Binary Search

int indexOf(int length, int *array, int element){
	int index;
	int i;

	for(i = 0; i < length; i++){
		if (element == array[i]) {
			index = i;
			break;
		}
	}

	return index;
}

// min / max

int min(int length, int array[]){
	int min = array[0];
	int i;

	for(i = 0; i < length; i++){
		if(min > array[i]){
			min = array[i];
		}
	}
	
	return min;
}

int max(int length, int array[]){
	int max = array[0];
	int i;

	for(i = 0; i < length; i++){
		if(max < array[i]){
			max = array[i];
		}
	}
	
	return max;
}

// Add / Sub / Mul / Div all elements of array together
int sum_all_elements(int length, int array[]){
	int result = 0;
	int i;

	for(i = 0; i < length; i++){
		result += array[i];
	}

	return result;
}

int sub_all_elements(int length, int array[]){
	int result = 0;
	int i;

	for(i = 0; i < length; i++){
		result -= array[i];
	}

	return result;
}	

// SORTING 

// me monke, me not explain, me go links: 
// Buble     Sort: https://www.geeksforgeeks.org/bubble-sort/
// Selection Sort: https://www.geeksforgeeks.org/selection-sort/
// Inserion  Sort: https://www.geeksforgeeks.org/insertion-sort/

// Quick Sort: https://youtu.be/Vtckgz38QHs
// Quick Sort: https://youtu.be/0jDiBM68NGU
// Quick Sort: https://www.geeksforgeeks.org/quick-sort/

// Buble Sort
void buble_sort(int length, int array[]){
	int i, j;

	for(i = 0; i < length - 1; i++){
		for(j = 0; j < length - 1; j++){
			if(array[j] > array[j + 1]){
				swap(&array[j], &array[j+1]);
			}
		}
	}
}	

// Selection Sort
void selection_sort(int length, int array[]){
	int i;
	int index_minimum;

	for(i = 0; i < length -1; i++){
		index_minimum = indexOf(length, array, ( min(length-i, array + i) + i) ); // get index of the smallest element in the partition of array
		swap(&array[index_minimum], &array[i]); // swap two elements by index
	}
}

// Insertion Sort
void insertion_sort(int length, int array[]){
	int i, j;
	int key;

	for(i = 1; i < length; i++){
		key = array[i];
		j = i -1;

		while(j >= 0 && array[j] > key){
			array[j+1] = array[j];
			j = j - 1;
		}

		array[j+1] = key;
	}
}

// Quick Sort
int partition(int array[], int low, int high){
	int pivot = array[high];
	int i = low;
	int j;

	for(j = low; j <= high - 1; j++){
		if(array[j] < pivot){
			swap(&array[i], &array[j]);
			i++;
		}
	}

	swap(&array[i], &array[high]);

	return i;
}

void quick_sort_recursive(int array[], int low, int high){
	if(low < high){
		int pivot_index = partition(array, low, high);

		quick_sort_recursive(array, low, pivot_index - 1);
		quick_sort_recursive(array, pivot_index + 1, high);
	}
}
// array[] --> Array to be sorted,
// low --> Starting index,
// high --> Ending index 

void quick_sort(int array[], int length){
	quick_sort_recursive(array, 0, length-1);
}
