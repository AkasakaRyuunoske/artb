typedef struct song *Song;

Song initSong (char * title, char * artist, int duration);
char * title (Song song);
char * artist(Song song);
int duration (Song song);