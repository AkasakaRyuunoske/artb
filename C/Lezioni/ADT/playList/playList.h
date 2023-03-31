#include "item-song.h"

typedef struct playList *PlayList;

PlayList createPlayList(char * name);

void addSong      (PlayList playList, Song song);
void removeSong   (PlayList playList, char * name);
void sortPlayList (PlayList playList);
void printPlayList(PlayList playList);