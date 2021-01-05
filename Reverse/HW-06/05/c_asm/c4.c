#include <stdio.h>

int find_elem(int *array, int size, int elem) {
    for (int i = 0; i < size; i++) {
        if (array[i] == elem)
            return i;
    }
    return -1;
}

int main()
{
    int a[10] = {1,2,3,4,5,6,7,8,9,0};
    int index = find_elem(a, 10, 6);
    printf("%d\n", index);
    return 0;
}
