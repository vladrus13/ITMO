#include <stdio.h>

int
is_even(int num) {
    int rem = num % 2;
    switch (rem) {
        case 0:
            return 1;
            break;
        case 1:
            return 0;
            break;
    }
}

int main()
{
    int a = 10;
    printf("%d\n", is_even(a));
    return 0;
}
