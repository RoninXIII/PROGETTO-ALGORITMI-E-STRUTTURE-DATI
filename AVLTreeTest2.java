
public class AVLTreeTest2 {

	public static void main(String[] args) {
		AVLTree<Integer> a1 = new AVLTree<Integer>();
	
		
		System.out.println(a1.height());
		a1.insert(10);
		a1.insert(11);
		a1.insert(7);
		a1.insert(3);
		a1.insert(2);
		
		
		// System.out.println(a1.getSuccessor(50));
		// a1.stampa();
		
		// a1.stampa();
		/*
		 * a1.removeNode(11); a1.removeNode(170); a1.removeNode(50);
		 * a1.removeNode(23);
		 */
		// System.err.println("Dopo la rimozione: ");
		// a1.removeNode(170);
		// a1.removeNode(50);
		// a1.removeNode(80);

		a1.print();
		// System.out.println(a1);

		// System.out.println(a1.getSuccessor(-6));
		// System.out.println(a1.minimo());

		/*
		 * a1.remove(90); a1.stampa();
		 */

	}

}
