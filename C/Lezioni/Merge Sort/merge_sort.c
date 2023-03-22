#include <stdio.h>
#include <stdlib.h>

void merge_sorted_arrays (int array[], int left, int middle, int right);
void merge_sort_recursive(int array[], int left, int right);
void merge_sort          (int array[], int length);

void swap(int * a, int * b);

void print_array(int length, int array[]);

int main(void){
	int array_a[] = {5, 2, 1, 4, 5, 10, 7, 8, 11, 1, 12, 32, 2, 4, 5, 6, 1, 3, 7, 6, 72, 23, 21, 17, 19, 8, 8, 42, 29, 1};
	int length = 30;
	
	printf("Before Merge Sort: \n");
	print_array(length, array_a);

	merge_sort(array_a, length);

	printf("After Merge Sort: \n");
	print_array(length, array_a);

	return 0;
}

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
