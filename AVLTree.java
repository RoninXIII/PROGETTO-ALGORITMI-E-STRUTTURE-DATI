
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

import java.util.Iterator;

import java.util.NoSuchElementException;
import java.util.SortedSet;
/**@author mariol96
 * Class of the AVL tree. An AVL tree is a binary search tree that implements the rotations to make the tree balanced.
 *  */
public class AVLTree<E extends Comparable<E>> {

	public class Node<E> {
		/** Value inside the node */
		private E data;
		/** Node from which the aiming node is generated */
		private Node<E> parent;

		/**
		 * Height of the node, in particular it refers to the height of the
		 * tree(or subtree) with the node as root.
		 */
		private int height;
		/** Left subtree */
		private Node<E> leftChild;
		/** Right subtree */
		private Node<E> rightChild;

		/**
		 * Creates a node with a value inside it.
		 * 
		 * @param value
		 *            the specified value that will be inside the node.
		 * @throws IllegalArgumentException
		 *             if the specified element is null.
		 * 
		 */
		private Node(E value) {
			if (value == null)
				throw new IllegalArgumentException("Tentativo di inserire un elemento null nell'AVL");
			this.data = value;

		}

		/**
		 * String representation of the tree.
		 * 
		 * @return String representation of the tree.
		 */
		@Override
		public String toString() {
			return "Node [data=" + data + " Fs=" + leftChild + " Fd= " + rightChild + "]";
		}

	}

	/**
	 * String representation of the tree.
	 * 
	 * @return String representation of the tree.
	 */
	public String toString() {
		if (root == null)
			return "[ ]";
		return "[ " + root.toString() + "]";
	}

	/** Root of the tree */
	private Node<E> root;

	/** Creates an empty tree */
	public AVLTree() {
		root = null;
	}

	/**
	 * Creates a a tree with a root, a left child and a right child.
	 * 
	 * @param el
	 *            element of the root
	 * @param left
	 *            element of the left child
	 * @param right
	 *            element of the right child
	 */
	public AVLTree(E el, E left, E right) {
		root = new Node<E>(el);
		root.leftChild = new Node<E>(left);
		root.rightChild = new Node<E>(right);
		root.leftChild.parent = root;
		root.rightChild.parent = root;

	}

	/**
	 * It creates an AVL tree with an element
	 * 
	 * @param value
	 *            - Value of the tree's root
	 */
	public AVLTree(E value) {
		root = new Node<E>(value);
	}

	/**
	 * It makes the tree empty
	 */
	public void clear() {
		root = null;
	}

	/**
	 * It updates the parents of the node and the other node beneath the node
	 * 
	 * @param node
	 *            - node from which the update starts.
	 * @return the node with the updated parent.
	 * 
	 */
	private Node<E> setPadre(Node<E> node) {

		if (node.leftChild != null) {

			node.leftChild.parent = node;

			setPadre(node.leftChild);
		}
		if (node.rightChild != null) {

			node.rightChild.parent = node;

			setPadre(node.rightChild);
		}

		return node;
	}

	/**
	 * 
	 * It returns a value between -1 and 1, in case of the node is balanced; 2
	 * or -2 otherwise.The balancing is determined by the difference between the
	 * height of the left subtree and the height of the right subtree.
	 * 
	 * @param node
	 *            the node of which balancing is calculated.
	 * @return the balancing coefficient.
	 */
	private int bilanciamento(Node<E> node) {

		/*
		 * bil is a support variable that will assume the value of the balancing
		 * coefficient.
		 */
		int bil = 0;

		/* If a node doesn't have any child it is perfectly balanced; */
		if (node.leftChild == null && node.rightChild == null) {
			return 0;
		}

		/*
		 * if a node has just the right child, the null child has a height of 0;
		 * in this case the balancing coefficient will be for sure a negative
		 * number.
		 */
		if (node.leftChild == null) {
			bil = (0 - getHeight(node.rightChild) - 1);
			return bil;

		}
		/*
		 * if a node has just the left child, the null child has a height of 0;
		 * in this case the balancing coefficient will be for sure a positive
		 * number.
		 */
		if (node.rightChild == null) {
			bil = (getHeight(node.leftChild) - 0) + 1;
			return bil;
		}
		/*
		 * If both of the children are present, it calculates the balancing
		 * coefficient with the difference between the left subtree's height and
		 * the right subtree's height.
		 */
		if (node.leftChild != null && node.rightChild != null) {

			bil = (getHeight(node.leftChild) - getHeight(node.rightChild));
			return bil;

		}

		return bil;
	}

	/**
	 * It returns the height of the subtree with node as root.
	 * 
	 * @param node
	 *            the root of the subtree of which the height is calculated.
	 * @return the height of the subtree(or the tree) with node as root.
	 */
	private int getHeight(Node<E> node) {
		// If there isn't any element in the node
		if (node == null) {
			return -1;
		} else {
			/*
			 * The height of the subtree(or the tree in case node is the root of
			 * the tree itself) is calculated taking the max height between the
			 * right and the left side of node.
			 */
			return 1 + Math.max(getHeight(node.leftChild), getHeight(node.rightChild));
		}

	}

	/**
	 * Returns the height of the tree.
	 * 
	 * @return the height of the tree.
	 */
	public int height() {

		return getHeight(root);
	}

	/**
	 * Checks if the tree is empty or not.
	 * 
	 * @return true if the tree is empty,false if it is not.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns the minimum value of the tree.
	 * 
	 * @return the minimum value of the tree
	 * @exception NullPointerException
	 *                if the tree is empty
	 */
	public E minimum() {

		if (root == null)
			throw new NullPointerException("Error: no elements are present.");
		// Starts from the tree's root
		Node<E> node = root;
		// While the left child is present it goes down to the left.
		while (node.leftChild != null) {
			node = node.leftChild;
		}
		return node.data;
	}

	/**
	 * Returns the minimum value compared to node, this is useful to get the
	 * successor.
	 * 
	 * @param node
	 *            the node of which the minimum node has to be known.
	 * @return the node with the minimum value.
	 * @throws IllegalArgumentException
	 *             if the parameter is null.
	 */
	private Node<E> getMin(Node<E> node) {
		if (node == null)
			throw new IllegalArgumentException("Null elements not allowed!!!");
		while (node.leftChild != null) {
			node = node.leftChild;
		}

		return node;
	}

	/**
	 * Returns the maximum value of the tree.
	 * 
	 * @return the maximum value of the tree.
	 * @throws NullPointerException
	 *             if the tree is empty.
	 */
	public E maximum() {
		if (root == null)
			throw new NullPointerException("Error: no elements are present!!!");
		// Starts from the tree's root.
		Node<E> node = root;
		// While the right child is present it goes down to the right.
		while (node.rightChild != null) {
			node = node.rightChild;
		}
		return node.data;
	}

	/**
	 * Searches for the specified element inside the tree.
	 * 
	 * @param value
	 *            the value to search for.
	 * @param node
	 *            the root of the subtree in which the value is searched.
	 * @return true if the element has been found, false otherwise.
	 */
	private boolean ricercaEl(E value, Node<E> node) {
		boolean found = false;
		if (value == null) {
			throw new IllegalArgumentException("Null element are not allowed, please insert a valid value");
		}
		if (node == null) {
			throw new NullPointerException("There are no elments to search");

		}
		/*
		 * Compares the value to search for with the element of node: If value
		 * is less than node's element it goes down to the left(if the leftChild
		 * is present), it goes down to the right otherwise(if the rightChild is
		 * present). The x variable will be -1 or 1 depending on the fact that
		 * value is less or greater than node.
		 */
		int x = value.compareTo(node.data);

		if (x < 0 && node.leftChild != null) {
			return found = ricercaEl(value, node.leftChild);
		}
		if (x > 0 && (node.rightChild != null)) {
			return found = ricercaEl(value, node.rightChild);
		}
		/*
		 * If the x variable is 0 it means that value and node.data coincide, so
		 * the value searched for is found.
		 */
		if (x == 0) {

			System.out.println("Elemento: " + value + " trovato!");
			found = true;

		}
		/*
		 * If none of the previous cases have occurred the element searched for
		 * is not found.
		 */
		else {
			System.out.println("Elemento: " + value + " non trovato!");
			found = false;
		}
		return found;

	}

	/**
	 * Searches for the specified element.
	 * 
	 * @param el
	 *            the element to search for.
	 * @return true if the specified element is present inside the tree, false
	 *         otherwise.
	 * @throws IllegalArgumentException
	 *             if the parameter is null.
	 * @throws NullPointerException
	 *             if the tree is empty
	 */
	public boolean search(E el) {
		if (el == null)
			throw new IllegalArgumentException("Null elements not allowed!!!");
		if (root == null)
			throw new NullPointerException("Error: no elements are present!!!");
		return ricercaEl(el, root);
	}

	/**
	 * Returns the successor of the specified element.
	 * 
	 * @param el
	 *            the element of which the successor has to be known.
	 * @return the node that contains the successor of el.
	 * @throws IllegalArgumentException
	 *             if the parameter is null.
	 * @throws NullPointerException
	 *             if the tree is empty.
	 */
	public Node<E> getSuccessor(E el) {
		if (el == null)
			throw new IllegalArgumentException("Null elements not allowed!!!");
		if (root == null)
			throw new NullPointerException("No Elements are present!!!");
		return getSuccessor(el, root);
	}

	/**
	 * It searches for the successor.
	 * 
	 * @param el
	 *            the element of which the successor has to be known.
	 * @param node
	 *            the root of a subtree
	 * @return the successor of the specified element.
	 */
	private Node<E> getSuccessor(E el, Node<E> node) {

		if (el == null)
			throw new IllegalArgumentException("Null elements not allowed!!!");
		// it initializes a node that will take the value of the needed
		// successor.
		Node<E> succ = new Node<E>(el);

		/*
		 * It goes to the left or to the right depending on the element it
		 * searches for; if el is greater than node,it goes to the right(x is 1)
		 * and if el is less than node it goes to the left(x is -1)
		 * 
		 */
		int x = el.compareTo(node.data);

		if (x < 0) {
			return getSuccessor(el, node.leftChild);
		}
		if (x > 0) {
			return getSuccessor(el, node.rightChild);
		}
		// it founds the element of which it wants to know the successor.
		if (x == 0) {
			// if it wants to know the successor of the last element, the
			// successor
			// is null since there are no more element.
			if (node.data == maximum()) {
				return succ = null;
			}
			/*
			 * At this point there will be at 100% the node's successor,that
			 * coincide with the desired element. If node has a right child, its
			 * successor will be the minimum of its right subtree...
			 */
			if (node.rightChild != null) {

				return succ = getMin(node.rightChild);

			} else {
				/*
				 * ...otherwise succ will assume the value of its parent,then it
				 * creates another variable(y) that allows to compare node(the
				 * node at which you are aiming) with succ. In other words it
				 * compares the aiming node with its parent.
				 */
				succ = node.parent;

				int y = node.data.compareTo(succ.data);
				/*
				 * if succ(the parent of the aiming node) is grater than its
				 * child, the desired successor is succ.This kind of situation
				 * presents itself only if it wants to know the successor of an
				 * element that is a left child...
				 */
				if (y < 0)
					return succ;
				/*
				 * ...if not, climbs the tree until the aiming node stops to be
				 * a right child taking, therefore, the first parent that is a
				 * right child. So the taken parent is an ancestor of the
				 * specified element and so the desired element.
				 */
				while (succ != null && el == succ.rightChild.data) {

					el = succ.data;
					succ = succ.parent;
					/*
					 * There is a break to avoid the NullPointerException.
					 */
					if (succ.rightChild == null)
						break;
				}

			}
		}

		return succ;

	}

	/** Prints the tree */
	public void print() {
		stampa(root);
	}

	/**
	 * Prints the tree.
	 * 
	 * @param node
	 *            root of a specific subtree or of the tree itself.
	 */
	private void stampa(Node<E> node) {
		if (node == null) {

			System.out.println("Empty tree");

			return;
		} else {

			if (node.parent != null)
				System.out.println("\n Node: " + node.data + " Subtree height: " + getHeight(node) + " balancing: "
						+ bilanciamento(node) + " Parent: " + node.parent.data);
			else
				System.out.println("\n Node: " + node.data + " Tree height: " + getHeight(node) + " balancing: "
						+ bilanciamento(node) + " Parent: null");

			if (node.leftChild != null) {

				stampa(node.leftChild);
			}
			if (node.rightChild != null) {

				stampa(node.rightChild);
			}

		}

	}

	/**
	 * Add an element inside the AVL tree.This method does not allow
	 * duplicates,if it tries to add an element that already exists the method
	 * simply tell it to you with a message; further additions are possible.
	 * 
	 * @param el
	 *            the element to add
	 * @return true if the specified element has been correctly inserted,false
	 *         otherwise.
	 * 
	 * 
	 */

	public boolean insert(E el) {
		/*
		 * There is a call to the method that effectively does all the
		 * operations, after the called method has finished the tree will be
		 * "update". The try{} catch{} block are needed to permit additional
		 * insertions when it tries to add an existing element.
		 */
		try {

			root = inserisci(el, root);
			setPadre(root);
			return true;
		} catch (IllegalArgumentException e) {
			System.err.println("The element: " + el + " is already present \n");

			return false;
		}

	}

	/**
	 * Add an element inside the AVL tree. After the element is added,if
	 * necessary,there is the implementation of the rotations.
	 * 
	 * @param el
	 *            the element to add
	 * @param node
	 *            the root of a specific subtree(or tree).
	 * @return the updated tree
	 * 
	 * @throws IllegalArgumentException
	 *             if the specified element is already present.
	 */
	private Node<E> inserisci(E el, Node<E> node) throws IllegalArgumentException {

		/*
		 * If node(where the element will be inserted) is null, it will become
		 * the node with the specified element.
		 */
		if (node == null) {
			node = new Node<E>(el);
			if (node.equals(root)) {
				root.parent = null;
			}
		}
		/*
		 * It compares the element to add with node(not null); if el is smaller
		 * than node, it goes to the left, to the right otherwise.
		 */
		else if (el.compareTo(node.data) < 0) {
			node.leftChild = inserisci(el, node.leftChild);
			/*
			 * After returning from the recursion call and the element has been
			 * correctly added, it checks for the balancing coefficient of the
			 * nodes we got through , it means the balancing of the left
			 * subtrees(and the tree itself) with node as root. If the subtree
			 * is unbalanced(balancing coefficient=2) and the added node is a
			 * left child, a single rotation is used(also called LL rotation);
			 * otherwise a double rotation is used(also called RL rotation).
			 */
			if (bilanciamento(node) == 2) {
				if (el.compareTo(node.leftChild.data) < 0) {
					node = rotazioneSS(node);

				} else {
					node = rotazioneSD(node);

				}
			}
			/*
			 * If we are in a right subtree,the balancing coefficient of node is
			 * -2 and its right child is greater than its child, an RR rotation
			 * is used; if smaller than its child a LR rotation, also called
			 * double rotation with the right child.
			 */
			if (bilanciamento(node) == -2) {
				if (el.compareTo(node.rightChild.data) > 0) {
					node = rotazioneDD(node);
				} else {
					node = rotazioneDS(node);
				}
			}

			setPadre(node);
			/* If we are on the right of root */
		} else if (el.compareTo(node.data) > 0) {
			node.rightChild = inserisci(el, node.rightChild);

			if (bilanciamento(node) == -2) {
				if (el.compareTo(node.rightChild.data) > 0) {
					node = rotazioneDD(node);

				} else {
					node = rotazioneDS(node);

				}
			}
			if (bilanciamento(node) == 2) {
				if (el.compareTo(node.leftChild.data) < 0) {
					node = rotazioneSS(node);
				} else {
					node = rotazioneSD(node);
				}
			}
			setPadre(node);
		} else {
			throw new IllegalArgumentException();
		}
		/*
		 * Updates the height of the current node assigning to it the maximum
		 * height of the children.
		 */
		node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
		return node;
	}

	/**
	 * This method is used to add a left child to a tree(or a subtree)
	 * 
	 * @param node
	 *            root of a subtree.
	 * @param node2
	 *            the left child we have to add
	 */
	private void addLeftChild(Node<E> node, Node<E> node2) {
		/*
		 * Updates the pointer of parent of node2, it means the left child of
		 * the node we want to remove, and the pointer of node, it means the
		 * parent of the node we want to remove.
		 */
		node2.parent = node;

		node.leftChild = node2;

	}

	/**
	 * This method is used to add a right child to a tree(or a subtree).
	 * 
	 * @param node
	 *            root of a subtree
	 * @param node2
	 *            the right child we have to add
	 */
	private void addRightChild(Node<E> node, Node<E> node2) {
		/*
		 * Updates the pointer of parent of node2, it means the right child of
		 * the node we want to remove, and the pointer of node, it means the
		 * parent of the node we want to remove.
		 */
		node2.parent = node;

		node.rightChild = node2;

	}

	/**
	 * Delete a leaf.
	 * 
	 * @param el
	 *            the element we have to extract
	 * @param node
	 *            it is the root of the tree or a sub-tree
	 * @return true if the node has been successfully deleted, false otherwise.
	 */
	private boolean estrazioneNoFigli(E el, Node<E> node) {

		boolean deleted = false;
		// If the node we want to extract is the
		// root...
		if (node.equals(root)) {
			// We set it to null
			root = null;
			deleted = true;
		}
		/*
		 * Verifies if the node to extract is a right child or a left child;
		 * than you simply set it to null.
		 */
		if (node == node.parent.leftChild) {

			node.parent.leftChild = null;

			deleted = true;

		} else {
			node.parent.rightChild = null;

			deleted = true;
		}

		return deleted;

	}

	/**
	 * Method to extract a node with only one child.
	 * 
	 * @param el
	 *            - element of the node that will be extracted.
	 * @param node
	 *            - root of a specific subtree.
	 * @return true if the node has been successfully deleted, false otherwise.
	 */

	private boolean estrazioneUnFiglio(E el, Node<E> node) {
		boolean deleted = false;

		/*
		 * Creates a node that will take the value of the child different from
		 * null.
		 */
		Node<E> c = node.leftChild != null ? node.leftChild : node.rightChild;
		if (node.equals(root)) {
			node.data = c.data;
			if (c.equals(node.leftChild)) {
				node.leftChild = null;
			} else
				node.rightChild = null;

			deleted = true;
		}
		/*
		 * If node is a left child
		 */
		if (node == node.parent.leftChild) {

			addLeftChild(node.parent, c);
			deleted = true;

		} else {
			// if node is a right child.
			addRightChild(node.parent, c);
			deleted = true;

		}
		return deleted;

	}

	/**
	 * This method just deletes a node that has two children
	 * 
	 * 
	 * @param el
	 *            the node we want to extract
	 * @param node
	 *            the root of the tree or a sub-tree
	 * @throws Exception
	 *             if the specified element is not present.
	 * @return true if the node has been successfully deleted, false otherwise.
	 */
	private boolean estrazioneDueFigli(E el, Node<E> node) throws Exception {
		boolean deleted = false;

		/*
		 * Instanciate a new empty node and we assign to this one the value of
		 * the successive node we want to delete
		 */
		Node<E> succ = new Node<E>(el);

		succ = getSuccessor(el, node);
		/*
		 * We choose between the following methods in order to delete the
		 * successor: "estrazioneUnFiglio", "estrazioneNoFigli"
		 */
		estrazione(succ.data, node);
		/*
		 * We replace the node we want to delete with its successor
		 */
		node.data = succ.data;
		deleted = true;

		return deleted;

	}

	/**
	 * It removes a specific node that contains the specified element.
	 * 
	 * @param el
	 *            - element of the node that will be extracted.
	 * @return true if the node with the specified element has been successfully
	 *         extracted, false otherwise.
	 * @throws NullPointerException
	 *             if the tree is empty.
	 */

	public boolean removeNode(E el) {
		if (root == null)
			throw new NullPointerException("Error: Empty tree");
		if (el == null)
			throw new IllegalArgumentException("Null elements not allowed!!!");
		try {

			/*
			 * After deleting a node it climbs the tree, avoiding any aiming
			 * problem.
			 */
			root = estrazione(el, root);

			if (root.parent != null) {
				while (root.parent != null) {
					root = root.parent;
				}
			}

		} catch (Exception e) {
			System.err.println("\nThe element: " + el + " is not present.");
			return false;
		}
		return true;
	}

	/**
	 * "Auxiliary" method to extract a node.
	 * 
	 * @param el
	 *            - element of the node that will be extracted
	 * @param node
	 *            - node root of a subtree.
	 * @return the updated tree after the extraction.
	 * @throws Exception
	 *             if the specified element is not present.
	 * 
	 */
	private Node<E> estrazione(E el, Node<E> node) throws Exception {
		boolean deleted = false;

		int x = node.data.compareTo(el);
		if ((x < 0 || x > 0) && node.rightChild == null && node.leftChild == null)
			throw new Exception();

		if (x > 0) {
			estrazione(el, node.leftChild);

		}
		if (x < 0) {

			estrazione(el, node.rightChild);
		}
		/*
		 * We have to check the condition the node respects. It depends if it
		 * has 0, 1 or 2 children, calling the respective methods
		 */
		if (x == 0) {
			if (node.leftChild == null && node.rightChild == null) {
				deleted = estrazioneNoFigli(el, node);

			} else if (node.leftChild == null || node.rightChild == null) {
				deleted = estrazioneUnFiglio(el, node);

			} else {
				deleted = estrazioneDueFigli(el, node);
			}
		}
		/*
		 * Checks the balancing coefficient of the aiming node and makes the
		 * rotations.
		 */
		if (bilanciamento(node) == 2) {
			if (bilanciamento(node.leftChild) == 1)
				node = rotazioneSS(node);
			else
				node = rotazioneSD(node);

		}
		if (bilanciamento(node) == -2) {
			if (bilanciamento(node.rightChild) == -1) {
				node = rotazioneDD(node);

			} else {
				node = rotazioneDS(node);
			}
		}
		// updates the the parents.
		setPadre(node);
		// updates the parent of node's child.
		if (node.parent != null && deleted == false)
			if (node.data.compareTo(node.parent.data) < 0) {
				node.parent.leftChild = node;
			} else {
				node.parent.rightChild = node;
			}

		return node;

	}

	/**
	 * Rotates a subtree(or the tree) that causes the displacement with a single
	 * rotation to the left.
	 * 
	 * @param node
	 *            the node to rotate.
	 * @return the node that is the root of the updated subtree(or tree).
	 * 
	 */

	private Node<E> rotazioneSS(Node<E> node) {
		/*
		 * Create a node with the same value of its left-child node; then we
		 * assign to the left-child node the value of right-child node.
		 */
		Node<E> t = node.leftChild;

		node.leftChild = t.rightChild;
		// The right child of t(that is now the left child of the specified
		// node) becomes the specified node.
		t.rightChild = node;
		// update the parent of t, so it becomes the new subtree.
		t.parent = node.parent;

		node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
		t.height = Math.max(getHeight(t.leftChild), node.height) + 1;

		return t;

	}

	/**
	 * Rotates a subtree(or the tree) that causes the displacement with a single
	 * rotation to the right.
	 * 
	 * @param node
	 *            root of the subtree(or tree) to rotate.
	 * @return the node that is the root of the updated subtree(or tree).
	 */
	private Node<E> rotazioneDD(Node<E> node) {

		Node<E> t = node.rightChild;
		/*
		 * Create a node with the same value of its left-child node; then we
		 * assign to the left-child node the value of right-child node.
		 */
		node.rightChild = t.leftChild;
		t.leftChild = node;
		// update the parent of t, so it becomes the new subtree.
		t.parent = node.parent;

		node.height = Math.max(getHeight(node.leftChild), getHeight(node.rightChild)) + 1;
		t.height = Math.max(getHeight(t.leftChild), node.height) + 1;

		return t;

	}

	/**
	 * Rotates a subtree(or the tree) that causes the displacement with a double
	 * rotation to the left.
	 * 
	 * @param node
	 *            root of the subtree(or tree) to rotate.
	 * @return the node that is the root of the updated subtree(or tree).
	 * 
	 */
	private Node<E> rotazioneSD(Node<E> node) {
		// The left child of the node that has to be rotate does a single
		// rotation to the right, than updates the parent and the node itself
		// does a single rotation to the left.
		node.leftChild = rotazioneDD(node.leftChild);
		setPadre(node);
		return rotazioneSS(node);

	}

	/**
	 * Rotates a subtree(or the tree) that causes the displacement with a double
	 * rotation to the right.
	 * 
	 * @param node
	 *            root of the subtree(or tree) to rotate.
	 * @return the node that is the root of the updated subtree(or tree).
	 */
	private Node<E> rotazioneDS(Node<E> node) {
		// The right child of the node that has to be rotate does a single
		// rotation to the left, than updates the parent and the node itself
		// does a single rotation to the right.
		node.rightChild = rotazioneSS(node.rightChild);
		setPadre(node);
		return rotazioneDD(node);

	}

	/**
	 * Sorted set class. It uses an AVL tree as main(and unique) structure, so
	 * its methods are inherited too.
	 */
	public static class mySortedSet<T extends Comparable<T>> extends AVLTree<T> implements SortedSet<T> {

		/** Variable of the number of the elements inside the tree. */
		private int countNode;

		/** Constructs a set with an AVL tree as data structure */
		public mySortedSet() {
			super();

		}

		/**
		 * Construct a sorted set with an element.
		 * 
		 * @param value-
		 *            the value to insert
		 */
		public mySortedSet(T value) {
			super(value);
			countNode++;
		}

		/**
		 * Insert an element inside the sorted set.
		 * 
		 * @param el
		 *            the element to add.
		 * @return true if the element was successfully added, false otherwise.
		 */
		@Override
		public boolean add(T el) {
			boolean added = false;
			// Calls the insert method of the AVL tree
			added = insert(el);
			// If the element was successfully added updates the number of the
			// element inside the sorted set.
			if (added == true) {
				countNode++;
				return true;
			} else
				return false;

		}

		/**
		 * Adds all the elements in the specified collection to this set.
		 * Duplicate values are not allowed.
		 * 
		 * @param c
		 *            the collection from which the elements are taken.
		 * @return true if this set has been modified, so at least one element
		 *         was added, false otherwise
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean addAll(Collection<? extends T> c) {

			boolean added = false;
			int numberOfNodes = countNode;
			if (c == null) {
				return false;
			}
			if (c.isEmpty())
				return false;
			// Initialize a variable that takes the iterator of the specified
			// collection.
			Iterator<?> iterator = c.iterator();
			while (iterator.hasNext()) {
				// Initializes a variable that takes the next element of the
				// iterator as value.
				Object object = iterator.next();
				// If this set is empty simply add the element.
				if (this.isEmpty()) {
					add((T) object);
				} else {
					/*
					 * Initialize a node, because this sorted set contains
					 * nodes, with the first element of this set that it is none
					 * other than the minimum value of the tree.
					 */
					Node<T> node = super.getMin(super.root);
					/*
					 * Controls if the elements' type of this set and the one
					 * the specified collection are the same. Different types
					 * are not allowed.
					 */
					if (this.first().getClass() != object.getClass()) {
						throw new IllegalArgumentException("Operazione non valida");
					}
					/*
					 * If the element is not present the added variable becomes
					 * true, false otherwise.
					 */
					int x = ((T) object).compareTo(node.data);
					if (x < 0) {
						add((T) object);
					}
					if (x > 0) {
						add((T) object);
					}
					if (x == 0) {
						added = false;
					}
				}

			}
			if (countNode > numberOfNodes)
				added = true;
			return added;
		}

		/**
		 * Searches for the specified element.
		 * 
		 * @param o
		 *            the element to search.
		 * @return true if the element was found, false otherwise.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean contains(Object o) {
			boolean found = false;
			// Calls the search method of the AVL tree.
			found = search((T) o);
			return found;
		}

		/**
		 * Checks if this set contains all the elements of the specified
		 * collection.
		 * 
		 * @param c
		 *            the collection that contains the elements to search.
		 * @return true if this set contains all the element of the specified
		 *         collection, false otherwise.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean containsAll(Collection<?> c) {

			if (c == null) {
				return false;
			}
			if (c.isEmpty())
				return false;
			// Initialize a variable that takes the iterator of the specified
			// collection.
			Iterator<?> iterator = c.iterator();
			/*
			 * Initialize a node, because this sorted set contains nodes, with
			 * the first element of this set that it is none other than the
			 * minimum value of the tree.
			 */
			Node<T> node = super.getMin(super.root);
			while (iterator.hasNext()) {
				boolean contains = false;
				// Initializes a variable that takes the next element of the
				// iterator as value.
				Object object = iterator.next();
				/*
				 * Controls if the elements' type of this set and the one the
				 * specified collection are the same. Different types are not
				 * allowed.
				 */
				if (super.root.data.getClass() != object.getClass()) {
					throw new IllegalArgumentException("Error: the specified collection is of an invalid type");
				}

				while (contains == false) {
					/*
					 * Compares the element of the specified collection with one
					 * of this set.
					 */
					int x = ((T) object).compareTo(node.data);
					/*
					 * If the element of the specified collection is smaller
					 * than one of this set it means that the element is not
					 * present.
					 */
					if (x < 0) {

						return false;

					}
					if (x == 0) {
						contains = true;

					}
					/*
					 * If the element of the specified collection is greater
					 * than one of this set node take the value of its
					 * successor.
					 */
					if (x > 0) {
						node = super.getSuccessor(node.data);

					}
					/*
					 * if node is null it means that there is an element in the
					 * specified collection that is greater than this set.
					 */
					if (node == null)
						return false;
				}

			}

			return true;
		}

		/** Define an iterator to run(in order) the objects in the collection */
		@Override
		public Iterator<T> iterator() {

			return new treeIterator(super.root);
		}

		/**
		 * Remove an object inside the collection
		 * 
		 * @param o
		 *            the element to remove
		 * @return true if the element was successfully removed, false otherwise
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean remove(Object o) {
			boolean deleted = false;
			// Simply call the remove method of the AVL tree class
			deleted = removeNode((T) o);
			if (deleted == true)
				// Updates the number of elements
				countNode--;

			return deleted;
		}

		/**
		 * Removes from this set all of its elements that are contained in the
		 * specified collection.
		 * 
		 * @param c
		 *            the collection from which the element to remove are taken.
		 * @return true if at least one element from this set was removed.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public boolean removeAll(Collection<?> c) {
			if (c == null) {
				return false;
			}
			if (c.isEmpty())
				return false;
			// Checks if this set and the collection contain the same elements.
			// If so clear this set.
			if (c.containsAll(this)) {
				this.clear();
				countNode = 0;
				return true;
			}

			boolean removed = false;
			// Variable to control at the end of the method if an element was
			// removed.
			int numberOfNodes = countNode;
			// Initialize a variable that takes the iterator of the specified
			// collection.
			Iterator<?> iterator = c.iterator();

			while (iterator.hasNext()) {

				// Initializes a variable that takes the next element of the
				// iterator as value.
				Object object = iterator.next();
				/*
				 * Controls if the elements' type of this set and the one the
				 * specified collection are the same. Different types are not
				 * allowed.
				 */
				if (super.root.data.getClass() != object.getClass()) {
					throw new IllegalArgumentException("Operazione non valida");
				}

				// Simply call the remove method of the AVL tree because the
				// checks are made by that method.
				remove(object);

			}
			if (countNode < numberOfNodes)
				removed = true;

			return removed;
		}

		/**
		 * Removes from this set the elements that are not contained in the
		 * specified collection.
		 * 
		 * @param c
		 *            the collection that contains the elements to retain.
		 * @return true if this set has been modified, false otherwise.
		 */
		@Override
		public boolean retainAll(Collection<?> c) {
			/*
			 * Note: in this method the iterator is used just to verify the type
			 * of the two collections. This means that the elements inside the
			 * collection are not visited, in fact we just control if a
			 * specified element is present inside the collection; if not it
			 * will be eliminated.
			 */
			if (c == null) {
				return false;
			}
			if (c.isEmpty())
				return false;

			if (this.isEmpty())
				return false;

			boolean removed = false;
			// Variable to control at the end of the method if an element was
			// removed.
			int numberOfNodes = countNode;
			// Initialize a variable that takes the iterator of the specified
			// collection.
			Iterator<?> iterator = c.iterator();
			/*
			 * Initialize a node, because this sorted set contains nodes, with
			 * the first element of this set that it is none other than the
			 * minimum value of the tree.
			 */
			Node<T> node = super.getMin(super.root);
			// Initializes a variable that takes the next element of the
			// iterator as value.
			Object object = iterator.next();

			if (super.root.data.getClass() != object.getClass()) {
				throw new IllegalArgumentException("Operazione non valida");
			}
			while (true) {
				if (node == null)
					break;
				/*
				 * Checks if the specified collection contain the current
				 * element of the tree.If not it will be removed and will assume
				 * the value of its successor.
				 */
				if (c.contains(node.data) == false) {
					Node<T> succ = super.getSuccessor(node.data);

					removed = remove(node.data);
					if (succ == null)
						break;
					node = succ;
				} else {
					// If yes it will assume the value of its successor.
					node = super.getSuccessor(node.data);
				}

			}
			// Checks if this set has been modified.
			if (numberOfNodes > countNode)
				removed = true;
			return removed;
		}

		/** Returns the cardinality of this set. */
		@Override
		public int size() {

			return countNode;
		}

		/**
		 * Allocates a new array with the same size( and same elements) of this
		 * set.
		 * 
		 * @return a new array with the element of this set.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Object[] toArray() {
			// creates a new array with the size of this set( even if this set
			// is an array)
			Object[] arr = new Object[countNode];

			Iterator<?> iterator = this.iterator();
			int i = 0;
			// The iterator flows the elements that are sequentially added
			// inside the new array.
			while (iterator.hasNext()) {
				Object object = iterator.next();

				arr[i] = (T) object;
				i++;
			}

			return arr;
		}

		/**
		 * Return an array containing all of the elements of this set; if the
		 * specified array in which the elements are to be stored is big enough
		 * , it is returned; otherwise a new array is allocated with the same
		 * size of this set.
		 * 
		 * @param a
		 *            the array in which the element are added.
		 * @return an array containing all the elements of this set.
		 * @throws ArrayStoreException
		 *             if this set and the specified array don't contain
		 *             elements of the same type.
		 * 
		 * @throws IllegalArgumentException
		 *             if the specified array is null.
		 * 
		 *
		 */
		@SuppressWarnings("unchecked")
		@Override
		public <T> T[] toArray(T[] a) {
			Iterator<?> iterator = this.iterator();
			int i = 0;
			if (a == null) {
				throw new IllegalArgumentException("Error: the parameter is null!!");
			} else {
				try {
					// Checks if the specified array is big enough to contain
					// the element of this set.
					if (a.length < countNode) {
						// if not the specified array will become a new array
						// with the same type and the same size of this set
						a = (T[]) Arrays.copyOf(a, countNode, a.getClass());
						/*
						 * The specified array can contains the desired
						 * elements, so they are added sequentially until the
						 * last position of the array is reached.
						 */
						while (iterator.hasNext()) {
							if (a[i] == this.last()) {
								break;
							}
							Object object = iterator.next();

							a[i] = (T) object;
							i++;
						}
						/*
						 * If the specified array is big enough, simply add the
						 * element. Null element are present inside the array if
						 * it is bigger than this set.
						 */
					} else if (a.length == countNode || a.length > countNode) {
						while (iterator.hasNext()) {
							if (a[i] == this.last()) {
								break;
							}
							Object object = iterator.next();

							a[i] = (T) object;
							i++;

						}
					}
				} catch (Exception e) {
					System.err.println("Operazione non valida, il tipo del set e dell'array non coincidono");
					return null;
				}
			}

			return a;
		}

		/** Returns the comparator used in this set. */
		@Override
		public Comparator<? super T> comparator() {
			// Since this set uses the natural ordering of the elements it
			// simply returns null
			return null;
		}

		/**
		 * Returns the first (lowest) element currently in this set.
		 * 
		 * @return Returns the first (lowest) element currently in this set.
		 * @throws NoSuchElementException
		 *             if this set is empty.
		 */
		@Override
		public T first() {
			if (this.isEmpty() == true)
				throw new NoSuchElementException("The set is empty");
			return minimum();
		}

		/**
		 * Returns the last (highest) element currently in this set.
		 * 
		 * @return Returns the last (highest) element currently in this set.
		 * @throws NoSuchElementException
		 *             if this set is empty.
		 */
		@Override
		public T last() {
			if (this.isEmpty() == true)
				throw new NoSuchElementException("The set is empty");
			return maximum();
		}

		/**
		 * Returns a view of the portion of this set whose elements are strictly
		 * less than toElement. Note: the returned set is not backed by this set
		 * (as the original method says).
		 * 
		 * @param toElement
		 *            high endpoint (exclusive) of the returned set.
		 * @return a view of the portion of this set whose elements are strictly
		 *         less than toElement.
		 */
		@Override
		public SortedSet<T> headSet(T toElement) {
			if (toElement == null)
				throw new IllegalArgumentException("Null elements not allowed!!!");
			if (this.isEmpty() == true)
				throw new NoSuchElementException("The set is empty");

			mySortedSet<T> set = new mySortedSet<>();

			Node<T> node = super.getMin(super.root);

			int x = toElement.compareTo(node.data);
			// It simply add the elements until toElement is reached
			while (x > 0) {
				set.add(node.data);
				node = getSuccessor(node.data);
				x = toElement.compareTo(node.data);
			}

			return set;
		}

		/**
		 * Returns a view of the portion of this set whose elements range from
		 * fromElement, inclusive, to toElement, exclusive. (If fromElement and
		 * toElement are equal, the returned set is empty.). Note:the returned
		 * set is not backed by this set (as the original method says).
		 * 
		 * @param fromElement
		 *            low endpoint (inclusive) of the returned set.
		 * @param toElement
		 *            high endpoint (exclusive) of the returned set.
		 * @return a view of the portion of this set whose elements range from
		 *         fromElement, inclusive, to toElement, exclusive.
		 */
		@SuppressWarnings("unchecked")
		@Override
		public SortedSet<T> subSet(T fromElement, T toElement) {
			// Checks the if the specified values are of the same type
			if (fromElement.getClass() != toElement.getClass())
				throw new IllegalArgumentException(
						"Error:the two elements that set the range must be of the same type");
			if (fromElement == null || toElement == null)
				throw new NullPointerException("Null elements not allowed!!!");
			// Checks if fromElement is grater than toElement
			int y = fromElement.compareTo(toElement);
			if (y > 0)
				throw new IllegalArgumentException(
						"Invalid values: the left value of the parameter must be lower than the right one");

			mySortedSet<T> treeSet = new mySortedSet<>();

			Iterator<?> iterator = this.iterator();

			Object object = iterator.next();
			// Compare fromElemet, that will be the low endpoint, with the
			// current element of this set. The search continue until
			// the value equals to fromElement or a greater value is reached.
			int x = fromElement.compareTo((T) object);
			while (x > 0) {
				object = iterator.next();
				x = fromElement.compareTo((T) object);
			}
			/*
			 * When the desired low endpoint is reached compares the high
			 * endpoint with the current element of this set.While the high
			 * endpoint is not reached adds the element to the new sorted set
			 * created.
			 */
			x = toElement.compareTo((T) object);
			while (x > 0) {
				treeSet.add((T) object);
				object = iterator.next();
				x = toElement.compareTo((T) object);
			}

			return treeSet;

		}

		/***/
		@SuppressWarnings("unchecked")
		@Override
		public SortedSet<T> tailSet(T fromElement) {
			if (fromElement == null)
				throw new IllegalArgumentException("Null elements not allowed!!!");
			// Iterator<?> iterator = this.iterator();

			mySortedSet<T> treeSet = new mySortedSet<>();
			Iterator<?> iterator = this.iterator();
			Object object = iterator.next();
			int x = fromElement.compareTo((T) object);
			// treeSet = this;

			while (x > 0) {
				object = iterator.next();
				x = fromElement.compareTo((T) object);
			}
			while (iterator.hasNext()) {
				treeSet.add((T) object);
				object = iterator.next();
				if (!iterator.hasNext())
					treeSet.add((T) object);
			}

			return treeSet;

		}

		public class treeIterator implements Iterator<T> {
			/** Next element of the iterator */
			private Node<T> next;

			/**
			 * Construct an iterator(that starts from the first element of the
			 * set) to flow the elements.
			 * 
			 * @param root
			 *            the root the iterator starts from
			 */
			public treeIterator(Node<T> root) {

				next = root;
				// If next is null it means that the iterator doesn't have any
				// element
				if (next == null)
					return;
				// With this operation the iterator starts from the shortest
				// element
				while (next.leftChild != null)
					next = next.leftChild;

			}

			/**
			 * Checks for the next element of the iterator; It aims to the
			 * first(lowest) element at the beginning.
			 * 
			 * @return true if the iterator has one more element, false
			 *         otherwise
			 */
			public boolean hasNext() {
				return next != null;
			}

			/**
			 * Returns the next element of the iterator.The elements are in
			 * order, so the next element of the iterator corresponds to the
			 * direct successor of the element itself.
			 * 
			 * @return the next element of the iteration.
			 * @throws NoSuchElementException
			 *             if the iteration has no more elements.
			 */
			public T next() {
				if (!hasNext())
					throw new NoSuchElementException();
				// Initializes a support variable with next value;
				Node<T> r = next;
				/*
				 * If the aimed element has a right child, it will be the
				 * element of the next iterator's call.
				 */
				if (next.rightChild != null) {
					next = next.rightChild;
					/*
					 * If the right child aforementioned has a left child, it
					 * will be the next in the next call of the iterator.
					 */
					while (next.leftChild != null)
						next = next.leftChild;
					return r.data;
				}
				// This cycle climbs the tree
				while (true) {
					/*
					 * If next doesn't have a father it means that we are at the
					 * end of the iterator, therefore it hasn't a following
					 * element. This occurs when we are on the last element of
					 * the iterator(the greatest value of the tree) that, not
					 * having a right child and not being a left child, climb
					 * the tree until the root is reached; the root, as defined,
					 * doesn't have any father.
					 */
					if (next.parent == null) {
						next = null;
						return r.data;
					}
					// If next is a left child, the successor will be the
					// father.

					if (next.parent.leftChild == next) {
						next = next.parent;
						return r.data;
					}

					next = next.parent;
				}

			}

			/** Remove an element from the iterator. */
			public void remove() {
				throw new UnsupportedOperationException("Remove is not supported");
			}

		}

	}
}

/** @author mariol96 */