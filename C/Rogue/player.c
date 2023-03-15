// Evevry thing was made according to this tutorial: https://dev.to/ignaoya/the-c-roguelike-tutorial-part-4-field-of-view-4l64
#include <rogue.h>


Entity* createPlayer(Position start_pos) {
	Entity* newPlayer = calloc(1, sizeof(Entity));

	newPlayer->pos.y = start_pos.y;
	newPlayer->pos.x = start_pos.x;
	newPlayer->ch = '@';
	newPlayer->color = COLOR_PAIR(VISIBLE_COLOR);

	return newPlayer;
}

void handleInput(int input) {
	Position newPos = { player->pos.y, player->pos.x };

	switch(input) {
		//move up
		case 'w':
			newPos.y--;
			break;
      
		//move down
		case 's':
			newPos.y++;
			break;
      
		//move left
		case 'a':
			newPos.x--;
			break;
      
		//move right
		case 'd':
			newPos.x++;
			break;
      
		default:
			break;
	}

	movePlayer(newPos);
}

void movePlayer(Position newPos) {
  
	if (map[newPos.y][newPos.x].walkable) {
		clearFOV(player);
		player->pos.y = newPos.y;
		player->pos.x = newPos.x;
		makeFOV(player);
	}
  
}

