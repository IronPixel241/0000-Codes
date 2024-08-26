class RedBlackTree {

    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        int data;
        Node left, right, parent;
        boolean color;

        Node(int data) {
            this.data = data;
            this.color = RED; // New nodes are always red initially
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    private Node root = null;

    // Rotate left operation
    private void rotateLeft(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;

        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.parent = node.parent;

        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        rightChild.left = node;
        node.parent = rightChild;
    }

    // Rotate right operation
    private void rotateRight(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;

        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }

        leftChild.parent = node.parent;

        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }

        leftChild.right = node;
        node.parent = leftChild;
    }

    // Fix violations after insertion
    private void fixInsertion(Node node) {
        Node parent = null;
        Node grandparent = null;

        while (node != root && node.color != BLACK && node.parent.color == RED) {
            parent = node.parent;
            grandparent = parent.parent;

            if (parent == grandparent.left) {
                Node uncle = grandparent.right;

                // Case 1: Uncle is red
                if (uncle != null && uncle.color == RED) {
                    grandparent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandparent;
                } else {
                    // Case 2: Node is right child
                    if (node == parent.right) {
                        rotateLeft(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    // Case 3: Node is left child
                    rotateRight(grandparent);
                    boolean temp = parent.color;
                    parent.color = grandparent.color;
                    grandparent.color = temp;
                    node = parent;
                }
            } else {
                Node uncle = grandparent.left;

                // Case 1: Uncle is red
                if (uncle != null && uncle.color == RED) {
                    grandparent.color = RED;
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    node = grandparent;
                } else {
                    // Case 2: Node is left child
                    if (node == parent.left) {
                        rotateRight(parent);
                        node = parent;
                        parent = node.parent;
                    }

                    // Case 3: Node is right child
                    rotateLeft(grandparent);
                    boolean temp = parent.color;
                    parent.color = grandparent.color;
                    grandparent.color = temp;
                    node = parent;
                }
            }
        }

        root.color = BLACK; // Root must always be black
    }

    // Insert a new node with given data
    public void insert(int data) {
        Node newNode = new Node(data);

        if (root == null) {
            root = newNode;
            root.color = BLACK; // Root must be black
        } else {
            Node current = root;
            Node parent = null;

            while (current != null) {
                parent = current;
                if (newNode.data < current.data) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }

            newNode.parent = parent;

            if (newNode.data < parent.data) {
                parent.left = newNode;
            } else {
                parent.right = newNode;
            }

            fixInsertion(newNode);
        }
    }

    // In-order traversal of the tree
    public void inorderTraversal(Node node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.print(node.data + " ");
            inorderTraversal(node.right);
        }
    }

    public void inorder() {
        inorderTraversal(root);
    }

    // Main method to test the Red-Black Tree
    public static void main(String[] args) {
        RedBlackTree tree = new RedBlackTree();
        tree.insert(10);
        tree.insert(20);
        tree.insert(30);
        tree.insert(15);
        tree.insert(25);
        tree.insert(5);

        System.out.println("In-order traversal of the Red-Black Tree:");
        tree.inorder(); // Should print the tree in sorted order
    }
}
