// Example program
#include <iostream>
#include <cstdlib>
#include <math.h>
#ifndef KNIGHT_HEADER
#define KNIGHT_HEADER

int f(int num){
 int comp = floor(num/4);
 return (num == 1) ? 3 : num - (2 * comp);    
}
int knightMoves(int xs, int ys){
    int x = (xs >= ys) ? abs(xs) : abs(ys);
    int y = (xs >= ys) ? abs(ys) : abs(xs);
    
    if(x % 2 == 0){
     x = (x / 2);
     int diff = x - y;
     return (x >= y) ? (f(2 * diff) + y) : (y- 2 * (floor((y-(x)) / 4)));
    }
    else{
        if(y == 0){ return f(x); }
        else{ return ((y % 2) == 0) ? knightMoves(x-1, y-2) + 1 : knightMoves(x+2, y+1) - 1;   }
    }
}

int main()
{
    int x;
    int y;
    
  std::cout << "Welcome to Knight Moves Calculator!\n";
  std::cout << "Please input the x, y coordinates: \n";
  std::cin >> x;
  std::cin >> y;
  
  int steps = knightMoves(x, y);
  
  std::cout << "Number of steps is: " << steps;
}

#endif
