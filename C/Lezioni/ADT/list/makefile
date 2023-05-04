int : list item_int main 
	gcc list.o item_int.o main.o -o list.exe

str : list item_str main 
	gcc list.o item_str.o main.o -o list.exe

item_int :
	gcc -c item_int.c

item_str :
	gcc -c item_str.c

main : 
	gcc -c main.c

list :
	gcc -c list.c

clean : 
	rm -f *.o *.exe

