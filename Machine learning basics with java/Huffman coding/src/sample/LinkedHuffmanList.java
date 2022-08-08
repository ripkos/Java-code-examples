package sample;

public class LinkedHuffmanList {
    LinkedHuffmanList Left;
    LinkedHuffmanList Right;
    HuffmanNode node;
    public LinkedHuffmanList(HuffmanNode n) {
        node = n;
    }
    public void insert(LinkedHuffmanList lnode) {
        if(Right!=null){
            if(lnode.node.isFirst(Right.node)){
                this.Right.Left=lnode;
                lnode.Right=this.Right;
                this.Right=lnode;
                lnode.Left=this;
            }
            else{
                this.Right.insert(lnode);
            }
        }
        else {
            lnode.Left=this;
            this.Right=lnode;
        }
    }
    public LinkedHuffmanList take() {
        LinkedHuffmanList r = this.Right;
        if (r!=null)
        if (r.Right!=null){
            r.Right.Left=this;
            this.Right=r.Right;
        }
        else {
            this.Right=null;
        }

        return r;
    }
}
