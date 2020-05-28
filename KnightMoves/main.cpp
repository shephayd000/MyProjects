#ifndef KNIGHT_HEADER
#define KNIGHT_HEADER
#include <iostream>
#include <cstdlib>
#include <math.h>

int f(int num) { //Removable function
    return (num == 1) ? 3 : num - (2 * floor(num / 4));
}
int knightMoves(int xs, int ys) {

    //Because the pattern of numbers for # of knight moves is the same in all directions
    //We only want to look at the postive x-y area.

    //The pattern also repeats below the y = x function as above, so to simplify further
    //We make x the greater #, and y the lesser to only use below the y = x function

    int x = (xs >= ys) ? abs(xs) : abs(ys);
    int y = (xs >= ys) ? abs(ys) : abs(xs);

    //If X is even
    if (x % 2 == 0) {
        x = (x / 2);
        //Alternative for first path: 2 * (x - .5 * y - floor(.5 * x - .25 * y)), exception for 1
        if (x >= y) { return f(2 * x - 2 * y) + y; }
        else {
            return (y==2) ? 4 :  y-floor((y-x) / 3) * 2;
            //return () ? ( : (y - 2 * (floor((y-x) / 3)));
            //int sum = y - x;
            //for (int i = 0; i < x - 1; i++) { sum += i; }
            //return 3 + floor((sum + 1) / 2) - floor((sum + 2) / 4);
        }
    }

    //If X is odd
    else {
        //
        if (y == 0) { return f(x); }
        //Recursion should only go at most three levels deep
        else { return ((y % 2) == 0) ? knightMoves(x - 1, y - 2) + 1 : knightMoves(x + 2, y + 1) - 1; }
    }
}
int main()
{
    int x = 0;
    int y = 0;

    
    while (x != -1) {

        std::cout << "Welcome to Knight Moves Calculator!\n";
        std::cout << "Please input the x, y coordinates: \n";

        std::cin >> x;
        std::cin >> y;

        int steps = knightMoves(x, y);

        std::cout << "Number of steps is: " << steps << std::endl << std::endl;
    }
    
}
#endif
