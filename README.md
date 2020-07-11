# IUT-Pacman

Pac-Man game in Java.

## Context

This is a second semester project. Its objective is to create a game from a small game framework and its design. Only the code is available here.

## Main tools used

* ``.map`` file to recreate the map by reading it in the program
* graph and Floyd–Warshall algorithm

## Limits 

The missing elements compared to the original game are : 

* personalized ghost movements (here, they move randomly while they all have a different way of moving in the original game)
* levels and fruit

```
__________________|      |____________________________________________
     ,--.    ,--.          ,--.   ,--.
    |oo  | _  \  `.       | oo | |  oo|
o  o|~~  |(_) /   ;       | ~~ | |  ~~|o  o  o  o  o  o  o  o  o  o  o
    |/\/\|   '._,'        |/\/\| |/\/\|
__________________        ____________________________________________
                  |      |
```