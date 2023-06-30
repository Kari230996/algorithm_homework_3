// import java.util.ArrayList;
// import java.util.List;

public class BinTree<T extends Comparable<T>> {
    Node root;

    public boolean add(T value) {
        if (root == null) {
            Node newNode = new Node(value);
            root = newNode;
            root.color = Color.Black;
            return true;
        }
        if (addNode(root, value) != null)
            return true;
        return false;
    }

    private Node addNode(Node node, T value) {
        if (node.value.compareTo(value) == 0)
            return null;
        if (node.value.compareTo(value) > 0) {
            if (node.left == null) {
                node.left = new Node(value);
                return node.left;
            }
            Node resultNode = addNode(node.left, value);
            node.left = rebalanced(node.left);
            return resultNode;
        } else {
            if (node.right == null) {
                node.right = new Node(value);
                return node.right;
            }
            Node resultNode = addNode(node.right, value);
            node.right = rebalanced(node.right);
            return resultNode;
        }
    }

    public boolean remove(T value) {
        if (!contain(value))
            return false;
        Node deleteNode = root;
        Node prevNode = root;
        while (deleteNode != null) {
            if (deleteNode.value.compareTo(value) == 0) {
                Node currentNode = deleteNode.right;
                if (currentNode == null) {
                    if (deleteNode == root) {
                        root = root.left;
                        root.color = Color.Black;
                        return true;
                    }
                    deleteNode = rebalanced(deleteNode);
                    if (deleteNode.left == null) {
                        deleteNode = null;
                        return true;
                    }
                    if (prevNode.left == deleteNode)
                        prevNode.left = deleteNode.left;
                    else
                        prevNode.right = deleteNode.left;
                    return true;
                }
                while (currentNode.left != null)
                    currentNode = currentNode.left;
                deleteNode = rebalanced(deleteNode);
                deleteNode.value = currentNode.value;
                currentNode = null;
                return true;
            }
            if (prevNode != deleteNode) {
                if (prevNode.value.compareTo(value) > 0)
                    prevNode = prevNode.left;
                else
                    prevNode = prevNode.right;
            }
            if (deleteNode.value.compareTo(value) > 0)
                deleteNode = deleteNode.left;
            else
                deleteNode = deleteNode.right;
        }
        return false;
    }

    private boolean contain(T value) {
        Node currentNode = root;
        while (currentNode != null) {
            if (currentNode.value.equals(value))
                return true;
            if (currentNode.value.compareTo(value) > 0)
                currentNode = currentNode.left;
            else
                currentNode = currentNode.right;
        }
        return false;
    }

    private Node rebalanced(Node node) {
        Node result = node;
        boolean needRebalance;
        do {
            needRebalance = false;
            if (result.right != null && result.right.color == Color.Red
                    && (result.left == null || result.left.color == Color.Black)) {
                needRebalance = true;
                result = leftRotate(result);
            }
            if (result.left != null && result.left.color == Color.Red
                    && result.left.left != null && result.left

                            .left.color == Color.Red) {
                needRebalance = true;
                result = rightRotate(result);
            }
            if (result.left != null && result.left.color == Color.Red
                    && result.right != null && result.right.color == Color.Red) {
                needRebalance = true;
                flipColors(result);
            }
        } while (needRebalance);
        return result;
    }

    private void flipColors(Node node) {
        node.color = Color.Red;
        node.left.color = Color.Black;
        node.right.color = Color.Black;
    }

    private Node leftRotate(Node node) {
        Node right = node.right;
        node.right = right.left;
        right.left = node;
        right.color = node.color;
        node.color = Color.Red;
        return right;
    }

    private Node rightRotate(Node node) {
        Node left = node.left;
        node.left = left.right;
        left.right = node;
        left.color = node.color;
        node.color = Color.Red;
        return left;
    }

    private class Node {
        T value;
        Color color;
        Node left;
        Node right;

        // Node() {
        // color = Color.Red;
        // }

        Node(T _value) {
            this.value = _value;
            left = null;
            right = null;
            color = Color.Red;
        }
    }

    enum Color {
        Red, Black
    }

    public void print() {
        printNode(root);
    }

    private void printNode(Node node) {
        if (node == null) {
            return;
        }

        printNode(node.left);
        System.out.println(node.value);
        printNode(node.right);
    }

}
