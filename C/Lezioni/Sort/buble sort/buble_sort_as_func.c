#include <stdio.h>

void buble_sort(int * array, int length);
void swap(int *a, int *b);

int main(void){
	int array[100];
	int length;
	int i, j;

	printf("Enter array length: \n");
	scanf("%d", &length);

	for (i = 0; i < length; i++) {
		printf("Enter value at %d: ", i);
		scanf("%d", &array[i]);
	}
	
	buble_sort(array, length);

	printf("Result of buble sorting: \n");
	for(i = 0; i < length; i++){
		printf("Value at post: %d is : %d \n", i, array[i]);
	}
	return 0;
}

void swap(int *a, int *b){
	*a = *a + *b;
    *b = *a - *b; 
    *a = *a - *b; 
}

void buble_sort(int * array, int length){
	int i, j;

	for (i = 0; i < length -1; i++) {
		for(j = 0; j < length - i -1; j ++){
			if (array[j] > array[j+1]) {
				swap(&array[j], &array[j+1]);				
			}
		}
	}
}
