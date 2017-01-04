Midterm projectCode has been implemented mac os in java for the following1)cgCanvas.java
-Filled in code for methods translation ,scaling,rotate,clear, addpoly,draw poly.
-This program use Jama Matrix for multiplication of the passed in vertices which intern does scaling rotate translate and clear operations.It consists of polygon class which stores the x and y co-ordinates and number of vertices of each shape in a "HashMap".The Hashmap returns a unique id for each polygon passed.

2)Rasterizer.java
Its my old rasterizer file which was used in polygon filling assignment.It is used in polygon fill for this project as well. It takes in input vertices and total number of vertices and gives the resulted output.It also has a Class file name Store which store the objects of each edge and values ymin,ymax,x-value,slope.

3)Clipper.java
It consists code for clipping polygon.It takes input vertices and gives output vertices to draw polygon method in cgCanvas which call draw poly in Rasterizer for filling polygon.The clip polygon class checks for vertices and handles code for each intersection - both points of line inside, either of them is inside using the “intersect” method.
