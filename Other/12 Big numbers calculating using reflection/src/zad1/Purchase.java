/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad1;


import java.beans.*;

public class Purchase {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private VetoableChangeSupport veto = new VetoableChangeSupport(this);
    String prod, data;
    double price;
    public Purchase(String towar, String status, double v) {
        this.prod=towar;
        this.data=status;
        this.price=v;
    }

    public synchronized void setPrice(double v) throws PropertyVetoException {
        double oldPrice = this.price;
        veto.fireVetoableChange("price",oldPrice,v);
        this.price=v;
        support.firePropertyChange("price",oldPrice,this.price);


    }

    public synchronized void setData(String nd) {
        String oldData = this.data;
        this.data=nd;
        support.firePropertyChange("data",oldData,nd);
    }

    public synchronized void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public synchronized void addVetoableChangeListener (VetoableChangeListener l) {
        veto.addVetoableChangeListener(l);
    }

    @Override
    public String toString () {
        return "Purchase [prod="+prod+", data="+data+", price="+price+"]";
    }
    //Purchase [prod=komputer, data=nie ma promocji, price=3000.0]
    //Change value of: data from: nie ma promocji to: w promocji
    //Change value of: price from: 3000.0 to: 2000.0
    //Purchase [prod=komputer, data=w promocji, price=2000.0]
    //Price change to: 500.0 not allowed
    //Purchase [prod=komputer, data=w promocji, price=2000.0]
}

