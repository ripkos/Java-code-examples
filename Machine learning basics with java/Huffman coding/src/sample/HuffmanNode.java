package sample;

public class HuffmanNode
{
    public HuffmanNode Left, Right;
    public String text;
    int weight;
    public HuffmanNode(String text, int waga) {
        this.text=text;
        this.weight=waga;
    }

    public HuffmanNode(HuffmanNode node1, HuffmanNode node2) {
        if (node1.isFirst(node2)) {
            text=node1.text+node2.text;
            Left=node1;
            Right=node2;
        }
        else {
            text=node2.text+node1.text;
            Left=node2;
            Right=node1;
        }
        weight=node1.weight+node2.weight;
    }

    public boolean isLeaf() {
        return Left==null && Right==null;
    }
    public boolean isFirst(HuffmanNode node) {
        if (this.weight==node.weight) {
            return this.text.compareTo(node.text) <=0;
        }
        else {
            return this.weight<node.weight;
        }
    }
}
