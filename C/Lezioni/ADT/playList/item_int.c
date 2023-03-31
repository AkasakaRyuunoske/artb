#include "item.h"
#include <stdlib.h>
#include <stdio.h>

Item inputItem(){
	int *p=malloc(sizeof(int));
	scanf("%d", p);

	return p;
}

void outputItem(Item a){
	int *p=a;
	printf("%d ", *p);
}

int cmpItem(Item a, Item b){
	int *p1=a;
	int *p2=b;

	return (*p1-*p2);
	
}
