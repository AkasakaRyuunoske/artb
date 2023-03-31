typedef struct Punto * Punto;

Punto creaPunto (double x, double y);

double ascissa  (Punto punto);
double ordinata (Punto punto);

double distanza   (Punto punto_1, Punto punto_2);
int coppieDistMinD(Punto point_array[], int length, double distanza_input);
double maxDistanza(Punto * array_punti, int length);

void spostaPunto(Punto punto, double deltaX, double deltaY);
Punto centroide  (Punto *punti, int length);

void riempiSequenza(Punto * array_punti, int length);