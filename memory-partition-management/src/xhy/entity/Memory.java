package xhy.entity;

import java.util.LinkedList;

/**
 * @author xuehy
 * @since 2019/4/30
 */
public class Memory {

    private int size;                       //内存块大小
    private int lastFind;                   //上次寻址结束的下标
    private LinkedList<Pcb> pcbs;           //记录内存块中进程的双向链表
    private LinkedList<Hole> holes;         //记录内存块分区的双向链表
    private static final int MIN_SIZE = 5;  //最小剩余分区大小

    public Memory getMemory(int size, int location, Hole hole) {    //size为申请大小 location为分配分区的下标 hole为location对应的分区
        if (hole.getSize() - size >= MIN_SIZE) {                    //若分配后当前分区剩余大小大于最小分区大小，则把当前分区分为两块
            Hole newHole = new Hole(hole.getHead() + size, hole.getSize() - size);
            holes.add(location + 1, newHole);
            hole.setSize(size);
        }
        pcbs.add(new Pcb(10000 + (int)(89999 * Math.random()), 1, hole));   //模拟添加一个就绪状态的进程
        hole.setFree(false);    //设置当前分区为非空闲状态
        System.out.println("成功分配大小为" + size + "的内存");
        return this;
    }

    public Memory releaseMemory(int id) {
        Pcb pcb = null;             //记录此id对应进程(忽略进程id与分区id相同，但进程不同的情况哈)
        if (id >= holes.size()) {   //若id大于holes的表长度，则需要判断此id是否是进程id
            boolean flag = false;
            for (int i = 0; i < pcbs.size(); i++) { //循环比对此id是否是pcbs链表中进程的id
                if (pcbs.get(i).getId() == id) {
                    pcb = pcbs.get(i);
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                System.err.println("无此分区:" + id);
                return this;
            }
        }
        if (pcb != null) {  //若是通过进程id释放内存，则用下列循环获取进程对应的hole对应holes链表的下标(获取分区id)
            for (int i = 0; i < holes.size(); i++) {
                Hole hole = holes.get(i);
                if ((pcb.getHole().getSize() == hole.getSize()) && (pcb.getHole().getHead() == hole.getHead())) {
                    id = i;
                    break;
                }
            }
        }
        Hole hole = holes.get(id);  //此id为分区id
        if (hole.isFree()) {
            System.out.println("此分区空闲,无需释放:\t" + id);
            return this;
        }
        //用分区id释放pcb
        for (int i = 0; i < pcbs.size(); i++) {
            Pcb pcb2 = pcbs.get(i);
            if ((pcb2.getHole().getSize() == hole.getSize()) && (pcb2.getHole().getHead() == hole.getHead())) {
                pcbs.remove(i);
                break;
            }
        }
        //如果回收分区不是尾分区且后一个分区为空闲, 则与后一个分区合并
        if (id < holes.size() - 1 && holes.get(id + 1).isFree()) {
            Hole nextHole = holes.get(id + 1);
            hole.setSize(hole.getSize() + nextHole.getSize());
            holes.remove(nextHole);
        }
        //如果回收分区不是首分区且前一个分区为空闲, 则与前一个分区合并
        if (id > 0 && holes.get(id - 1).isFree()) {
            Hole lastHole = holes.get(id - 1);
            lastHole.setSize(hole.getSize() + lastHole.getSize());
            holes.remove(id);
            id--;
        }
        holes.get(id).setFree(true);
        System.out.println("内存回收成功!");
        return this;
    }

    public Memory(int size) {
        this.size = size;
        this.pcbs = new LinkedList<>();
        this.holes = new LinkedList<>();
        holes.add(new Hole(0, size));
    }

    @Override
    public String toString() {
        return "Memory{" +
                "size=" + size +
                ", holes=" + holes.toString() +
                '}';
    }

    public int getLastFind() {
        return lastFind;
    }

    public void setLastFind(int lastFind) {
        this.lastFind = lastFind;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public LinkedList<Hole> getHoles() {
        return holes;
    }

    public LinkedList<Pcb> getPcbs() {
        return pcbs;
    }

    public void setHoles(LinkedList<Hole> holes) {
        this.holes = holes;
    }

    public static int getMinSize() {
        return MIN_SIZE;
    }

}
