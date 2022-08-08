/**
 *
 *  @author Poskrypko Maksym S20865
 *
 */

package zad1;


import java.beans.PropertyVetoException;

public class Main {
  public static void main(String[] args) {

    Purchase purch = new Purchase("komputer", "nie ma promocji", 3000.00);
    System.out.println(purch);

    // --- tu należy dodać odpowiedni kod
    purch.addPropertyChangeListener( pcl -> {
      String pname = pcl.getPropertyName();
      Object oldv = pcl.getOldValue();
      Object newv = pcl.getNewValue();
      System.out.println("Change value of: "+ pname +" from: "+oldv+" to: " +newv);

    });
    purch.addVetoableChangeListener( evt -> {
      Object newv = evt.getNewValue();
      Object oldv = evt.getOldValue();
      String propname = evt.getPropertyName();
      if ((double)newv<1000) {
        throw new PropertyVetoException("Price change to: "+newv+" not allowed", evt);
      }
    });
    // ----------------------------------

    try {
      purch.setData("w promocji");
      purch.setPrice(2000.00);
      System.out.println(purch);

      purch.setPrice(500.00);

    } catch (PropertyVetoException exc) {
      System.out.println(exc.getMessage());
    }
    System.out.println(purch);

  }
}
