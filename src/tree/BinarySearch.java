package tree;


/**
 * 二叉查找树
 */
public class BinarySearch {

    private static TreeNode root = null;
    private static int[] digit = new int[]{
      5, 3, 2, 0, 0, 5, 0, 0, 7, 0, 8, 0, 0
    };
    private static int index = -1;

    public static TreeNode createBitree(TreeNode root){
        int data = digit[++index];
        root = new TreeNode(data);
        if (data != 0){
            root.data = data;
            //System.out.print(root.data + "的左孩子");
            root.left = createBitree(root.left);
            //root.parent = root;
            //System.out.print(root.data + "的右孩子");
            root.right = createBitree(root.right);
            //root.parent = root;
        }
        else {
            root = null;
        }
        return root;
    }

    public static void inorderTreeWalk(TreeNode root) {
        if (root != null) {
            inorderTreeWalk(root.left);
            System.out.print(root.data + " ");
            inorderTreeWalk(root.right);
        }
    }

    //非递归查找
    public TreeNode iterativeTreeSearch(TreeNode node, int key){
        while (node != null && key != node.data){
            if (key < node.data)
                node = node.left;
            else
                node = node.right;
        }
        return node;
    }

    //递归查找
    public static TreeNode treeSearch(TreeNode node, int key){

        if (key == node.data)
            return node;

        if (key < node.data)
            //要返回一个节点回去才可以
            return treeSearch(node.left, key);
        else
            return treeSearch(node.right, key);
    }

    public static TreeNode treeMinimum(TreeNode node){
        while (node.left != null)
            node = node.left;
        return node;
    }

    public static TreeNode treeMaximum(TreeNode node){
        while (node.right != null)
            node = node.right;
        return node;
    }

    private static class TreeNode{
        TreeNode left;
        TreeNode right;
        TreeNode parent;
        int data;

        TreeNode(){ }

        TreeNode(int data){
            this.data = data;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    public static void treeInsert(TreeNode node){
        TreeNode parent = null;
        //找到插入的适合位置
        TreeNode temp = root;
        while (temp != null){
            parent = temp;//始终指向父亲节垫
            if (node.data < temp.data)
                temp = temp.left;
            else
                temp = temp.right;
        }

        node.parent = parent;

        if (parent == null) //查找二叉树为空
            root = node;
        else{
            if (node.data < parent.data)
                parent.left = node;
            else
                parent.right = node;
        }

    }

    public static TreeNode createBinaryTree(int n){
        for (int i=0; i<n; i++){
            int data = (int)(Math.random()*20);
            TreeNode temp = new TreeNode(data);
            treeInsert(temp);
        }
        return root;
    }

    /**
     * 查找node的后继
     * 如果该节点有右子树，则该节点的后继是该节点的右子树中最左边的元素；如果该节点没有右子树，从当前节点开始往上寻找，
     * 直到当前的节点是其父节点的左孩子，那么这个父节点就是起始节点的后继。
     * @param node
     * @return
     */
    public static TreeNode treeSuccessor(TreeNode node){
        if (node.right != null)
            return treeMinimum(node.right);
        TreeNode parent = node.parent;
        while (parent != null && node == parent.right){
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    /**
     * 查找二叉查找树的前驱
     * 如果该节点有左子树，那么该节点的前驱就是该节点左子树中最右边的节点；如果该节点没有左子树，从当前节点开始往上寻找，
     * 直到当前节点是其父节点的右孩子，那么这个父节点，就是起始节点的前驱。
     * @param node
     * @return
     */
    public static TreeNode treePioneer(TreeNode node){
        if (node.left != null)
            return treeMaximum(node.left);
        TreeNode parent = node.parent;
        while (parent != null && node == parent.left){
            node = parent;
            parent = parent.parent;
        }
        return parent;
    }

    /**
     * 二叉查找树的删除
     * @param node 返回被删节点
     */
    public static TreeNode treeDelete(TreeNode node){
        //要删除的目标节点，如果该节点至多有一个节点，则删除当前节点，如果有两个节点则删除他的后继并且把节点值赋值为后继的值
        TreeNode target = null;
        TreeNode child = null;

        if (node.left == null || node.right == null)
            target = node;
        else
            target = treeSuccessor(node);//删除节点变为他的后继

        if (target.left != null)
            child = target.left;
        else
            child = target.right;

        //如果被删除节点只有一个子女而且不为空，则只要通过其子节点与父亲节点之间建立一条链来删除当前节点
        //如果子女为空，则直接删除该节点
        if (child != null)
            child.parent = target.parent;

        if (target.parent == null)  //如果被删节点是根节点，则其子女成为心得根节点
            root = child;
        else{   //如果根节点不为空
            if (target == target.parent.left)
                target.parent.left = child;
            else
                target.parent.right = child;

        }

        //当被删节点有两个子女时，不仅要删除起后继节点，还要把后继节点的值复制到源节点中（他的前驱节点），即修改它的值
        if (target != node)
            node.data = target.data;

        return target;
    }

    public static void main(String[] args) {
        TreeNode binaryTree = createBinaryTree(10);
        System.out.println(root.data);
        inorderTreeWalk(binaryTree);
        System.out.println();
        System.out.println(root.data);
    }


}
