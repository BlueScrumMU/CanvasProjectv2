
package canvasproject2;

import edu.ksu.canvas.CanvasApiFactory;
import edu.ksu.canvas.interfaces.AssignmentReader;
import edu.ksu.canvas.interfaces.AssignmentWriter;
import edu.ksu.canvas.interfaces.CourseReader;
import edu.ksu.canvas.model.Course;
import edu.ksu.canvas.model.assignment.Assignment;
import edu.ksu.canvas.oauth.NonRefreshableOauthToken;import edu.ksu.canvas.requestOptions.GetSingleAssignmentOptions;
import edu.ksu.canvas.requestOptions.ListUserCoursesOptions;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
;

/**
 *
 * @author rewil
 */
public class CanvasInterface {
    
    private final CanvasApiFactory canvas = new CanvasApiFactory("https://manchester.instructure.com");
    private String accessToken = "4633~y8cN7aTlZEvq0BA0dASsQ0ZxoyrZ918rq9FbtFASVeohhpMeqfjReBKT8EUwF2Ro";
    private NonRefreshableOauthToken token = new NonRefreshableOauthToken(accessToken);
    private final String courseID = "6574";
    private final String userID = "6343";
    
    public CanvasInterface() {
        
    }
    public CanvasInterface(String accessToken) {
        this.accessToken = accessToken;
        token = new NonRefreshableOauthToken(accessToken);
    }
    
    /**
     * Returns a list of all courses the given userID is in. 
     * Returns null if it errors. 
     * @param userID
     * @return 
     */
    public List<Course> getCourses(String userID) {
        CourseReader reader = canvas.getReader(CourseReader.class, token);
        ListUserCoursesOptions luco = new ListUserCoursesOptions(userID);
        try {
            return reader.listUserCourses(luco);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * Returns a list of all courses the stored userID (BlueScrum) is in. 
     * Returns null if it errors. 
     * @return 
     */
    public List<Course> getCourses() {
        return getCourses(userID);
    }
    
    /**
     * Returns a CourseInterface for the given courseID. 
     * Returns null if it errors. 
     * @param courseID
     * @return 
     */
    public CourseInterface getCourseInterface(String courseID) {
        try {
            return new CourseInterface(canvas, token, courseID);
        } catch (IOException ex) {
            return null;
        }
    }
    /**
     * Returns a CourseInterface for the stored courseID (Scrum Blue). 
     * Returns null if it errors. 
     * @return 
     */
    public CourseInterface getCourseInterface() {
        return getCourseInterface(courseID);
    }
    
    /**
     * Returns the given Assignment identified by CourseID and AssignmentID. 
     * Returns null if it fails. 
     * @param courseID
     * @param assignmentID
     * @return 
     */
    public Assignment getAssignment(String courseID, int assignmentID) {
        try {
            return canvas.getReader(AssignmentReader.class, token).getSingleAssignment(new GetSingleAssignmentOptions(courseID, assignmentID)).get();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    /**
     * Commits all edits on a given Assignment to Canvas. 
     * Returns false if it fails, otherwise true. 
     * @param agmt
     * @return 
     */
    public boolean flushAssignment(Assignment agmt) {
        try {
            canvas.getWriter(AssignmentWriter.class, token).editAssignment(agmt.getCourseId(), agmt);
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
    
}
