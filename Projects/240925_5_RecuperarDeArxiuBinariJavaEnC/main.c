#include <stdio.h>
#include <stdlib.h>

int main(int argc, char** argv) {
    FILE *f = fopen("..\\240919_1_Gestio_FileInputStream_FileOutputStream\\enters100.bin","rb");
    if (f == NULL) {
        printf("No es pot obrir el fitxer");
        return 0;
    }
    printf ("S'ha pogut obrir el fitxer\n");
    int aux;
    int q = 0;
    char buffer[4]; // Per llegir els bytes corresponents a un int o a un short
    fread(buffer, sizeof (int), 1, f);
    while (!feof(f)) {
        q++;
        aux = (int) buffer[3] | (int) buffer[2] << 8 | (int) buffer[1] << 16 | (int) buffer[0] << 24;
        printf("%d ", aux);
        fread(buffer, sizeof (int), 1, f);
    }
    printf("\nHem recuperat %d valors\n", q);
    fclose(f);
    return 0;
}

