package dao;
public class Column {
    private int id=0,                 //时段
            time=0,                   //时区
            preMeasuring=0,           //预测量
            orders=0,                 //订单量
            grossRequirement=0,       //毛需求量
            firstPAB=0,               //PAB初值
            netRequirement=0,         //净需求量
            planned_order_receipts=0, //计划产出量
            PAB=0,                    //PAB
            planned_order_releases=0, //计划投入量
            ATP=0;                    //ATP


    @Override
    public String toString() {
        return "Column{" +
                "preMeasuring=" + preMeasuring +
                ", orders=" + orders +
                ", grossRequirement=" + grossRequirement +
                ", firstPAB=" + firstPAB +
                ", netRequirement=" + netRequirement +
                ", planned_order_receipts=" + planned_order_receipts +
                ", PAB=" + PAB +
                ", planned_order_releases=" + planned_order_releases +
                ", ATP=" + ATP +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPreMeasuring() {
        return preMeasuring;
    }

    public void setPreMeasuring(int preMeasuring) {
        this.preMeasuring = preMeasuring;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getGrossRequirement() {
        return grossRequirement;
    }

    /*public void setGrossRequirement(int grossRequirement) {this.grossRequirement = grossRequirement;}*/

    public void setGrossRequirement() {
        if (time == 0)
            grossRequirement = 0;
        else if (time == 1)
            grossRequirement = orders;
        else if (time == 2){
            if (orders>preMeasuring)
                grossRequirement = orders;
            else
                grossRequirement = preMeasuring;
        }else if (time == 3)
            grossRequirement = preMeasuring;
    }

    public int getFirstPAB() {
        return firstPAB;
    }

    public void setFirstPAB(int firstPAB) {
        this.firstPAB = firstPAB;
    }

    public void setFirstPAB(int existingStock,Column column) {
        if (id == 0)
            firstPAB = existingStock;
        else if (id==1)
            firstPAB = existingStock - grossRequirement;
        else if (id>1)
            firstPAB = column.getPAB() - grossRequirement;
    }

    public int getNetRequirement() {
        return netRequirement;
    }

    public void setNetRequirement(int netRequirement) {
        this.netRequirement = netRequirement;
    }

    public int getPlanned_order_receipts() {
        return planned_order_receipts;
    }

    public void setPlanned_order_receipts(int planned_order_receipts) {
        this.planned_order_receipts = planned_order_receipts;
    }

    public int getPAB() {
        return PAB;
    }

    public void setPAB(int PAB) {
        this.PAB = PAB;
    }

    public int getPlanned_order_releases() {
        return planned_order_releases;
    }

    public void setPlanned_order_releases(int planned_order_releases) {
        this.planned_order_releases = planned_order_releases;
    }

    public int getATP() {
        return ATP;
    }

    public void setATP(int ATP) {
        this.ATP = ATP;
    }
}
