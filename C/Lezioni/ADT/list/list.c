#include<stdlib.h>

#include "item.h"
#include "list.h"

struct list {
    int size;
    struct node *head;
};

struct node {
    Item value;
    struct node *next;
};

// Create
List newList(){
    List list = malloc(sizeof(struct list));

    list->size = 0;
    list->head = NULL;

    return list;
}

// Auxiliary
/* 0 = is empty
   1 = is not empty*/
int isEmpty(List list){
    if(list->size == 0) {
        return 0;
    } else {
        return 1;
    }
}

int getSize(List list){
    return list->size;
}

Item getHead(List list){

    if(isEmpty(list)){
        return NULL;
    }

    return list->head->value;
}

// Print
void printList(List list){
    struct node *current;

    for(current = list->head; current != NULL; current = current->next) {
        outputItem(current->value);
    }
}

// Add
void addHead(List list, Item item){
    struct node *node = malloc(sizeof(struct node));

    node->next  = list->head;
    node->value = item;

    list->head  = node;
    list->size++;
}
// int  addListPos (List list, Item, int);
// int  addListTail(List list, Item item);

// // Search
// Item searchListItem(List list, Item item, int *);

// Remove
Item removeHead(List list){

    if(isEmpty(list)){
        return NULL;
    }

    struct node *temporal_node = list->head;
    list->head = temporal_node->next;

    Item removedItem = temporal_node->value;

    free(temporal_node);
    list->size--;

    return removedItem;
}
// Item removeListItem(List list, Item item);
// Item removeListPos (List list, int position);

// // Reverse
// void reverseList  (List list);

// // Clone
// List cloneList    (List list);
// List cloneListItem(List list);
