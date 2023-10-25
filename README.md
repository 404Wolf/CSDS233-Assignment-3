# CSDS233-Assignment-3
CWRU CSDS233 Assignment 3

## Implementation Notes
* To prevent adding duplicate elements to the tree, and to check whether an element already exists in the tree, I've added an auxiliary hashset that checks the existence of elements. This hashset is only used for this purpose, since it allows for O(1) lookups to see if elements exist.
* I've reimplemented my own methods for AVL tree rotations, placing these rotation methods directly into the node class definition.
* I've properly encapsulated all the fields of the AVL tree and AVL tree node, which previously were accessed directly.
* JUnit tests can be found under the `src/test` folder. These tests test various methods for the tree, and various different cases for each method.

## Notes
* The height is 1 less than the provided testcase's height because a node's height is formally defined to be the number of edges of the longest path from the root to the deepest leaf. The sample test seems to include the root node in the height computation, which is inconsistent with the definition of tree height. 