import java.util.ArrayList;

public class BinarySearchTree {
  private Node root;
  private int lenght;
  private Comparator comparator;

  public BinarySearchTree(int value) {
    this.root = new Node(null, value);
    this.lenght = 1;
    this.comparator = new Comparator();
  }

  public Node root() {
    return this.root;
  }

  public boolean isEmpty() {
    return this.lenght > 0;
  }

  public Node parent(Node node) throws InvalidNodeException {
    if (node.parent() == null) throw new InvalidNodeException();
    return node.parent();
  }

  public boolean isInternal(Node node) {
    return node.hasLeftChild() || node.hasRightChild();
  }

  public boolean isExternal(Node node) {
    return !node.hasLeftChild() && !node.hasRightChild();
  }

  public boolean isRoot(Node node) {
    return this.root == node;
  }

  public Node leftChild(Node node) {
    return node.getLeftChild();
  }

  public Node rightChild(Node node) {
    return node.getRightChild();
  }

  public void addChild(Node node, int value) {
    int nodeElement = node.element();
    int auxComparator = comparator.comparatorInt((int) nodeElement, (int) value);
    // se for menor que o valor do nó
    if (auxComparator == 1) {
      // se o nó tiver filho esquerdo
      if (node.hasLeftChild()) {
        addChild(node.getLeftChild(), value);
      // se o nó não tiver filho esquerdo
      } else {
        Node newNode = new Node(node, value);
        node.setLeftChild(newNode);
        this.lenght++;
      }
    // se for maior que o valor do nó
    } else if (auxComparator == -1) {
      // se tiver filho direito
      if (node.hasRightChild()) {
        addChild(node.getRightChild(), value);
      } else {
        Node newNode = new Node(node, value);
        node.setRightChild(newNode);
        this.lenght++;
      }
    }
  }

  public Node search(Node node, int value) throws InvalidNodeException {
    if (node != null) {
      int nodeValue = node.element();
      int auxComparator = comparator.comparatorInt((int)value, (int) nodeValue);
      // se o valor buscado for menor que o do nó
      if (auxComparator == -1) {
        return search(node.getLeftChild(), value);
      // se for igual
      } if (auxComparator == 0) {
        return node;
      // se for maior
      } else {
        return search(node.getRightChild(), value);
      }
    } else {
      throw new InvalidNodeException();
    }
  }

  public int remove(int value) throws InvalidNodeException {
    // nó original do valor
    Node aux1 = search(this.root, value);

    // se não for encontrado na arvore, retorna um erro
    if (aux1 == null)
      throw new InvalidNodeException();

    // Se o valor encontrado tiver um filho direito
    else if (aux1.hasRightChild()) {
      // se o filho direito tiver um filho esquerdo
      if (aux1.getRightChild().hasLeftChild()) {
        //Pegar o filho direito do último filho esquerdo
        Node aux2 = auxRemove(aux1);
        // redefine o filho direito do último filho esquerdo da sub arvore
        aux2.setParent(aux1.parent());
        aux2.setLeftChild(aux1.getLeftChild());
        aux2.setRightChild(aux1.getRightChild());

        // Define que o pai do filho direito do valor a ser removido  será o filho esquerdo do valor a ser removido
        aux1.getRightChild().setParent(aux1.getLeftChild());

        // Compara o valor do elemento com o seu pai
        int auxComparator = comparator.comparatorInt((int) aux1.element(), (int)aux1.parent().element());

        // caso seja maior
        if (auxComparator == 1)
          aux1.parent().setRightChild(aux2);
        else 
          aux1.parent().setLeftChild(aux2);
        this.lenght--;
        return aux1.element();

      // se o filho direito não tiver um filho esquerdo
      } else {
        aux1.getRightChild().setParent(aux1.parent());
        aux1.getRightChild().setLeftChild(aux1.getLeftChild());
        aux1.getRightChild().setRightChild(aux1.getRightChild().getRightChild());

        if (aux1.getLeftChild() != null) 
          aux1.getLeftChild().setParent(aux1.getRightChild());
        int auxComparator = comparator.comparatorInt((int) aux1.element(), (int) aux1.parent().element());
        if (auxComparator == 1) 
          aux1.parent().setRightChild(aux1.getRightChild());
        else 
          aux1.parent().setLeftChild(aux1.getRightChild());
        this.lenght--;
        return aux1.element();
      }
      // se o valor encontrado tiver um filho esquerdo...
    } else if (aux1.hasLeftChild()) {
      aux1.getLeftChild().setParent(aux1.parent());
      aux1.getLeftChild().setLeftChild(aux1.getLeftChild());
      aux1.getLeftChild().setRightChild(aux1.getRightChild());

      int auxComparator = comparator.comparatorInt((int) aux1.element(), (int) aux1.parent().element());
      if (auxComparator == 1)
        aux1.parent().setRightChild(aux1.getLeftChild());
      else 
        aux1.parent().setLeftChild(aux1.getLeftChild());
      this.lenght--;
      return aux1.element();
    // caso não tenha filhos
    } else {
      Node aux1Parent = aux1.parent();
      int aux2 = aux1.element();
      if (aux1Parent.getLeftChild().element() == aux1.element()) 
        aux1Parent.setLeftChild(null);
      else 
        aux1Parent.setRightChild(null);
      this.lenght--;
      return aux2;
    }
  }

  private Node auxRemove(Node node) {
    // Verifica se tem o lado esquerdo
    if (node.hasLeftChild()) {
      return auxRemove(node.getLeftChild());
    }
    return node;
  }

  public int depth(Node node) {
    return auxDepth(node);
  }

  private int auxDepth(Node node) {
    if (node == this.root) 
      return 0;
    else
      return 1 + auxDepth(node.parent()); 
  }

  public int height(Node node) {
    return auxHeight(node);
  }

  private int auxHeight(Node node) {
    if (node == null) 
      return 0;
    else if (isExternal(node))
      return 0;
    else {
      int height = 0;
      height = Math.max(auxHeight(node.getLeftChild()), auxHeight(node.getRightChild()));
      return height+1;
    }
  }

  public void show() {
    int[][] matriz = new int[this.height(root)+1][this.lenght];

    ArrayList<Node> order = new ArrayList<Node>(this.lenght);
    order = this.inOrder(root, order);

    for (int i = 0; i<this.lenght; i++)
      matriz[this.depth(order.get(i))][i] = (int) order.get(i).element();

    for (int line = 0; line<=this.height(root); line++) {
      for(int column = 0; column<this.lenght; column++) {
        if (matriz[line][column] == 0)
          System.out.print(" ");
        else
          System.out.print(matriz[line][column]);
      }
      System.out.println("");
    }

  }

  public ArrayList<Node> inOrder(Node node, ArrayList<Node> arr) {
    if (this.isInternal(node))
      if (node.hasLeftChild())
        this.inOrder(node.getLeftChild(), arr);
    arr.add(node);
    if(this.isInternal(node))
      if (node.hasRightChild())
        this.inOrder(node.getRightChild(), arr);

    return arr;
  }
}

