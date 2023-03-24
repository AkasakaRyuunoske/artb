typedef struct Punto * Punto;

Punto newPunto (double x, double y);

double getX    (Punto punto);
double getY    (Punto punto);

void setX      (Punto punto, double newX);
void setY      (Punto punto, double newY);

double distance(Punto punto_1, Punto punto_2);
Punto add(Punto punto_a, Punto punto_b);

Punto movePunto(Punto punto, double deltaX, double deltaY);
Punto centroide(Punto *punti, int length);

void inputPunto(Punto punto);
void outputPunto(Punto punto);

Punto inputPunti(Punto *array_punti);

int compare_punto(Punto punto_a, Punto punto_b);