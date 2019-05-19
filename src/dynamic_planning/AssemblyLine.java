package dynamic_planning;

/**
 * 装配线调度
 * 装配线1,2   6个装配站
 *          7   9   3   4   8   4           装配站点1处理时间
 *          2   2   3   1   3   4   3       1线移动到2线的时间
 * 底盘进入
 *          4   2   1   2   2   1   1       2线移动到1线的时间
 *          8   5   6   4   5   7           专配站点2处理时间
 */
public class AssemblyLine {
    /**
     * 计算最快时间
     * 从上至下，通过计算出前一个装配站点的最快时间，以此推导出后续的站点最快时间，再由两个最后的站点终点的最快时间比较得出最优的方案
     * @param assemblyStation 装配线站点
     * @param moveTime  从该站点移动到另一站点的时间
     * @param entryTime 底盘进入时间
     * @param endTime   结束时间
     * @param num   装配线长度，即这条装配线装配站点的数量
     */
    private void fastestWay(int[][] assemblyStation, int[][] moveTime, int[] entryTime, int[] endTime, int num){
        //一个底盘从起点从装配站 S(i, j) 的最快处理时间
        int[] fastA = new int[num + 1];        //从底盘1出发
        int[] fastB = new int[num + 1];         //从底盘2出发
        //move[1][]表示装配线1上的站点,move[2][]同理
        //move[1][j]表示最快时间到达此站点的站点是从那一装配线移动来的
        int[][] moveDirection = new int[3][num + 1];
        int ans;    //保存返回的结果
        int fastestLine;  //最快的装配线

        //从各自底盘到装配线开始站点的最快时间
        fastA[1] = entryTime[1] + assemblyStation[1][1];
        fastB[1] = entryTime[2] + assemblyStation[2][1];

        for (int j=2; j<=num; j++){
            //装配线1上从第二个站点开始，各个站点的最快通过时间
            //不等式左边：不等式左边是装配线1直接通过该站点的时间，右边是装配线2移动过来的时间
            if ( fastA[j-1] + assemblyStation[1][j] <= fastB[j-1] + moveTime[2][j-1] + assemblyStation[1][j] ){
                //装配线1直接移动
                fastA[j] = fastA[j-1] + assemblyStation[1][j];
                moveDirection[1][j] = 1;     //值为1，表示移动方向是装配线1 -> 1
            }else{
                fastA[j] = fastB[j-1] + moveTime[2][j-1] + assemblyStation[1][j];
                moveDirection[1][j] = 2;     //值为2，表示移动方向装配线2 -> 1
            }

            if (fastB[j-1] + assemblyStation[2][j] <= fastA[j-1] + moveTime[1][j-1] + assemblyStation[2][j]){
                fastB[j] = fastB[j-1] + assemblyStation[2][j];
                moveDirection[2][j] = 2;
            }else{
                fastB[j] = fastA[j-1] + moveTime[1][j-1] + assemblyStation[2][j];
                moveDirection[2][j] = 1;
            }
        }

        if (fastA[num] + endTime[1] <= fastB[num] + endTime[2]){
            ans = fastA[num] + endTime[1];
            fastestLine = 1;
        }else {
            ans = fastB[num] + endTime[2];
            fastestLine = 2;
        }

        System.out.println("最快的装配线 ：" + fastestLine);
        System.out.println("最快时间： " + ans);
        printStation2(fastestLine, moveDirection, num);
    }

    /**
     * 递减顺序
     * @param fastestLine
     * @param move
     * @param num
     */
    private void printStation(int fastestLine, int[][] move, int num){
        int i = fastestLine;
        System.out.println("line" + i + ": " + "station" + num);
        for (int j=num; j>=2; j--){
            //move记录了通过此站点的前一个通过最快时间的站点
            i = move[i][j];
            System.out.println("line" + i + ": station" + (j-1));
        }
    }

    private void printStation2(int fastestLine, int[][] moveDirection, int num){

    }

    public static void main(String[] args) {
        //从 1 开始计数
        int[][] assemblyStation = new int[][]{
                {},
                {0, 7, 9, 3, 4, 8, 4},
                {0, 8, 5, 6, 4, 5, 7}
        };
        int[][] moveTime = new int[][]{
                {},
                {0, 2, 3, 1, 3, 4},
                {0, 2, 1, 2, 2, 1}
        };
        int[] entryTime = new int[]{0, 2, 4};
        int[] endTime = new int[]{0, 3, 2};
        AssemblyLine line = new AssemblyLine();
        line.fastestWay(assemblyStation, moveTime, entryTime, endTime, 6);
    }
}
