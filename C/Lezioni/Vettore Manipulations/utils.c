#include "utils.h"

void swap(int *a, int *b){
	*a = *a + *b;
    *b = *a - *b; 
    *a = *a - *b; 
}

// void swap(int *a, int *b){
// 	int temp=*a;
// 	*a=*b;
// 	*b=temp;
// }