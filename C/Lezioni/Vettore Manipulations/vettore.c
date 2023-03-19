#include <stdlib.h> // calloc/malloc/realloc/free/NULL
#include <stdio.h>  // Printf/Scanf

#include "utils.h"  // Swap

/**
 * Contains all functionality needed to work with Arrays (Only int for now)
 * 
 * @Akasaka
 * 
 * */

//TODO Search + Binary Search
//TODO input arrays from file
//TODO testing with files, oracles n voidrays
//TODO compare arrays
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

// Add / Sub / Mul all elements of array together
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

int mul_all_elements(int length, int array[]){
	int result = 1;
	int i;

	for(i = 0; i < length; i++){
		result *= array[i];
		printf("result on %d interation: %d\n", i, result);
	}

	return result;
}

int * sum_two_arrays(int length, int array_a[], int array_b[]){
	int result[length];
	int i;

	for(i = 0; i < length; i++){
		result[i] = array_a[i] + array_b[i];
	}

	return result;
}

// int sub_two_arrays(int length, int array_a[], int array_b[]){

// }

// int mul_two_arrays(int length, int array_a[], int array_b[]){

// }

// SORTING 

// Buble Sort

/**
 * Bubble Sort is the simplest sorting algorithm that works by repeatedly swapping the adjacent elements if they are in the wrong order.
 * 
 * This algorithm is not suitable for large data sets as its average and worst-case time complexity is quite high.
 * 
 * Source: https://www.geeksforgeeks.org/bubble-sort/
 * */
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

/**
 * 
 * Selection sort is a simple and efficient sorting algorithm that works by repeatedly selecting the smallest (or largest)
 * element from the unsorted portion of the list and moving it to the sorted portion of the list. 
 * The algorithm repeatedly selects the smallest (or largest) element from the unsorted portion of the list 
 * and swaps it with the first element of the unsorted portion. 
 * This process is repeated for the remaining unsorted portion of the list until the entire list is sorted.
 * 
 * Source: https://www.geeksforgeeks.org/selection-sort/
 * */

void selection_sort(int length, int array[]){
	int i;
	int index_minimum;

	for(i = 0; i < length -1; i++){
		index_minimum = indexOf(length, array, ( min(length-i, array + i) + i) ); // get index of the smallest element in the partition of array
		swap(&array[index_minimum], &array[i]); // swap two elements by index
	}
}

// Insertion Sort

/**
 * 
 * Insertion sort is an algorithm used to sort a collection of elements in ascending or descending order. 
 * The basic idea behind the algorithm is to divide the list into two parts: a sorted part and an unsorted part.
 * Initially, the sorted part contains only the first element of the list, while the rest of the list is in the unsorted part.
 * The algorithm then iterates through each element in the unsorted part, 
 * picking one at a time, and inserts it into its correct position in the sorted part.
 * To do this, the algorithm compares the current element with each element in the sorted part, starting from the rightmost element.
 * It continues to move to the left until it finds an element that is smaller (if sorting in ascending order)
 * or larger (if sorting in descending order) than the current element.
 * Once the correct position has been found, the algorithm shifts all the elements to the right of that position to make room
 * for the current element, and then inserts the current element into its correct position.
 * This process continues until all the elements in the unsorted part have been inserted into their correct positions in the sorted part,
 * resulting in a fully sorted list.
 * 
 * Source: https://www.geeksforgeeks.org/c-program-for-insertion-sort/
 * */

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
/**
 * Quick Sort:
 * 
 * It picks an element as a pivot and partitions the given array around the picked pivot. There are many different versions of quickSort that pick pivot in different ways. 
 *
 * Always pick the first(low) element as a pivot.
 * Always pick the last(high) element as a pivot (implemented below)
 * Pick a random element as a pivot.
 * Pick median as the pivot.
 * 
 * Quick Sort: https://youtu.be/Vtckgz38QHs
 * Quick Sort: https://youtu.be/0jDiBM68NGU
 * Quick Sort: https://www.geeksforgeeks.org/quick-sort/
 * 
 * */

/**
 * Partition:
 * 
 * The logic is simple, we start from the leftmost element and keep track of the index of smaller (or equal to) elements as i.
 * While traversing, if we find a smaller element, we swap the current element with arr[i]. Otherwise, we ignore the current element. 
 * */
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

// array[] --> Array to be sorted,
// low     --> Starting index,
// high    --> Ending index 
void quick_sort_recursive(int array[], int low, int high){

	if(low < high){
		int pivot_index = partition(array, low, high);

		quick_sort_recursive(array, low, pivot_index - 1);  // cals same procedure on right and left part's of array
		quick_sort_recursive(array, pivot_index + 1, high);
	}

}

// It's just a wrapper func
void quick_sort(int array[], int length){
	quick_sort_recursive(array, 0, length-1);
}


// Merge Sort

// Example visualization of the merge sort algorithm:
//
//          [38, 27, 43, 3, 9, 82, 10]
//                     /   \
//       [38, 27, 43, 3]   [9, 82, 10]
//        /         |         |      \
//   [38, 27]    [43, 3]   [9, 82]   [10]
//    /   |      /    |    /    \      |
// [38]  [27]  [43]  [3]  [9]   [82]  [10]
//    \  /       \   /     \     /     |
//   [27, 38]    [3, 43]   [9, 82]    [10]
//       \         /          \        /
//     [3, 27, 38, 43]        [9, 10, 82]
//           \                  /
//          [3, 9, 10, 27, 38, 43, 82]
//
// Source: https://youtu.be/LeWuki7AQLo

void merge_sorted_arrays(int array[], int left, int middle, int right){
    int left_length  = middle - left + 1;
    int right_length = right  - middle;

    int *temp_left_array  = calloc(left_length, sizeof(int));
    int *temp_right_array = calloc(right_length, sizeof(int));

    int i, j, k;

    // Copy partitions into temp arrays
    // Left partition
    for(i = 0; i < left_length; i++){
        temp_left_array[i] = array[left + i];
    }

    // Right partition
    for(j = 0; j < right_length; j++){
        temp_right_array[j] = array[middle + 1 + j];
    }

    // k keeps track position in final array
    // i keeps track of what index we are in the left array
    // j for right array
    for(i = 0, j = 0, k = left; k <= right; k++){
        if((i < left_length) && (j >= right_length || temp_left_array[i] <= temp_right_array[j])){ // CAREFUL HERE, THIS MUST BE (cond 1) && (cond2) NOT (cond 1) && cond 2 || cond 3
            array[k] = temp_left_array[i];
            i++;
        } else {
            array[k] = temp_right_array[j];
            j++;
        }
    }

    free(temp_left_array);
    free(temp_right_array);
}

void merge_sort_recursive(int array[], int left, int right){
	if (left < right) {

		int middle = left + (right - left) / 2;

		merge_sort_recursive(array, left, middle);      // left part of the array
		merge_sort_recursive(array, middle + 1, right); // right part of the array

		merge_sorted_arrays(array, left, middle, right);

	}
}


// Wrapper function
void merge_sort(int array[], int length){
	merge_sort_recursive(array, 0, length - 1);
}

