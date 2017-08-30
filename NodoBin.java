import progetto.util.*;

public class NodoBin<T> {
	private T el = null;
	private NodoBin<T> left = null;
	private NodoBin<T> right = null;
	
	public NodoBin(T t) {
		el = t;
	}
	
	public T getEl() {
		return el;
	}
	
	public void setLeft(NodoBin<T> n) {
		left = n;
	}
	
	public void setRight(NodoBin<T> n) {
		right = n;
	}
	
	public void visitaPreOrder(NodoBin<T> n)	{
		if(n != null) {
			System.out.println(n.getEl());
			visitaPreOrder(n.left);
			visitaPreOrder(n.right);
		}
	}
	
	public void visitaPerLiveli() {
		Coda<NodoBin<T>> c = new Coda<NodoBin<T>>();
		c.enqueue(this);
		while(!c.isEmpty()) {
			NodoBin t = c.dequeue();
			if(t != null) {
				System.out.println(t.getEl());
				c.enqueue(t.left);
				c.enqueue(t.right);
			}
		}
	}
}
