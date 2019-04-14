package project;
import dao.Column;
public class mps {
    /*代码缺陷
    * 1.只能处理提前期为1的mps表格，因为其中少了计划接收量这一基本数量
    * 2.只能处理时区设定为需求时区：{1,2} 计划时区：{3,4,5,6} 预测时区：{7,8,9,10}的mps横式报表
    * 3.时间复杂度高
    * 4.代码复用性差
    * 5.页面...
    * */
    public static void main(String[] args) {
        int existingStock = 120,                  //现有库存量
        safetyStock = 20,                        //安全库存量
        lotSize = 160,                            //生产批量
        batchIncrement = 160,                     //批量增量
        leadtime = 1;                           // 提前期
        int time1[]={1,2},time2[]={3,4,5,6},time3[]={7,8,9,10};//时区
//        int orders[] = {160,170,180,130,170,160,150,190,200,210};         //订单量
//        int preMeasuring[] ={150,150,150,150,160,160,160,200,200,200};   //预测量
        int preMeasuring[] ={70,70,70,70,70,80,80,80,80,80};   //预测量
        int orders[] = {100,90,80,60,70,90,50,100,90,70};         //订单量

        Column column[] = new Column[11];
        //column[]初始化（把时区和时段设置好）
        for (int i = 0;i<column.length;i++){
            column[i] = new Column();
            column[i].setId(i);
            if (i==0)
                column[i].setTime(0);
            else if (i==1||i==2)
                column[i].setTime(1);
            else if (i==3||i==4||i==5||i==6)
                column[i].setTime(2);
            else if (i==7||i==8||i==9||i==10)
                column[i].setTime(3);
        }
        //把preMeasuring[]和orders[]存进column[]中
        column[0].setPreMeasuring(0);
        column[0].setOrders(0);
        for (int i = 0;i<column.length-1;i++){
            column[i+1].setPreMeasuring(preMeasuring[i]);
            column[i+1].setOrders(orders[i]);
        }
        //对column中的每一列进行计算
        for (int i = 0;i<column.length;i++){
            //对column中的毛需求量进行计算
            column[i].setGrossRequirement();
            //对column中的PAB初值进行计算
            if (i == 0)
                column[i].setFirstPAB(existingStock);
            else if (i==1)
                column[i].setFirstPAB(existingStock - column[i].getGrossRequirement());
            else if (i>1)
                column[i].setFirstPAB(column[i-1].getPAB() - column[i].getGrossRequirement());
            //对column中的净需求量进行计算
            if (column[i].getFirstPAB()>=safetyStock)
                column[i].setNetRequirement(0);
            else
                column[i].setNetRequirement(safetyStock-column[i].getFirstPAB());
            //对column中的计划产出量进行计算
            if (column[i].getNetRequirement()>0){
                for (int j = 0;column[i].getNetRequirement()>column[i].getPlanned_order_receipts();j++)
                    column[i].setPlanned_order_receipts(j*lotSize);
            }else
                column[i].setPlanned_order_receipts(0);
            //对column中的PAB进行计算
            if (i<=0)
                column[i].setPAB(0);
            else if (i==1)
                column[i].setPAB(column[i].getFirstPAB()+column[i].getPlanned_order_receipts());
            else if (i>1)
                column[i].setPAB(column[i-1].getPAB()-column[i].getGrossRequirement()+column[i].getPlanned_order_receipts());
        }
        //对column中的所有Column进行计算
        for (int i = 0;i<column.length;i++){
            //对column中的计划投入量和ATP进行计算
            if (i==column.length-1){
                column[i].setPlanned_order_releases(0);
                if (column[i].getPlanned_order_receipts()!=0)
                    column[i].setATP(column[i].getPlanned_order_receipts()-column[i].getOrders());
                else
                    column[i].setATP(0);
            }else {
                column[i].setPlanned_order_releases(column[i+1].getPlanned_order_receipts());
                if (column[i].getPlanned_order_receipts()!=0){
                    int sum=column[i].getOrders();
                    for (int j=i+1 ;column[j].getPlanned_order_receipts()==0&&j!=column.length-1;j++){
                        sum+=column[j].getOrders();
                    }
                    if (i==1)
                        column[i].setATP(column[i].getPlanned_order_receipts()+existingStock-sum);
                    else column[i].setATP(column[i].getPlanned_order_receipts()-sum);
                }else {
                    if (i==1)
                        column[i].setATP(existingStock-column[i].getOrders());
                    else
                        column[i].setATP(0);
                }
            }
        }
        for (int i=0;i<column.length;i++)
            System.out.print(column[i].getPreMeasuring()+"\t");
        System.out.println("");
        for (int i=0;i<column.length;i++)
            System.out.print(column[i].getOrders()+"\t");
        System.out.println("");
        for (int i=0;i<column.length;i++)
            System.out.print(column[i].getGrossRequirement()+"\t");
        System.out.println("");
        for (int i=0;i<column.length;i++)
            System.out.print(column[i].getFirstPAB()+"\t");
        System.out.println("");
        for (int i=0;i<column.length;i++)
            System.out.print(column[i].getNetRequirement()+"\t");
        System.out.println("");
        for (int i=0;i<column.length;i++)
            System.out.print(column[i].getPlanned_order_receipts()+"\t");
        System.out.println("");
        for (int i=0;i<column.length;i++)
            System.out.print(column[i].getPAB()+"\t");
        System.out.println("");
        for (int i=0;i<column.length;i++)
            System.out.print(column[i].getPlanned_order_releases()+"\t");
        System.out.println("");
        for (int i=0;i<column.length;i++)
            System.out.print(column[i].getATP()+"\t");
        System.out.println("");
    }
}
