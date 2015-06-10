package heap;

/**
 * A self-adjusting heap that is implemented as a binary tree
 * It is advantageous over binary heap because of the ability to merge more quickly
 * no structural constraints, so no guarantee that theheight is logarithmic 
 * 
 * @author Benchun
 *
 * @param <T> element type, must implements Comparable 
 */
public class SkewHeap <T extends Comparable<? super T>> 
					  implements IHeap<T> {
	private BinaryTreeNode<T> root;
	private int size;
	
	public SkewHeap(T rootElement) {
		root = new BinaryTreeNode<T>(rootElement);
		size = 1;
	}
	
	@Override
	public T peak() {
		return root == null ? null : root.element;
	}

	@Override
	public T extract() {
		if(root==null)
			throw new RuntimeException("empty heap exception");
		T rootElement = root.element;
		root = merge(root.left, root.right);
		size--;
		return rootElement;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size<=0;
	}

	@Override
	public void insert(T element) {
		if(element==null)
			throw new IllegalArgumentException("element cannot be null");
		if(root==null)
			root = new BinaryTreeNode<T>(element);
		else 
			root = merge(root, new BinaryTreeNode<T>(element));
		size++;
	}
	
	public SkewHeap<T> merge(SkewHeap<T> heap) {
		if(heap==null)
			return this;
		BinaryTreeNode<T> newRoot = merge(this.root, heap.root);	
		this.root = heap.root = newRoot;
		this.size = heap.size = (this.size + heap.size);
		return this;
	}
	
	private BinaryTreeNode<T> merge(BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
		return recursiveMerge(left, right);
	}
	
	private BinaryTreeNode<T> recursiveMerge(BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
		if(left==null)
			return right;
		if(right==null)
			return left;
		
		//during merge, new left and new right are swapped to avoid unbalanced tree
		if( left.element.compareTo(right.element) == -1 ){
			BinaryTreeNode<T> temp = left.left;
			left.left = merge(left.right, right);
			left.right = temp;
			return left;
		} else {
			BinaryTreeNode<T> temp = right.left;
			right.left = merge(right.right, left);
			right.right = temp;
			return right;
		}
	}
	
	private BinaryTreeNode<T> nonrecursiveMerge(BinaryTreeNode<T> left, BinaryTreeNode<T> right) {
		return this.root;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public T[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void print() {
		System.out.print("Heap dump: ");
		printNode(root);
		System.out.println();
	}
	
	private void printNode(BinaryTreeNode<T> node) {
		if(node==null)
			return;
		printNode(node.left);
		System.out.print(node.element + " ");
		printNode(node.right);
	}
	
	private static class BinaryTreeNode<T> {		
		T element;			     
		BinaryTreeNode<T> left;  
		BinaryTreeNode<T> right; 
		
		BinaryTreeNode(T theElement) {
			element = theElement;
			left = right = null;
		}
	}
	
	public static void main(String[] args) {
		SkewHeap<Integer> heap = new SkewHeap<Integer>(10);
		heap.insert(3);
		heap.insert(5);
		heap.print();
		heap.insert(4);
		heap.print();
		heap.insert(8);
		heap.print();
	}
}
