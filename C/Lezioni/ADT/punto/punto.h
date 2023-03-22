typedef struct punto * Punto;

Punto newPunto (double x, double y);
double getX    (Punto punto);
double getY    (Punto punto);
double distance(Punto punto_1, Punto punto_2);