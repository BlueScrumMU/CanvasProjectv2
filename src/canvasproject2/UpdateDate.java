
package canvasproject2;

import edu.ksu.canvas.model.assignment.Assignment;
import java.time.LocalDate;
import java.util.Date;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author Ande404
 */
public class UpdateDate {
   ListView agmtsMEList;
   CheckBox enableDatePushMECheck;
   TextField datePushMEField;
   
   public UpdateDate(){
       
   }
   
   private void updateDates(Assignment a){
      
       Integer numberOfDays;
       
       try {
            numberOfDays = Integer.parseInt(datePushMEField.getText());
       }
       catch(Exception e){
           return;
       }
      
       
       //update start date
       a.setUnlockAt(DateUtils.addDays(a.getUnlockAt(),numberOfDays)); 
       
       //update lock date
       a.setLockAt(DateUtils.addDays(a.getLockAt(),numberOfDays));
       
       //update due date
       a.setDueAt(DateUtils.addDays(a.getDueAt(), numberOfDays));
       
   }
   
   
   
}
