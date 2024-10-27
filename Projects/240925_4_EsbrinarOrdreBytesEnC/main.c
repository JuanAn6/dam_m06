#include <stdio.h>
#include <stdlib.h>

int main ()
{
  unsigned int x = 0x76543210;
  char *c = (char*) &x;

  printf ("*c is: 0x%x\n", *c);
  if (*c == 0x10)
  {
    printf ("Underlying architecture is little endian. \n");
  }
  else
  {
     printf ("Underlying architecture is big endian. \n");
  }
  printf ("C/C++ sempre usa l'ordre de bytes de l'arquitectura de la CPU. \n");

  return 0;
}
