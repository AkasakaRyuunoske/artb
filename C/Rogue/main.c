// Every thing was made according to this tutorial: https://dev.to/ignaoya/the-c-roguelike-tutorial-part-4-field-of-view-4l64
#include <rogue.h>

const int MAP_HEIGHT = 25;
const int	MAP_WIDTH = 100;

Entity* player;
Tile** map;

int main(void) {
	Position start_pos;
	bool compatibleTerminal;

	compatibleTerminal = cursesSetup();

	if (compatibleTerminal) {
		srand(time(NULL));

		map = createMapTiles();
		start_pos = setupMap();
		player = createPlayer(start_pos);

		gameLoop();

		closeGame();
 } else {
    
		endwin();
	}

	return 0;
}
