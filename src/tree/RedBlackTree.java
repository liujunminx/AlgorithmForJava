package tree;

/**
 * 红黑树：
 * １，每个节点或是红的，或是黑的
 * ２，根节点是黑的
 * ３，每个叶节点是黑的
 * ４，如果一个节点是红的，则它的两个儿子都是黑的
 * ５，对每个节点，从该节点到其子孙节点的所有路径上的包含相同数目的黑节点
 */
public class RedBlackTree {
    private RBNode root = null;
    private RBNode tail = null; //代表所有的叶子以及根节点的父节点
    private int[] redBlackArray = new int[]{
            11, 2, 14, 1, 7, 15, 5, 8, 4
    };

    private void createRBTreeInArray(){
        for (int i=0; i<redBlackArray.length; i++){
            RBInsert(redBlackArray[i]);
        }
    }

    private void inorderTreeWalk(RBNode root) {
        if (root != null) {
            inorderTreeWalk(root.left);
            System.out.println(root.key + " " + root.color);
            inorderTreeWalk(root.right);
        }
    }

    /**
     * 红黑树的左旋
     * @param node
     */
    private void leftRotate(RBNode node){
        RBNode rightChild = node.right;     //左旋是以该节点和其右孩子节点为轴进行旋转
        node.right = rightChild.left;   //把右孩子的左子树和该节点的右子树相连
        if (rightChild.left != tail)   //如果其右孩子节点不为叶子
            rightChild.left.parent = node;  //其右孩子的左子树父亲节点连上（指向）节点，相当于断开了该节点与其右孩子节点的链接

        rightChild.parent = node.parent;    //其右孩子节点的父亲节点指向该节点的父亲节点，断开该节点与其父亲节点的链接

        if (node.parent == tail)    //如果是根节点
            root = rightChild;
        else{   //如果根节点不为空
            if (node == node.parent.left)  //如果该节点是左孩子，　如果不为空
                node.parent.left = rightChild;  //则该节点父亲节点的左子树指向其右孩子节点　否则右子树指向
            else
                node.parent.right = rightChild; //至此，该节点的右孩子称为新的内节点
        }


        rightChild.left = node;     //该节点的左子树指向该节点，此时旋转已经完成，该节点成为了其右子树的左子树，且该节点右子树替代该节点成为了新的内节点
        node.parent = rightChild;
    }

    private void rightRotate(RBNode node){
        RBNode leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != tail)
            leftChild.right.parent = node;

        leftChild.parent = node.parent;

        if (node.parent == tail)
            root = leftChild;
        else if (node == node.parent.left)
            node.parent.left = leftChild;
        else
            node.parent.right = leftChild;

        leftChild.right = node;
        node.parent = leftChild;
    }

    /**
     * 红黑树的插入
     * @param node
     */
    private void RBInsert(RBNode node){
        if (node == null)
            return;
        RBNode parent = tail;
        RBNode temp = root;
        while (temp != tail){
            parent = temp;
            if (node.key < temp.key)
                temp = temp.left;
            else
                temp = temp.right;
        }

        node.parent = parent;

        if (parent == tail)
            root = node;
        else{
            if (node.key < parent.key)
                parent.left = node;
            else
                parent.right = node;
        }

        node.left = tail;
        node.right = tail;
        node.color = RBColor.RED;
        //初始化每个节点为红色
        insertFixUp(node);
    }

    private RBNode parentOf(RBNode node){
        return node.parent;
    }

    /**
     * 保持红黑树的性质，调整节点位置
     * 插入会导致红黑树被破坏的性质：
     * 1，根节点是黑的
     * 2，如果一个节点是红的，则他的两个子女都是黑的
     * @param node
     */
    private void RBInsertFixup(RBNode node){

        root.color = RBColor.BLACK;
        RBNode parent, gparent;

        //被传入的节点是红色，他的父亲节点也应该是黑色，如果是黑色，则需要调整
        while ((parent=parentOf(node))!=null && parent.color == RBColor.RED){
            //如果父节点是左孩子
            //这里会遇到父节点是根节点，但是根节点永远是黑色的，而且只有父节点是红色时才进入循环，所以祖父节点肯定存在
            if ((gparent = parentOf(parent))!=null && parent == gparent.left){
                //uncle是叔父节点
                RBNode uncle = gparent.right;
                //第一种情况
                //如果叔夫节点是红的，已知父亲节点是红的，该节点也是红的，不满足红黑树性质
                //而且由于是向具有红黑树性质的树插入节点，所以祖父节点是黑色的
                //故使得以祖父节点为根节点的树调整为红黑树就需要把祖父节点变成红色，叔父节点和父节点变成黑色，
                //如果叔父节点是黑的则说明父亲节垫也是黑的
                /**
                 * 用z标示当前迭代中的节点，用grandPa = z.parent.parent表示下一次迭代的节点
                 * 1）因为这次迭代把grandPa着色为red,所以下一次迭代，他的颜色也是red
                 * 2）在此次迭代中节点grandPa.parent是z.parent.parent.parent,而且这个节点的颜色不变
                 * 如果他是根节点，则再次迭代之前他是黑的，此次迭代之后他还是黑的
                 * 结果：
                 * 如果节点grandPa在下一次迭代的开始是根，则在这次的迭代中修正了唯一被破坏的性质2，并且由于grandPa是根而且是红色的
                 * 所以性质2成为了唯一被违反性质，只是由于grandPa导致的，此时只需要修改根节点颜色永远是黑色的
                 * 如果节点grandPa在下一次迭代的开始不是根，则在这次的迭代中修改了唯一被破坏的性质2，然后grandPa颜色被修改为红色，而grandPa.parent颜色不变
                 * 如果grandPa.parent颜色是黑色的，则没有违反性质
                 * 如果grandPa.parent颜色是红色的，则把grandPa着色为红色会在grandPa与grandPa.parent之间造成性质2的违反
                 * 所以循环条件为当前节点父节点是红色的才要调整
                 */
                if (uncle.color == RBColor.RED){
                    parent.color = RBColor.BLACK;
                    uncle.color = RBColor.BLACK;
                    gparent.color = RBColor.RED;
                    //节点指针上移到祖父节点
                    node = gparent;
                }
                //如果叔父节点是黑色的
                else{
                    //如果节点是右孩子，节点指针上移到父节点并左旋，父节点左旋
                    if (node == node.parent.right){
                        node = node.parent;
                        leftRotate(node);
                    }
                    //如果节点是左孩子，则曾父节点右旋，由于右旋时父节点的右子树连接到曾祖父左子树，所以曾祖父就有两个子女，所以曾祖父的颜色修改为红色
                    //而且由于当前节点颜色为红色，右旋后父节点替换曾祖父节点称为新的内节点，则祖父节点右一红一黑两个孩子，所以祖父节点修改为红色
                    node.parent.color = RBColor.BLACK;
                    node.parent.parent.color = RBColor.RED;
                    rightRotate(node.parent);
                }
            }
            //如果父节点是右孩子
            else if ((gparent=parentOf(parent))!=null && parent==gparent.right){
                RBNode uncle = gparent.left;
                if (uncle.color == RBColor.RED){
                    node.parent.color = RBColor.BLACK;
                    uncle.color = RBColor.BLACK;
                    node.parent.parent.color = RBColor.RED;
                    node = node.parent.parent;
                }
                else {
                    if (node == node.parent.right){
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = RBColor.BLACK;
                    node.parent.parent.color = RBColor.RED;
                    rightRotate(node.parent.parent);
                }
            }
        }
    }

    private boolean isRed(RBNode node){
        return node.color==RBColor.RED;
    }

    private void setBlack(RBNode node){
        node.color = RBColor.BLACK;
    }

    private void setRed(RBNode node){
        node.color = RBColor.RED;
    }

    private void insertFixUp(RBNode node) {
        RBNode parent, gparent;

        // 若“父节点存在，并且父节点的颜色是红色”
        while (((parent = parentOf(node))!=null) && isRed(parent)) {
            gparent = parentOf(parent);

            //若“父节点”是“祖父节点的左孩子”
            if (parent == gparent.left) {
                // Case 1条件：叔叔节点是红色
                RBNode uncle = gparent.right;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是右孩子
                if (parent.right == node) {
                    RBNode tmp;
                    leftRotate(parent);
                    //左旋之后节点指针上移到父节点，但是父节点左旋之后转变成了父节点的左孩子
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是左孩子。
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {    //若“z的父节点”是“z的祖父节点的右孩子”
                // Case 1条件：叔叔节点是红色
                RBNode uncle = gparent.left;
                if ((uncle!=null) && isRed(uncle)) {
                    setBlack(uncle);
                    setBlack(parent);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                // Case 2条件：叔叔是黑色，且当前节点是左孩子
                if (parent.left == node) {
                    RBNode tmp;
                    rightRotate(parent);
                    tmp = parent;
                    parent = node;
                    node = tmp;
                }

                // Case 3条件：叔叔是黑色，且当前节点是右孩子。
                setBlack(parent);
                setRed(gparent);
                leftRotate(gparent);
            }
        }
        // 将根节点设为黑色
        setBlack(this.root);
    }

    private void RBInsert(int key){
        RBNode node = new RBNode(key);
        if (node != null)
            RBInsert(node);
    }


    private RBNode treeMinimum(RBNode node){
        while (node.left != null)
            node = node.left;
        return node;
    }

    /**
     * 节点后继
     * @return
     */
    private RBNode treeSuccessor(RBNode node){
        if (node.right != null)
            return treeMinimum(node.right);

        RBNode parent = parentOf(node);
        while (parent!=null && parent!=parent.left){
            parent = parentOf(parent);
        }

        return parent;
    }

    private RBNode leftOf(RBNode node){
        return node.left;
    }

    private RBNode rightOf(RBNode node){
        return node.right;
    }
    /**
     * 删除节点
     * @param node
     */
    private void RBDelete(RBNode node){
        RBNode target = null;
        RBNode temp = null;
        if (node.left!=null || node.right!=null)
            target = node;
        else
            target = treeSuccessor(node);

        if (leftOf(target) != null && target!=null)
            temp = node.left;
        else
            temp = node.right;

        if (temp != null)
            temp.parent = target.parent;

        //target是删除的当前节点
        if (target.parent == null)
            root = node;
        else {
            if (target == target.parent.left)
                target.parent.left = node;
            else
                target.parent.right = node;
        }

        //修改节点卫星数据
        if (target != node)
            node.key = target.key;

        /**
         * 当节点是黑色的才执行修正，如果节点是红色的则不修正
         * 理由如下：
         * 1，删除该节点后，红黑树的黑高度并没有改变
         * 2，红黑树没有相邻的两个红色的节点
         * 3，如果节点是红色的，则节点不是根，删除节点后，根节点颜色仍然是黑色的
         */
        if (target.color == RBColor.BLACK)
            RBDeleteFixup(node);
    }

    /**
     * 对红黑树中node节点的修正
     * @param node
     */
    private void RBDeleteFixup(RBNode node){
        RBNode brother = null;
        RBNode parent = null;
        //node节点具有额外的一种黑色，所以node或为红黑或为双重黑
        //因为node为黑色时才进行修正，所以node开始是双重黑
        while (node!=root && node.color == RBColor.BLACK && (parent = parentOf(node))!=null ){
            //如果修正节点是左孩子
            if (node==parent.left){
                brother = parent.right;
                //情况 1：当兄弟颜色是红色时
                //父节点左旋时父节点右子树链接上了兄弟节点左子树所以父节点着色为红色
                //兄弟节点装变为内节点变为黑色
                //左旋后兄弟节点随之变化，所以要更新兄弟节点
                //情况一在转换为情况二，情况三和情况四
                if (brother.color == RBColor.RED){
                    brother.color = RBColor.BLACK;
                    parent.color = RBColor.RED;
                    leftRotate(parent);
                    brother = parent.right;
                    continue;
                }
                /**
                 * 情况二：brother的颜色是黑色的，而且brother的两个孩子的颜色是黑色的
                 * brother的两个孩子是黑色的，因为brother也是黑色的，所以从brother和node上去掉一重黑色，之后得到的node只有一重黑色，而brother颜色是红色
                 * 为了补偿从node和brother上去掉的一重黑色，我们想在原来是红色或者黑色的node的父节点内新增一重额外的黑色
                 * 通过父节点为新节点node来重复while循环，注意如果通过情况一进入情况二，则新节点是红黑色的，因此新节点node的颜色是红色
                 * 并且在测试循环之后为了保持红黑树性质，在最后新节点node被单独着色为黑色（这里的Node是元Node的父节点）
                 */
                if (brother.right.color==RBColor.BLACK && brother.left.color==RBColor.BLACK){
                    brother.color = RBColor.RED;
                    node = node.parent;
                } else {
                    /**
                     * 情况三：brother节点的颜色是黑色的，brother节点的左孩子是红色的，右孩子是黑色的
                     * 交换brother和brother右孩子的颜色，并且进行右旋，保持红黑树的性质
                     * 右旋之后Node的新兄弟brother是一个右孩子颜色是红色的节点，进入情况四
                     */
                    if (brother.right.color == RBColor.BLACK){
                        brother.left.color = RBColor.BLACK;
                        brother.color = RBColor.RED;
                        rightRotate(brother);
                        brother = node.parent.right;
                        continue;
                    }
                    /**
                     * 情况四：
                     * brother色颜色转化为Node父节点的颜色
                     * 因为要对父节点进行左旋，所以父节点颜色变为黑色，又因为brother装变为心得内节点，而他的父节点颜色可能是红色，
                     * 为了继续保持红黑树的性质，故bother的右孩子的颜色着色为黑色，brother的左子树右旋接父节点，父节点已经是黑色
                     * 在吧Node置为根
                     */
                    brother.color = node.parent.color;
                    node.parent.color = RBColor.BLACK;
                    brother.right.color = RBColor.BLACK;
                    leftRotate(node.parent);
                    node = root;
                }
            }

            //如果节点是右孩子
            else

                {

                brother = node.parent.left;

                if (brother.color == RBColor.RED){
                    brother.color = RBColor.BLACK;
                    parent.color = RBColor.RED;
                    rightRotate(parent);
                    brother = parent.left;
                    continue;
                }


                if (brother.left.color==RBColor.BLACK || brother.right.color==RBColor.BLACK){
                    brother.color = RBColor.RED;
                    node = parent;
                } else {

                    if (brother.left.color == RBColor.RED){
                        brother.right.color = RBColor.BLACK;
                        brother.color = RBColor.RED;
                        leftRotate(node.parent.left);
                        brother = node.parent.left;
                        continue;
                    }

                    brother.color = node.parent.color;
                    node.parent.color = RBColor.BLACK;
                    brother.left.color = RBColor.BLACK;
                    rightRotate(node.parent);
                    node = root;
                }
            }
        }



        node.color = RBColor.BLACK;
    }

    /**
     * 红黑树节点
     */
    private class RBNode{
        private RBNode left;
        private RBNode right;
        private RBNode parent;
        private int key;
        private RBColor color;
        public RBNode(int key){
            this.key = key;
            this.left = tail;
            this.right = tail;
            this.parent = tail;
        }
    }

    enum RBColor{
        RED, BLACK
    }


    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.createRBTreeInArray();
        tree.inorderTreeWalk(tree.root);
        tree.RBDelete(tree.root.left.left);
        tree.inorderTreeWalk(tree.root);
    }
}
