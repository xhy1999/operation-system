package xhy.entity;

/**
 * @author xuehy
 * @since 2019/4/30
 */
public class Hole {

    private int head;       //小内存块的起始地址
    private int size;       //小内存块的大小
    private boolean isFree; //小内存块的空闲状态

    public Hole(int head, int size) {
        this.head = head;
        this.size = size;
        this.isFree = true;
    }

    public Hole(int head, int size, boolean isFree) {
        this.head = head;
        this.size = size;
        this.isFree = isFree;
    }

    @Override
    public String toString() {
        return "Hole{" +
                "head=" + head +
                ", size=" + size +
                ", isFree=" + isFree +
                '}';
    }

    public int getHead() {
        return head;
    }

    public void setHead(int head) {
        this.head = head;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
