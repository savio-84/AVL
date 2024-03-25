class Main {
  public static void main(String[] args) {
    try {

      Avl tree = new Avl(10);
      
      tree.addChild(tree.root(), 15);
      tree.addChild(tree.root(), 5);
      tree.addChild(tree.root(), 16);
      tree.addChild(tree.root(), 17);
      tree.show();
      // System.out.println(tree.height(tree.root().getLeftChild())- tree.height(tree.root().getRightChild()));
      // tree.addChild(tree.root(), 17);
      // tree.showBf(tree.root());
      // System.out.println(tree.lenght);
      // System.out.println(tree.root().element());
      // System.out.println(tree.root().getLeftChild().element());
      // System.out.println(tree.root().getRightChild().element());

      // tree.show();
      // System.out.println(tree.root().getBf());
      // tree.addChild(tree.root(), 2);
      // tree.addChild(tree.root(), 1);
      // tree.addChild(tree.root(), 8);
      // tree.addChild(tree.root(), 22);
      // tree.remove(5);
      
    } catch(Exception error) {
      System.out.println(error);
    }
  }
}