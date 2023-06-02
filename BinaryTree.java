public class BinaryTree {
    private TreeNode root;

    private enum Color {BLACK, RED}

    private class TreeNode {
        private int value;
        private TreeNode leftChild;
        private TreeNode rightChild;
        private Color color;

    }

    private boolean find(int value) {
        TreeNode currentNode = root;
        while (currentNode != null) {
            if (currentNode.value == value)
                return true;
            else if (value > currentNode.value) {
                currentNode = currentNode.rightChild;
            } else {
                currentNode = currentNode.leftChild;
            }
        }
        return false;
    }

    private boolean add(int value) {
        if (root == null) {
            root = new TreeNode();
            root.value = value;
            return true;
        } else {
            boolean result = addNewNode(root, value);
            rebalance(root);
            return result;
        }
    }

    private boolean addNewNode(TreeNode treeNode, int value) {
        if (treeNode.value > value) {
            if (treeNode.leftChild == null) {
                treeNode.leftChild.value = value;
                treeNode.leftChild.color = Color.RED;
                rebalance(treeNode.leftChild);
                return true;
            } else {
                boolean result = addNewNode(treeNode.leftChild, value);
                return result;
            }
        } else if (treeNode.value < value) {
            if (treeNode.rightChild == null) {
                treeNode.rightChild.value = value;
                treeNode.rightChild.color = Color.RED;
                rebalance(treeNode.rightChild);
                return true;
            } else {
                boolean result = addNewNode(treeNode.rightChild, value);
                return result;
            }
        } else
            return false;
    }

    private TreeNode rebalance(TreeNode node) {
        boolean flag = true;
        while (!flag) {
            flag = true;
            if (root.color == Color.RED) {
                flag = false;
                root.color = Color.BLACK;
            }
            if (node.rightChild != null &&
                    node.leftChild != null &&
                    node.rightChild.color == Color.RED &&
                    node.leftChild.color == Color.RED) {
                flag = false;
                swapColor(node);
            }
            if (node.rightChild != null &&
                    node.rightChild.color == Color.RED &&
                    (node.leftChild == null ||
                    node.leftChild.color == Color.BLACK)) {
                flag = false;
                node = smallRightFlip(node);
            }
            if (node.leftChild != null &&
                    node.leftChild.color == Color.RED &&
                    node.leftChild.leftChild != null &&
                    node.leftChild.leftChild.color == Color.RED) {
                flag = false;
                node = smallLeftFlip(node);
            }
        }
        return node;
    }

    private void swapColor(TreeNode node) {
        node.color = Color.RED;
        node.leftChild.color = Color.BLACK;
        node.rightChild.color = Color.BLACK;
    }

    private TreeNode smallRightFlip(TreeNode node) {
        TreeNode rightElement = node.rightChild;
        TreeNode betweenElement = rightElement.leftChild;
        rightElement.leftChild = node;
        node.rightChild = betweenElement;
        rightElement.color = Color.BLACK;
        node.color = Color.RED;
        return rightElement;
    }

    private TreeNode smallLeftFlip(TreeNode node) {
        TreeNode leftElement = node.leftChild;
        TreeNode betweenElement = leftElement.rightChild;
        leftElement.rightChild = node;
        node.leftChild = betweenElement;
        leftElement.color = Color.BLACK;
        node.color = Color.RED;
        return leftElement;
    }
}