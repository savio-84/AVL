public class Node {
  private int element;
  private Node father;
  private Node left;
  private Node right;
  private int bf;

  public Node(Node father, int value) {
    this.father = father;
    this.element = value;
    this.left = null;
    this.right = null;
    this.bf = 0;
  }

  public int element() {
    return this.element;
  }

  public void setElement(int value) {
    this.element = value;
  }

  public Node parent() {
    return this.father;
  }

  public void setParent(Node value) {
    this.father = value;
  }

  public Node getLeftChild() {
    return this.left;
  }

  public void setLeftChild(Node value) {
    this.left = value;
  }

  public Node getRightChild() {
    return this.right;
  }

  public void setRightChild(Node value) {
    this.right = value;
  }

  public boolean hasLeftChild() {
    return (this.left != null);
  }

  public boolean hasRightChild() {
    return (this.right != null);
  }

  public int getBf() {
    return this.bf;
  }

  public void setBf(int value) {
    this.bf = value;
  }
}
