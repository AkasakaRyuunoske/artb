/**
 * Contains all functionality needed to work with Arrays (Only int for now)
 * 
 * @Akasaka
 * 
 * */

//TODO Search + Binary Search
//TODO input arrays from file
//TODO testing with files, oracles n voidrays
//TODO compare arrays

/* I/O Arrays */
int * input_int_array_dyn (int length);

void print_array          (int length, int array[]);
void print_array_less_info(int length, int array[]);

/* I/O elements into array */
void input_int_elements(int length, int * array);
 
/* Add / Delete / Update By element */
void add   (int * length, int array[], int element, int position);
void delete(int * length, int array[], int position);
void update(int   length, int array[], int position, int element);

/* Add / Delete / Update By Index */

int min(int length, int array[]);
int max(int length, int array[]);

/* Search / Binary Search */
int indexOf(int length, int *array, int element);

/* Add / Sub / Mul all elements of array together */
int sum_all_elements(int length, int array[]);
int sub_all_elements(int length, int array[]);
int mul_all_elements(int length, int array[]);

/* Add / Sub / Mul 2 arrays together */
int * sum_two_arrays(int length, int array_a[], int array_b[]);
int * sub_two_arrays(int length, int array_a[], int array_b[]);
int * mul_two_arrays(int length, int array_a[], int array_b[]);

/* SORTING */
//easy ones
void buble_sort    (int length, int array[]);
void selection_sort(int length, int array[]);
void insertion_sort(int length, int array[]);

// Quick Sort
int  partition           (int array[], int low, int high);
void quick_sort          (int array[], int length);
void quick_sort_recursive(int array[], int low, int high);

// Merge Sort
void merge_sorted_arrays (int array[], int left, int middle, int right);
void merge_sort_recursive(int array[], int left, int right);
void merge_sort          (int array[], int length);
