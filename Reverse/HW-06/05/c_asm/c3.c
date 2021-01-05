#include <stdio.h>

int
gcd_iter(int u, int v) {
  if (u < 0) u = -u;
  if (v < 0) v = -v;
  if (v) while ((u %= v) && (v %= u));
  return (u + v);
}

int main()
{
    int a;
    a = gcd_iter(100, 20);
    printf("%d\n", a);
    return 0;
}
