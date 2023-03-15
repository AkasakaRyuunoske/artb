// Every Thing made according to this tutorial: https://dev.to/ignaoya/the-c-roguelike-tutorial-part-4-field-of-view-4l64
#include <rogue.h>

void drawMap(void) {
	for (int y = 0; y < MAP_HEIGHT; y++) {
		for (int x = 0; x < MAP_WIDTH; x++) {
      
			if (map[y][x].visible) {
				mvaddch(y, x, map[y][x].ch | map[y][x].color);
        
			} else if (map[y][x].seen) {
				mvaddch(y, x, map[y][x].ch | COLOR_PAIR(SEEN_COLOR));
			} else {
				mvaddch(y, x, ' ');
			}
      
		}
	}
}

void drawEntity(Entity* entity) {
	mvaddch(entity->pos.y, entity->pos.x, entity->ch | entity->color);
}

void drawEverything(void) {
	clear();
	drawMap();
	drawEntity(player);
}
