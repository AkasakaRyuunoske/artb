//Basic buble sort implementation in C
#include <stdio.h>

// void buble_sort(int * array, int length);
void swap(int *a, int *b);

int main(void){
	int array[100];
	int length;
	int i, j;
	// int swap;

	printf("Enter array length: \n");
	scanf("%d", &length);

	for (i = 0; i < length; i++) {
		scanf("%d", &array[i]);
	}

	for (i = 0; i < length -1; i++) {
		for(j = 0; j < length - i -1; j ++){
			if (array[j] > array[j+1]) {
				swap(&array[j], &array[j+1]);				
			}
		}
	}

	printf("Result of buble sorting: \n");
	for(i = 0; i < length; i++){
		printf("%d\n", array[i]);
	}
	return 0;
}

void swap(int *a, int *b){
    *a = *a + *b;
    *b = *a - *b; 
    *a = *a - *b; 
}
