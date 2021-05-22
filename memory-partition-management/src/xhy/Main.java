package xhy;

import xhy.entity.Memory;
import xhy.logic.LPcb;

import java.util.Scanner;

/**
 * @author xuehy
 * @since 2019/4/30
 */
public class Main {

    public static void main(String[] args) {
        LPcb lPcb = new LPcb();
        Scanner sr = new Scanner(System.in);

        System.out.print("初始化内存大小:");
        System.out.println();
        Memory memory = new Memory(sr.nextInt());
        System.out.println("初始化成功!");
        while (true) {
            System.out.print("请输入操作数(0、结束进程\t1、申请内存\t2、释放内存):");
            switch (sr.nextInt()) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    System.out.print("请选择算法(1、首次适应算法\t2、循环首次适应算法\t3、最佳适应算法\t4、最坏适应算法):");
                    int algorithm = sr.nextInt();
                    System.out.print("请输入申请大小:");
                    int size = sr.nextInt();
                    switch (algorithm) {
                        case 1:
                            memory = lPcb.首次适应算法(memory, size);
                            break;
                        case 2:
                            memory = lPcb.循环首次适应算法(memory, size);
                            break;
                        case 3:
                            memory = lPcb.最佳适应算法(memory, size);
                            break;
                        case 4:
                            memory = lPcb.最坏适应算法(memory, size);
                            break;
                        default:
                            System.out.println("操作取消");
                            break;
                    }
                    lPcb.showPcbs(memory);
                    lPcb.showMemory(memory);
                    break;
                case 2:
                    System.out.print("请输入需要释放的分区id或进程id:");
                    int id = sr.nextInt();
                    System.out.println();
                    memory = memory.releaseMemory(id);
                    lPcb.showPcbs(memory);
                    lPcb.showMemory(memory);
                    break;
                default:
                    System.err.println("未知标识符");
                    break;
            }
        }
    }

}
