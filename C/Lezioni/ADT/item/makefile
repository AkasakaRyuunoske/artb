link : array utils item_int main
	gcc array.o utils.o item_int.o main.o -o array.exe

link_str : array utils item_str main
	gcc array.o utils.o item_str.o main.o -o array.exe

item_str :
	gcc -c item_str.c

item_int :
	gcc -c item_int.c
	
array :
	gcc -c array.c

utils :
	gcc -c utils.c

main : 
	gcc -c main.c

clean : 
	rm -f array.o utils.o item_int.o item_str.o main.o array.exe

