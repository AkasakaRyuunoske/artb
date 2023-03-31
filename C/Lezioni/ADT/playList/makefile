link : list item_int main
	gcc list.o item_int.o main.o -o list.exe

link_str : list item_str main
	gcc list.o item_str.o main.o -o list.exe

item_str :
	gcc -c item_str.c

item_int :
	gcc -c item_int.c
	
list :
	gcc -c list.c

main : 
	gcc -c main.c

clean : 
	rm -f *.o *.exe

