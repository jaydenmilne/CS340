## Graphics 

### The map graphics are a bit complicated so here are some notes. 
* `InfinityMapSVG` is the master copy of just the map
* `map-w-background` is the background with the map superimposed on top. The canvas of the background has been sized exactly the same as the map so it will position correctly. This file should be changed first when changing items on the background. 
    * If the map is moved relative to the background, the canvas will have to be resized relative to the map.
* `map-background` is map-w-background with the map layer removed. Note that changes should be made in `map-w-background` and saved over `map-background`, not the other way around. 
* The file `map-background` in the assets folder is a plain svg, some of the inkscape tooling is removed to decrease the download size.
* `route-map` is `InfinityMapSVG` with the cities & shields removed and all the routes changed to vibranium color.