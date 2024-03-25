import java.util.ArrayList;

public class Avl {
  private Node root;
  public int lenght;
  private Comparator comparator;

  public Avl(int value) {
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

  private void simpleLeftRotation(Node node) {
    // guarde a subárvore direita
    Node rightSubTree = node.getRightChild(); // 16

    // Troque a subárvore guardada pela subárvore esquerda da árvore guardada
    if (rightSubTree.hasLeftChild()) {
      node.setRightChild(rightSubTree.getLeftChild());
      rightSubTree.getLeftChild().setParent(node);
      rightSubTree.setLeftChild(null);
    } else {
      node.setRightChild(null);
    }

    // Ponha na subárvore esquerda da subárvore guardada a árvore restante.
    // System.out.println(node.getRightChild().element());
    rightSubTree.setLeftChild(node);
    if (node.parent() != null) {
      if (node.parent().getLeftChild() == node) 
        node.parent().setLeftChild(rightSubTree);
      else
        node.parent().setRightChild(rightSubTree);
    }
    
    rightSubTree.setParent(node.parent());
    node.setParent(rightSubTree);

    int FB_B = node.getBf();
    int FB_A = 0;

    if (node.getRightChild() != null) 
      FB_A = node.getRightChild().getBf();
    
    int FB_B_novo= FB_B + 1 - Math.min(FB_A, 0);
    int FB_A_novo= FB_A + 1 + Math.max(FB_B_novo, 0);

    node.setBf(FB_B_novo);
    rightSubTree.setBf(FB_A_novo);
    
    if (node == this.root) {
      this.root = rightSubTree;
    }
  }

  private void simpleRightRotation(Node node) {
    // guarde a subárvore esquerda
    Node leftSubTree = node.getLeftChild();

    // Troque a subárvore guardada pela subárvore direita da árvore guradada.
    node.setLeftChild(leftSubTree.getRightChild());
    leftSubTree.setParent(node);
    leftSubTree.setLeftChild(null);

    // Ponha na subárvore direita da subárvore guardada a árvore restante.
    leftSubTree.setRightChild(node);
    if (node.parent().getLeftChild() == node) 
      node.parent().setLeftChild(leftSubTree);
    else 
      node.parent().setRightChild(leftSubTree);
    
    leftSubTree.setParent(node.parent());
    node.setParent(leftSubTree);

    int FB_B = node.getBf();
    int FB_A = node.getLeftChild().getBf();

    int FB_B_novo= FB_B - 1 - Math.max(FB_A, 0);
    int FB_A_novo= FB_A - 1 + Math.min(FB_B_novo, 0);

    node.setBf(FB_B_novo);
    leftSubTree.setBf(FB_A_novo);
  }

  private void doubleLeftRotation(Node node) {
    simpleRightRotation(node.getRightChild());
    simpleLeftRotation(node);
  }

  private void doubleRightRotation(Node node) {
    simpleLeftRotation(node.getLeftChild());
    simpleRightRotation(node);
  }

  public void insertUpdateBf(Node node) {
    // if (node.parent() != null) {
    //   Node parent = node.parent();
    //   if (parent.getLeftChild() == node) 
    //     parent.setBf(parent.getBf() + 1);
    //   else if (parent.getRightChild() == node)
    //     parent.setBf(parent.getBf() - 1);
      
    //   if (parent.getBf() == 2) {
    //     if (parent.getLeftChild() != null) {
    //       if (parent.getLeftChild().getBf() >= 0) simpleRightRotation(parent);
    //       else doubleRightRotation(parent);

    //       int newBf = this.height(parent.getLeftChild()) - this.height(parent.getRightChild());
    //       parent.setBf(newBf);
    //     }        
    //   } else if (parent.getBf() == -2) {
    //     if (parent.getRightChild() != null) {
    //       if (parent.getRightChild().getBf() <= 0) simpleLeftRotation(parent);
    //       else doubleLeftRotation(parent);

    //       int newBf = this.height(parent.getLeftChild()) - this.height(parent.getRightChild());
    //       parent.setBf(newBf);
    //     }
    //   }

    //   if (parent.getBf() != 0)
    //     insertUpdateBf(parent);
    // }
    
    Node parent = node.parent();
    if (parent.getLeftChild() == node) {
      parent.setBf(parent.getBf() + 1);
      // System.out.println("Adicionou do lado esquerdo");
    } else if (parent.getRightChild() == node) {
      parent.setBf(parent.getBf() - 1);
      // System.out.println("Adicionou do lado direito");
    }

    // if (parent.getBf() == 2) {
    //   if (parent.getLeftChild().getBf() >= 0) simpleRightRotation(parent);
    //   else doubleRightRotation(parent);
    // }
    if (parent.getBf() == -2) {
      if (parent.getRightChild().getBf() <= 0) {
        simpleLeftRotation(parent);
      } else {
        doubleLeftRotation(parent);
      }
      // if (parent.getRightChild().getBf() <= 0) simpleLeftRotation(parent);
      // else doubleLeftRotation(parent);
    }

    
    if (parent.parent() != null) {
      if (parent.parent().getBf() != 0) {
        insertUpdateBf(parent);
      }
    }
  }

  public void addChild(Node node, int value) {
    // se for menor que o valor do nó
    if (value < node.element()) {
      // se o nó tiver filho esquerdo
      if (node.hasLeftChild()) {
        addChild(node.getLeftChild(), value);
      // se o nó não tiver filho esquerdo
      } else {
        Node newNode = new Node(node, value);
        node.setLeftChild(newNode);
        this.lenght++;
        insertUpdateBf(newNode);
      }
    // se for maior que o valor do nó
    } else if (value > node.element()) {
      // se tiver filho direito
      if (node.hasRightChild()) {
        addChild(node.getRightChild(), value);
      } else {
        Node newNode = new Node(node, value);
        node.setRightChild(newNode);
        this.lenght++;
        insertUpdateBf(newNode);
      }
    }
  }

  public void showBf(Node node) {
    ArrayList<Node> array = this.inOrder(node, new ArrayList<Node>());
    for (int i = 0; i<array.size(); i++) {
      System.out.println(array.get(i).getBf());
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
      int height = 1;
      if ((node.getLeftChild() != null) && (node.getRightChild() != null))
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
