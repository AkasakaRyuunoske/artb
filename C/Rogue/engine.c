// Every thing was made according to this tutorial: https://dev.to/ignaoya/the-c-roguelike-tutorial-part-4-field-of-view-4l64
#include <rogue.h>

bool cursesSetup(void) {
	initscr();
	noecho();
	curs_set(0);

	if (has_colors()) {
		start_color();

		init_pair(VISIBLE_COLOR, COLOR_WHITE, COLOR_BLACK);
		init_pair(SEEN_COLOR, COLOR_BLUE, COLOR_BLACK);
		
		return true;
    
	} else {
    
		mvprintw(20, 50, "Your system doesn't support color. Can't start game!");
		getch();
    
		return false;
	}
  
}

void gameLoop(void) {
	int ch;

	makeFOV(player);
	drawEverything();

	while(ch = getch()) {
		if (ch == 'q') {
			break;
		}

		handleInput(ch);
		drawEverything();
	}
  
}

void closeGame(void) {
	endwin();
	free(player);
}
