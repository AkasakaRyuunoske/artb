#include <stdio.h>
#include <stdlib.h>

#include "utils.h"
#include "vettore.h"

// Bakit hindi mo sabukan bago mo sabihin na hindi mo kaya?
// Oo, Mas madaling sabihin kagsa ganin

// TODO Provide interface(CLI)
int main(int argc, char * argv []){
	int array_a[] = {5, 2, 1, 4, 5, 10, 7, 8, 11, 1, 12, 32, 2, 4, 5, 6, 1, 3, 7, 6, 72, 23, 21, 17, 19, 8, 8, 42, 29, 1};
	int array_b[] = {5, 2, 1, 4, 5, 10, 7, 8, 11, 1, 12, 32, 2, 4, 5, 6, 1, 3, 7, 6, 72, 23, 21, 17, 19, 8, 8, 42, 29, 1};
	int length = 30;

	printf("Wellcome to %s!\n", argv [0]); // argv [0] --> name of .exe

	int * array   = input_int_array_from_file(argv[1], length);
	int * array_1 = input_int_array_from_file(argv[2], length);
	
    print_array_less_info(length, array);
    print_array_less_info(length, array_1);

	printf("Result of compare arrays: %d\n", compare_int_arrays(array, array_1, length));

	// printf("Min : %d\n", min(length, array));
	// printf("max : %d\n", max(length, array));

	// printf("Pos of min : %d\n", indexOf(length, array, min(length, array)));
	// printf("Pos of max : %d\n", indexOf(length, array, max(length, array)));

	// printf("Result of sums: %d\n", sum_all_elements(length, array));
	// printf("Result of subs: %d\n", sub_all_elements(length, array));

	// printf("Result of compare arrays: %d\n", compare_int_arrays(array_a, array_b, length));

	// print_array(length, array);
 
	// quick_sort(array_a, length);
	// merge_sort(array_a, length);
	// buble_sort(array_a, length);
	// selection_sort(array_a, length);
	// insertion_sort(array_a, length);

	// print_array_less_info(length, array_a);
	// print_array_less_info(length, array_b);

	// int *result = sum_two_arrays(length, array_a, array_b);

	// print_array_less_info(length, result);

	free(array);
	free(array_1);
	//free(result);
	return 0;
}