/**
 * Contains all functionality needed to work with Arrays (Only int for now)
 * 
 * @Akasaka
 * 
 * */

//TODO buble, insertion, selection, quick, merge SORTS
//TODO Search + Binary Search
//TODO input arrays from file
//TODO testing with files, oracles n voidrays
//TODO compare arrays
//TODO Add/sub/mul/div all elements of array together
//TODO add/sub/mul/div two arrays

// I/O Arrays
int* input_int_array_dyn(int length);

void print_array(int length, int array[]);

// I/O elements into array
void input_int_elements(int length, int * array);
 
// Add / Delete / Update By element
void add   (int * length, int array[], int element, int position);
void delete(int * length, int array[], int position);
void update(int length, int array[], int position, int element);
// Add / Delete / Update By Index

int min(int length, int array[]);
int max(int length, int array[]);

// Search / Binary Search
int indexOf(int length, int *array, int element);

// Add / Sub / Mul / Div all elements of array together
int sum_all_elements(int length, int array[]);
int sub_all_elements(int length, int array[]);

// SORTING
void buble_sort    (int length, int array[]);
void selection_sort(int length, int array[]);
void insertion_sort(int length, int array[]);

void partition           (int array[], int low, int high);
void quick_sort          (int array[], int length);
void quick_sort_recursive(int array[], int low, int high);
