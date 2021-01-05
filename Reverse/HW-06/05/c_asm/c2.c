#include <stdio.h>

int minimum(int a, int b) {
    int r;
    if (a < b) {
        r = a;
    } else {
        r = b;
    }
    return r;
}

int main()
{
	printf("%d\n", minimum(100, 20));
	return 0;
}
