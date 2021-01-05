#include <stdio.h>

int main(int argc, char *argv[])
{
    int a[10] = {0,1,2,3,4,5,6,7,8,9};

    for (int i = 0; i < 10; i++) {
        for (int j = 0; j < 10-i; j++) {
            if (a[j] <= a[j+1]) {
                int t = a[j];
                a[j] = a[j+1];
                a[j+1] = t;
            }
        }
    }

    for (int i = 0; i < 10; i++) {
        printf("%d ", a[i]);
    }

    return 0;
}
