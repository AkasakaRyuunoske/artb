#include "item.h"

typedef struct list *List;

// Create
List newList();

// Auxiliary
int  isEmpty (List list);
int  getSize (List list);
Item getHead (List list);

// Print
void printList (List list); 

// Add
void addHead    (List list, Item);
int  addListPos (List list, Item, int);
int  addListTail(List list, Item);

// Search
Item searchListItem(List list, Item item, int *);

// Remove
Item removeHead    (List list);
Item removeListItem(List list, Item item);
Item removeListPos (List list, int position);

// Reverse
void reverseList  (List list);

// Clone
List cloneList    (List list);
List cloneListItem(List list);
