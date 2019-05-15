package data_structure;

/**
 * 链表的操作
 */
public class LinkList<E> {
    //头指针，尾指针，和链表大小
    private static Node head;
    private static Node tail;
    private static int size;

    public LinkList(){

    }

    private Node getNode(int key){
        Node p =head;
        int index = 0;
        while (p != null && index < key){
            p = p.next;
            index++;
        }
        return p;
    }

    private static void print(){
        Node p = head;
        while (p != null){
            System.out.print(p.elem + " ");
            p = p.next;
        }
        System.out.println();
    }

    private void linklistInsertToHead(Node temp){
        temp.next = head;
        if (head != null)
            head.prev = temp;
        head = temp;
        temp.prev = null;
    }

    private void linklistDelete(Node temp){
        if (temp.prev != null)
            temp.prev.next = temp.next;
        else
            head = temp.next;

        if (temp.next != null)
            temp.next.prev = temp.prev;
    }

    /**
     * 节点类
     */
    private static class Node{
        int elem;
        Node prev;
        Node next;

        Node(int elem){
            this.elem = elem;
        }

        Node(Node prev, Node next, int elem){
            this.elem = elem;
            this.next = next;
            this.prev = prev;
        }
    }

    public static void main(String[] args) {
        LinkList list = new LinkList();
        for (int i=0; i<10; i++){
            Node node = new Node((int)(Math.random()*100));
            list.linklistInsertToHead(node);
        }
        print();
        Node q = list.getNode(4);
        System.out.println(q.elem);
        list.linklistDelete(q);
        print();
    }
}
