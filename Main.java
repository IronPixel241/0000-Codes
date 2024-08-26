class Node
{
    int data;
    Node left, right, parent;
    boolean color;

    Node(int data)
    {
        this.data = data;
        left = right = parent = null;
        color = true;
    }
}

class RedBlackTree
{
    private Node root;
    private Node TNULL;

    public RedBlackTree()
    {
        TNULL = new Node(0);
        TNULL.color = false;
        root = TNULL;
    }

    public void insert(int key)
    {
        Node node = new Node(key);
        node.parent = null;
        node.data = key;
        node.left = TNULL;
        node.right = TNULL;
        node.color = true;

        Node y = null;
        Node x = root;

        while(x != TNULL)
        {
            y = x;
            if(node.data < x.data)
            {
                x = x.left;
            } else
            {
                x = x.right;
            }
        }

        node.parent = y;
        if(y == null)
        {
            root = node;
        } else if(node.data < y.data)
        {
            y.left = node;
        } else
        {
            y.right = node;
        }

        if(node.parent == null)
        {
            node.color = false;
            return;
        }

        if(node.parent.parent == null)
        {
            return;
        }

        balanceInsert(node);
    }

    private void balanceInsert(Node k)
    {
        Node u;
        while(k.parent.color == true)
        {
            if(k.parent == k.parent.parent.right)
            {
                u = k.parent.parent.left;
                if(u.color == true)
                {
                    u.color = false;
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    k = k.parent.parent;
                } else
                {
                    if(k == k.parent.left)
                    {
                        k = k.parent;
                        rightRotate(k);
                    }
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    leftRotate(k.parent.parent);
                }
            } else
            {
                u = k.parent.parent.right;

                if(u.color == true)
                {
                    u.color = false;
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    k = k.parent.parent;
                } else
                {
                    if(k == k.parent.right)
                    {
                        k = k.parent;
                        leftRotate(k);
                    }
                    k.parent.color = false;
                    k.parent.parent.color = true;
                    rightRotate(k.parent.parent);
                }
            }
            if(k == root)
            {
                break;
            }
        }
        root.color = false;
    }

    private void leftRotate(Node x)
    {
        Node y = x.right;
        x.right = y.left;
        if(y.left != TNULL)
        {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if(x.parent == null)
        {
            root = y;
        } else if(x == x.parent.left)
        {
            x.parent.left = y;
        } else
        {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x)
    {
        Node y = x.left;
        x.left = y.right;
        if(y.right != TNULL)
        {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if(x.parent == null)
        {
            root = y;
        } else if(x == x.parent.right)
        {
            x.parent.right = y;
        } else
        {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public void inorderTraversal(Node node)
    {
        if(node != TNULL)
        {
            inorderTraversal(node.left);
            System.out.print(node.data + " -> ");
            inorderTraversal(node.right);
        }
    }

    public Node getRoot()
    {
        return root;
    }
}

public class Main
{
    public static void main(String[] args)
    {
        RedBlackTree tree = new RedBlackTree();

        int[] elements =
        { 20, 15, 40, 10, 50, 60, 4, 17 };
        for(int element : elements)
        {
            tree.insert(element);
        }

        System.out.print("Inorder traversal of this red-black tree: ");
        tree.inorderTraversal(tree.getRoot());
        System.out.print("NULL");
    }
}
