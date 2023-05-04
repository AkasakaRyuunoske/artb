#include "array.h"
#include "item.h"

#define N 10

int main(){
	Item array[N];

	int length = 5;
	
	inputArray(array, length);
	
	bubbleSort(array, length);
	
	printArray(array, length);

	return 0;
}