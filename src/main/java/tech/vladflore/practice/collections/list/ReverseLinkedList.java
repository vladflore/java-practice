package tech.vladflore.practice.collections.list;

import lombok.Getter;
import lombok.Setter;

public class ReverseLinkedList {

    public static void main(String[] args) {

        // create the singly-linked list
        ListNode head = null, tail = null;
        for (int i = 1; i <= 5; i++) {
            ListNode node = new ListNode(i);
            if (head == null) {
                head = node;
            } else {
                tail.setNext(node);
            }
            tail = node;
        }

        printList(head);

        // reverse the singly-linked list
        ListNode previous = null;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.getNext();
            current.setNext(previous);
            previous = current;
            current = next;
        }
        head = previous;

        printList(head);

    }

    private static void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.getData());
            if (head.getNext() != null) {
                System.out.print(" ");
            } else {
                System.out.println("");
            }
            head = head.getNext();
        }
    }

}

@Getter
class ListNode {
    private int data;

    @Setter
    private ListNode next;

    ListNode(int data) {
        this.data = data;
    }
}
