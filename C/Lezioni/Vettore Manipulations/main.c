#include <stdio.h>
#include <stdlib.h>

#include "utils.h"
#include "vettore.h"

// Bakit hindi mo sabukan bago mo sabihin na hindi mo kaya?
// Oo, Mas madaling sabihin kagsa ganin

int main(int argc, char * argv []){
	int array[] = {5, 2, 1, 4, 5, 10, 7, 8, 11, 0};
	int length = 10;

	printf("Min : %d\n", min(length, array));
	printf("max : %d\n", max(length, array));

	printf("Pos of min : %d\n", indexOf(length, array, min(length, array)));
	printf("Pos of max : %d\n", indexOf(length, array, max(length, array)));

	printf("Result of sums: %d\n", sum_all_elements(length, array));
	printf("Result of subs: %d\n", sub_all_elements(length, array));

	print_array(length, array);
 
	quick_sort(array, length);
	printf("array after buble sort\n");

	print_array(length, array);

	return 0;
}