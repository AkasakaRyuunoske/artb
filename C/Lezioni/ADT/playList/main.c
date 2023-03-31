#include "item.h"
#include "list.h"
#include <stdio.h>


int main(){

	List list = newList();
	int i, pos;
	Item el, search, stampa;
	Item removedItem;
	
	for (i = 0; i < 5; i++){
		el = inputItem();
		addHead(list, el);
	}

	printList(list);
	printf("\n");
	scanf("%d", &pos);
	//stampa = searchListItem(list, search, &pos);
	stampa = removeListPos(list, pos);
	if (stampa == NULL)
		printf("Errore, item non trovato\n");
	else
	{
		printf("Item ");
		outputItem(stampa);
		printf("Eliminato\n");
	}
	printList(list);
	printf("\n");
	return 0;
}