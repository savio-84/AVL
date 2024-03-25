public class Comparator {
  public int comparatorInt(int first, int second) {
    int aux = 0;
    if (first > second)
      aux = 1;
    else if (first == second)
      aux = 0;
    else if (first < second)
      aux = -1;
    return aux;
  }
}
