import java.util.SortedSet;
import java.util.TreeSet;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/*UPDATE: migliorato il metodo principale per la rimozione di un nodo, ora si può eliminare la radice
 * dell'albero anche essendo l'unico elemento dll'albero; migliorato anche il metodo per l'estrazione
 * di un nodo con un figlio, problema nell'eliminazione di un figlio della radice dell'albero,avente
 * due soli elementi(padre e figlio quindi),risolto.
 * DA VERIFICARE: METODO RETAINALL;
 * 				  METODO TOARRAY();
 * 				  METODO TOARRAY(T[] a);
 */
public class mySortedSetTest extends AVLTree {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		mySortedSet<Integer> s1 = new mySortedSet<Integer>();
		mySortedSet<Integer> s2 = new mySortedSet<Integer>();

		// ArrayList<String> list=new ArrayList<String>();
		// s1.removeAll(list);
		
		for (int i = 0; i < 500; i++) {
			int x = (int) (Math.random() * 1000);
			s1.add(x);

		}
		
		
		
		s1.addAll(s2);
		
		
	
		
		for (Integer el : s1) {
			System.out.println(el);
		}

	
		
		
		
		

	}

}
