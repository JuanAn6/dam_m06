#include <stdio.h>
#include <stdlib.h>

int main()
{
    FILE *f;
    f = fopen("..\\240919_1_Gestio_FileInputStream_FileOutputStream\\enters100.bin","rb");
    if (f==NULL) {
        printf ("No es pot obrir el fitxerA");
        return 0;
    }
    printf("Ha pogut obrir el fitxer\n");
    int aux;    // Per omplir-la amb el valor llegit
    int q = 0;  // Per comptar quants valors hem llegit
    char tipus = 'i';   // Semàfor per saber què he de llegir (int o short)
    fread(&aux, sizeof (int), 1, f);
    while (!feof(f)) {
        q++;
        printf("%d ", aux);
        fread(&aux, sizeof (int), 1, f);
    }
    printf("\nHem recuperat %d valors\n", q);
    fclose(f);
    return 0;
}
