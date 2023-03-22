#include <stdio.h>

int  partition           (int array[], int low, int high);
void quick_sort          (int array[], int length);
void quick_sort_recursive(int array[], int low, int high);

void swap(int * a, int * b);

void print_array(int length, int array[]);

int main(void){
	int array_a[] = {5, 2, 1, 4, 5, 10, 7, 8, 11, 1, 12, 32, 2, 4, 5, 6, 1, 3, 7, 6, 72, 23, 21, 17, 19, 8, 8, 42, 29, 1};
	int length = 30;
	
	printf("Before Quick Sort: \n");
	print_array(length, array_a);

	quick_sort(array_a, length);

	printf("After Quick Sort: \n");
	print_array(length, array_a);

	return 0;
}

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

void swap(int * a, int * b){
	int temp = *a;
	*a = *b;
	*b = temp;
}

// Print Array
void print_array(int length, int array[]){
	int i;

	for(i = 0; i < length; i++){
		printf("array[%d] = %d\n", i, array[i]);
	}
}
