package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q4_ReverseList;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q4_ReverseList.SinglyLinkedList;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q4_ReverseList.SinglyLinkedList.Entry;

public class ReverseList {
	public static<T> void reverseRecursive(SinglyLinkedList<T> ll) {
		reverse(ll.head,null);
	}
	
	public static<T> Entry<T> reverse(Entry<T> node,Entry<T> prev) {
		if(node==null)
			return prev;
		Entry<T> n = node.next;
		node.next = prev;
		return reverse(n,node);
	}
	
	public static<T> void reverseIterative(SinglyLinkedList<T> ll) {
		Entry<T> prev = null;
		while(ll.head!=null) {
			Entry<T> curr =  ll.head;
			ll.head = ll.head.next;
			curr.next = prev;
			prev = curr;
		}
	}

	public static void main(String args[]) {
		SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
		ll.add(10);
		ll.add(15);
		ll.add(20);
		ll.add(1);
		ll.add(2);
		
		System.out.print("Iterative: ");
		reverseIterative(ll);
		while(ll.tail!=null) {
			System.out.print(ll.tail.element+"  ");
			ll.tail = ll.tail.next;
		}
		
		ll = new SinglyLinkedList<Integer>();
		ll.add(10);
		ll.add(15);
		ll.add(20);
		ll.add(1);
		ll.add(2);
		
		System.out.print("\nRecursive: ");
		reverseRecursive(ll);
		while(ll.tail!=null) {
			System.out.print(ll.tail.element+"  ");
			ll.tail=ll.tail.next;
		}
	}
}
