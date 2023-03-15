#include <stdio.h>

// Code & Explanation: https://youtu.be/Tz7vBodZqo8
void insertion_sort(int array[], int length);

int main(){
    int array[] = {9, 1, 8, 3, 6, 4, 5, 7, 0};

    insertion_sort(array, 9);

    for (int i = 0; i < 9; ++i)
    {
        printf("array[%d] = %d \n", i, array[i]);
    }

    return 0;
}

void insertion_sort(int array[], int length){
    int i;

    for(i = 1; i < length; i ++){

        int key = array[i];
        int j = i - 1;

        while (j >= 0 && array[j] > key){
            array[j + 1] = array[j];
            j = j - 1;
        }

        array[j+1] = key;
    }

}
