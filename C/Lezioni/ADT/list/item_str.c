#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "item.h"

#define STR_LEN 100

Item inputItem()
{
	char *ptr_char;
	
	ptr_char = malloc((STR_LEN + 1) * sizeof(char));
	
	if (ptr_char == NULL)
	{
		printf("\nERRORE: ALLOCAZIONE FALLITA");
		exit(EXIT_FAILURE);
	}
	
	scanf("%s", ptr_char);
	
	return ptr_char;
}

void outputItem(Item val)
{
	char *ptr_char;
	
	ptr_char = val;
	
	printf(" %s ", ptr_char);
}

int cmpItem(Item val1, Item val2)
{
	char *ptr_val1, *ptr_val2;
	
	ptr_val1 = val1;
	ptr_val2 = val2;
	
	return strcmp(ptr_val1, ptr_val2);
}