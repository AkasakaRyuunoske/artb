#include <stdio.h>
#include <stdlib.h>

#include "item.h"

Item inputItem()
{
	int *ptr_int;
	
	ptr_int = malloc(sizeof(int));
	
	if (ptr_int == NULL)
	{
		printf("\nERRORE: ALLOCAZIONE FALLITA");
		exit(EXIT_FAILURE);
	}
	
	scanf("%d", ptr_int);
	
	return ptr_int;
}

void outputItem(Item val)
{
	int *ptr_int;
	
	ptr_int = val;
	
	printf(" %d ", *ptr_int);
}

int cmpItem(Item val1, Item val2)
{
	int *ptr_val1, *ptr_val2;
	
	ptr_val1 = val1;
	ptr_val2 = val2;
	
	return *ptr_val1 - *ptr_val2;
}