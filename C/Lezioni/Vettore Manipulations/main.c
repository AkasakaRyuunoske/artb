#include <stdio.h>
#include <stdlib.h>

#include "utils.h"
#include "vettore.h"

// Bakit hindi mo sabukan bago mo sabihin na hindi mo kaya?
// Oo, Mas madaling sabihin kagsa ganin

int main(int argc, char * argv []){
	int array_a[] = {5, 2, 1, 4, 5, 10, 7, 8, 11, 1, 12, 32, 2, 4, 5, 6, 1, 3, 7, 6, 72, 23, 21, 17, 19, 8, 8, 42, 29, 1};
	int array_b[] = {5, 2, 1, 4, 5, 10, 7, 8, 11, 1, 12, 32, 2, 4, 5, 6, 1, 3, 7, 6, 72, 23, 21, 17, 19, 8, 8, 42, 29, 1};

	int length = 30;

	// printf("Min : %d\n", min(length, array));
	// printf("max : %d\n", max(length, array));

	// printf("Pos of min : %d\n", indexOf(length, array, min(length, array)));
	// printf("Pos of max : %d\n", indexOf(length, array, max(length, array)));

	// printf("Result of sums: %d\n", sum_all_elements(length, array));
	// printf("Result of subs: %d\n", sub_all_elements(length, array));

	// print_array(length, array);
 
	merge_sort(array_a, length);
    printf("Sorted: ");

	print_array(length, array_a);

	int result[length] = sum_two_arrays(length, array_a, array_b);

	for(int i = 0; i < length; i++){
		printf("result at[%d]: %d", i, result[i]);
	}



	return 0;
}