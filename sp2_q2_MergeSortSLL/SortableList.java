package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q2_MergeSortSLL;

import java.util.Scanner;

public class SortableList<T extends Comparable<? super T>> extends SinglyLinkedList<T> {
    
	void merge(SortableList<T> otherList) {  
		Entry<T> head1 = this.head;
		Entry<T> head2 = otherList.head;
		Entry<T> tail1 = this.tail;
		Entry<T> tail2 = otherList.tail;
		Entry<T> tmp = null;
		Entry<T> tmpHead = null;
		while (head1 != this.tail.next && head2 != otherList.tail.next) {
			if (head1.element.compareTo(head2.element) <= 0) {
				if (tmp == null){
					tmp = head1;
					tmpHead = tmp;
				}
				else{
					tmp.next = head1;
					tmp = tmp.next;
				}
				head1 = head1.next;
			} else {
				if (tmp == null){
					tmp = head2;
					tmpHead = tmp;
				}
				else{
					tmp.next = head2;
					tmp = tmp.next;
				}
				head2 = head2.next;
			}
		}
		if (head1 != this.tail.next) {
			tail1.next = tail2.next;
			tmp.next = head1;
		} else if (head2 != otherList.tail.next){
			tmp.next = head2;
		}
		this.head = tmpHead;
    }
	
	void mergeSort() {
		if (this.head == this.tail)  return;

		Entry<T> head = this.head;
		Entry<T> tail = head;
		Entry<T> middle = head;

		while (tail != null && tail != this.tail && tail.next != this.tail && tail.next != null && tail.next.next != null) {
			middle = middle.next;
			tail = tail.next.next;
		}


		SortableList<T> fhalf = new SortableList<>();
		fhalf.head = head;
		fhalf.tail = middle;
		SortableList<T> lhalf = new SortableList<>();
		lhalf.head = middle.next;
		lhalf.tail = this.tail;

		fhalf.mergeSort();
		lhalf.mergeSort();
		fhalf.merge(lhalf);
		this.head = fhalf.head;
		this.tail = fhalf.tail;
    }
    public static<T extends Comparable<? super T>> void mergeSort(SortableList<T> list) {
    	list.mergeSort();    	
    }

	public static void main(String[] args) {

		int n = 10, value = 0;

		SortableList<Integer> sl = new SortableList<>();
		Scanner in = new Scanner(System.in);

		for (int i = 0; i < n; i++){
			value = in.nextInt();
			sl.add(new Integer(value));
		}

		sl.head = sl.head.next;
		mergeSort(sl);
		
		System.out.println("After merge sort ::");
		
		sl.printListWithoutDummyHeader();
		in.close();

	}
}
