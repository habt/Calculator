/*************************************************************************************************************************************************************************
 * BTree file and class,
 * Contains Tree building Interfaces(previously given) 
 *
 * @authors Habtegebreil Kassaye Haile
 * 			NB-Btree class and contained interfaces from Class material
 *
 *************************************************************************************************************************************************************************/


package function_calculator;


public class BTree  {
    public class Node {
        public Elem elem;
        Node l;
        Node r;

        void addLeft(Node n) {
            l = n;
        }
        void addRight(Node n) {
            r = n;
        }
        Node(Elem elem) 
        { 
        		this.elem = elem;
        }
    }

    public Node root = null;

    private BTree(Node n) {
        root = n;
    }

    /* ---------------------------------*/ 
    /* INTERFACE                        */
    /* ---------------------------------*/ 
    
    public BTree(Elem elem) {
        root = new Node(elem);
    }

    public BTree addLeft(BTree t) {
        root.addLeft(t.root);
        return this;
    }

    public BTree addRight(BTree t) {
        root.addRight(t.root);
        return this;
    }

    public BTree linkToLeft(BTree t) {
        t.root.addLeft(root);
        return this;
    }

    public BTree linkToRight(BTree t) {
        t.root.addRight(root);
        return this;
    }

    public BTree getLeftSubtree() {
        if (root == null) return null;
        else return new BTree(root.l);
    }

    public BTree getRightSubtree() {
        if (root == null) return null;
        else return new BTree(root.r);
    }

    void printPostOrder() {
        if (root == null) return;
        else {
        	getRightSubtree().printPostOrder();
            getLeftSubtree().printPostOrder();
            //getRightSubtree().printPostOrder();
            if(root.elem.is_value)
            	System.out.print(root.elem.value);
            else
            	System.out.print(root.elem.opn);
            System.out.print(" ");
        }
    }

    void printPreOrder() {
        if (root == null) return;
        else {
        	if(root.elem.is_value)
            	System.out.print(root.elem.value);
            else
            	System.out.print(root.elem.opn);
            System.out.print(" ");
            getLeftSubtree().printPreOrder();
            getRightSubtree().printPreOrder();
        }
    }

    void printInOrder() {
        if (root == null) return;
        else {
            getLeftSubtree().printInOrder();
            if(root.elem.is_value)
            	System.out.print(root.elem.value);
            else
            	System.out.print(root.elem.opn);
            System.out.print(" ");
            getRightSubtree().printInOrder();
        }
    }

    
}
