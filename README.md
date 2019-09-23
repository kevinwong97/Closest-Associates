# Closest-Associates
Task A: Implement the Graph Representations and their Operations (8 marks)
In this task, you will implement the
directed
,
weighted
graph using the
adjacency  list
and
incidence
matrix
representations.  Each representations will be implemented by a data structure.  Your imple-
mentation should support the following operations:
•
Create an empty directed graph (implemented as a constructor that takes zero arguments).
•
Add a vertex to the graph.
•
Add an edge to the graph.
•
Get the weight of an edge in the graph.
•
Update weight of edge in the graph.
•
Delete a vertex from the graph.
•
Compute the k-nearest in-neighbours of a vertex in the graph.
•
Compute the k-nearest out-neighbours of a vertex in the graph.
•
Print out the set of vertices of the graph.
•
Print out the set of edges and their weights of the graph.
Data Structure Details
Graphs  can  be  implemented  using  a  number  of  data  structures.   You  are  to  implement  the  graph
abstract data type using the following data structures:
•
Adjacency list, using an array of linked lists.
•
Incidence matrix, using a 2D array (an array of arrays).
For the above data structures, you must program your own implementation, and not use the LinkedList
or Matrix type of data structures in java.utils or any other libraries.  You must implement your own
nodes  and  methods  to  handle  the  operations.   If  you  use  java.utils  or  other  implementation  from
libraries,  this  will  be  considered  as  an  invalid  implementation  and  attract  0  marks  for  that  data
structure.  The only exception is if you choose to implement a map of vertex labels to a row or column
index for the incidence matrix, you may use one of the existing Map classes to do this.


