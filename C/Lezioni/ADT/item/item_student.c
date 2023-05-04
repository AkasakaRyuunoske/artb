#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#include "item.h"

#define MAX 20

typedef struct Student{
	char *name[MAX];
	int matricola;
}

Item inputItem(){
	char * p = malloc(sizeof(Student));

	scanf("%d", p->matricola);

	return p;
}

void outputItem(Item a){
	char *p=a;
	printf("%s ", p);
	
}
int cmpItem(Item a,Item b){
	char * p1 = a;
	char * p2 = b;

	return strcmp(p1,p2);
}