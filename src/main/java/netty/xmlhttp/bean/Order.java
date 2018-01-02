package netty.xmlhttp.bean;

/**
 * Created by Administrator on 2018/1/2.
 * Description: 订单实体类
 */
public class Order {
    private long orderNumber;
    private Customer customer;
    /**
     * Billing address information
     */
    private Address billTo;//开发票的地址信息
    private Shipping shipping;
    /**
     * Shipping address information.
     * if missing,the billing address is also used to the address
     */
    private Address shipTo;//运输的地址
    private Float total;

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getBillTo() {
        return billTo;
    }

    public void setBillTo(Address billTo) {
        this.billTo = billTo;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Address getShipTo() {
        return shipTo;
    }

    public void setShipTo(Address shipTo) {
        this.shipTo = shipTo;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}
