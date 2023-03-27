#include "item.h"
#include "list.h"

int main(int argc, char *argv[]){

	List list = newList();

	int i;

	for(i=0;i<5;i++) {
		Item item=inputItem();
		addHead(list, item);
	}

	removeHead(list);
	
	printList(list);

	return 0;
}