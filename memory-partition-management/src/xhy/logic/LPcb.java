package xhy.logic;

import xhy.entity.Hole;
import xhy.entity.Memory;
import xhy.entity.Pcb;

/**
 * @author xuehy
 * @since 2019/4/30
 */
public class LPcb {

    public Memory 首次适应算法(Memory memory, int size) {
        int sum = 0;
        for (int i = 0; i < memory.getHoles().size(); i++) {    //循环内存中所有分区
            sum++;
            memory.setLastFind(i);  //为循环首次适应算法设置最后寻址的下标
            Hole hole = memory.getHoles().get(i);   //获得对应的分区
            if (hole.isFree() && hole.getSize() >= size) {  //若此分区空闲且大小大于申请的大小，则申请内存
                System.out.println("查找" + sum + "次");
                return memory.getMemory(size, i, hole);
            }
        }
        System.err.println("OUT OF MEMORY!");
        return memory;
    }

    public Memory 循环首次适应算法(Memory memory, int size) {
        Hole hole = memory.getHoles().get(memory.getLastFind());
        if (hole.isFree() && hole.getSize() >= size) {  //判断最后寻址的分区的大小是否足够
            return memory.getMemory(size, memory.getLastFind(), hole);
        }
        int length = memory.getHoles().size();
        int sum = 0;    //为区分与首次适应算法循环次数所设置
        //如果不够,则从下一个分区开始循环
        for (int i = (memory.getLastFind() + 1) % length; i != memory.getLastFind(); i = (i + 1) % length) {
            sum++;
            memory.setLastFind(i);
            hole = memory.getHoles().get(i);
            if (hole.isFree() && hole.getSize() >= size) {
                System.out.println("查找" + sum + "次");
                return memory.getMemory(size, i, hole);
            }
        }
        System.err.println("OUT OF MEMORY!");
        return memory;
    }

    public Memory 最佳适应算法(Memory memory, int size)  {
        int findIndex = -1;         //最佳分区的下标
        int min = memory.getSize(); //min存储当前找到的最小的合适的分区大小
        for (int i = 0; i < memory.getHoles().size(); i++) {
            //memory.setLastFind(i);
            Hole hole = memory.getHoles().get(i);
            if (hole.isFree() && hole.getSize() >= size) {
                if (min > hole.getSize() - size){   //若当前找到的分区大小比min还要合适(剩余空间更小),则修改其值
                    min = hole.getSize() - size;
                    findIndex = i;
                }
            }
        }
        if (findIndex != -1) {  //若存在合适分区
            return memory.getMemory(size, findIndex, memory.getHoles().get(findIndex));
        }
        System.err.println("OUT OF MEMORY!");
        return memory;
    }

    public Memory 最坏适应算法(Memory memory, int size) {
        int findIndex = -1;
        int max = 0;
        for (int i = 0; i < memory.getHoles().size(); i++) {
            //memory.setLastFind(i);
            Hole hole = memory.getHoles().get(i);
            if (hole.isFree() && hole.getSize() >= size) {
                if (max < hole.getSize() - size){
                    max = hole.getSize() - size;
                    findIndex = i;
                }
            }
        }
        if (findIndex != -1) {
            return memory.getMemory(size, findIndex, memory.getHoles().get(findIndex));
        }
        System.err.println("OUT OF MEMORY!");
        return memory;
    }

    public void showMemory(Memory memory) {
        System.out.println("------------------------------------");
        System.out.println("分区编号\t分区始址\t分区大小\t空闲状态\t");
        System.out.println("------------------------------------");
        for (int i = 0; i < memory.getHoles().size(); i++){
            Hole hole = memory.getHoles().get(i);
            System.out.println(i + "\t\t" + hole.getHead() + "\t\t" + hole.getSize() + "  \t" + hole.isFree());
        }
        System.out.println("------------------------------------");
    }

    public void showPcbs(Memory memory) {
        System.out.println("------------------------------------");
        System.out.println("进程编号\t进程状态\t进程起始地址\t进程大小\t");
        System.out.println("------------------------------------");
        if (memory.getPcbs().size() > 0) {
            for (int i = 0; i < memory.getPcbs().size(); i++) {
                Pcb pcb = memory.getPcbs().get(i);
                System.out.println(pcb.getId() + "  \t" + pcb.getState() + "\t\t" + pcb.getHole().getHead() + "\t\t\t" + pcb.getHole().getSize());
            }
        } else {
            System.err.println("\t\t\t暂无进程！");
        }
        System.out.println("------------------------------------");
    }

}
