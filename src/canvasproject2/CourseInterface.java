package canvasproject2;

import edu.ksu.canvas.CanvasApiFactory;
import edu.ksu.canvas.interfaces.AssignmentReader;
import edu.ksu.canvas.interfaces.AssignmentWriter;
import edu.ksu.canvas.interfaces.CourseReader;
import edu.ksu.canvas.interfaces.CourseWriter;
import edu.ksu.canvas.interfaces.UserReader;
import edu.ksu.canvas.interfaces.UserWriter;
import edu.ksu.canvas.model.Course;
import edu.ksu.canvas.model.User;
import edu.ksu.canvas.model.assignment.Assignment;
import edu.ksu.canvas.oauth.OauthToken;
import edu.ksu.canvas.requestOptions.GetSingleCourseOptions;
import edu.ksu.canvas.requestOptions.GetUsersInCourseOptions;
import edu.ksu.canvas.requestOptions.ListCourseAssignmentsOptions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author rewil
 */
public class CourseInterface {

    private final String courseID;

    //<editor-fold desc="Reader/Writer Vars">
    private final CourseReader courseReader;
    private final CourseWriter courseWriter;
    private final AssignmentReader agmtReader;
    private final AssignmentWriter agmtWriter;
    private final UserReader userReader;
    private final UserWriter userWriter;
    //</editor-fold>
    
    private final Course course;
    private List<Assignment> assignments;
    private List<Assignment> selectedAgmts = new ArrayList<>();

    private List<User> users;

    public CourseInterface(CanvasApiFactory factory, OauthToken token, String courseID) throws IOException {
        this.courseID = courseID;

        courseReader = factory.getReader(CourseReader.class, token);
        courseWriter = factory.getWriter(CourseWriter.class, token);
        agmtReader = factory.getReader(AssignmentReader.class, token);
        agmtWriter = factory.getWriter(AssignmentWriter.class, token);
        userReader = factory.getReader(UserReader.class, token);
        userWriter = factory.getWriter(UserWriter.class, token);

        GetSingleCourseOptions gsco = new GetSingleCourseOptions(courseID);
        course = courseReader.getSingleCourse(gsco).get();

        ListCourseAssignmentsOptions lcao = new ListCourseAssignmentsOptions(courseID);
        assignments = agmtReader.listCourseAssignments(lcao);
        selectAllAssignments();

        GetUsersInCourseOptions guico = new GetUsersInCourseOptions(courseID);
        users = userReader.getUsersInCourse(guico);
    }

    //<editor-fold desc="Course Information/Manipulation">
    
    /**
     * Returns the name of the course. Returns null if course is null.
     *
     * @return
     */
    public String getName() {
        if (course == null) {
            return "null";
        }
        return course.getName();
    }

    /**
     * Sets the name of the course. Returns false if course is null, otherwise true.
     *
     * @param name
     * @return
     */
    public boolean setName(String name) {
        if (course == null) {
            return false;
        }
        course.setName(name);
        return true;
    }

    /**
     * Returns the ID of this course. Returns -1 if the course is null.
     *
     * @return
     */
    public int getID() {
        if (course == null) {
            return -1;
        }
        return course.getId();
    }

    /**
     * Returns the Start Date of this course. Returns null if course is null.
     *
     * @return
     */
    public Date getStartDate() {
        if (course == null) {
            return null;
        }
        return course.getStartAt();
    }

    /**
     * Sets the Start Date of this course. Returns false if the course is null, otherwise true.
     *
     * @param start
     * @return
     */
    public Boolean setStartDate(Date start) {
        if (course == null) {
            return false;
        }
        course.setStartAt(start);
        return true;
    }

    /**
     * Returns the End Date of this course. Returns null if the course is null.
     *
     * @return
     */
    public Date getEndDate() {
        if (course == null) {
            return null;
        }
        return course.getEndAt();
    }

    /**
     * Sets the End Date of this course. Returns false if the course is null, otherwise true.
     *
     * @param end
     * @return
     */
    public boolean setEndDate(Date end) {
        if (course == null) {
            return false;
        }
        course.setEndAt(end);
        return true;
    }

    /**
     * Returns a list of the Users in this course. Returns null if course is null;
     *
     * @return
     */
    public List<User> getUsers() {
        if (course == null) {
            return null;
        }
        return users;
    }

    /**
     * Returns a list of Assignments in this course. Returns null if course is null.
     *
     * @return
     */
    public List<Assignment> getAssignments() {
        if (course == null) {
            return null;
        }
        return assignments;
    }

    /**
     * Returns the list of currently selected Assignments.
     *
     * @return
     */
    public List<Assignment> getSelectedAssignments() {
        return selectedAgmts;
    }

    //</editor-fold>
  //------------------------------------------------------------------------------------
    //<editor-fold desc="Assignment Selection">
    
    /**
     * Selects all Assignments in the course.
     */
    public void selectAllAssignments() {
        selectedAgmts = new ArrayList<>();
        selectedAgmts.addAll(getAssignments());
    }

    /**
     * Selects all Assignments that are in the given groupID
     *
     * @param groupID
     */
    public void selectByAssignmentGroup(String groupID) {
        selectedAgmts = new ArrayList<>();
        for (Assignment a : getAssignments()) {
            if (a.getGroupCategoryId().equals(groupID)) {
                selectedAgmts.add(a);
            }
        }
    }

    /**
     * Selects all Assignments that match a given Regex.
     *
     * @param regex
     */
    public void selectByPattern(String regex) {
        selectedAgmts = new ArrayList<>();
        for (Assignment a : getAssignments()) {
            if (a.getName().matches(regex)) {
                selectedAgmts.add(a);
            }
        }
    }
    
    //</editor-fold>
  //------------------------------------------------------------------------------------
    //<editor-fold desc="Assignment Manipulation">
    
    @Deprecated
    /**
     * Removes the Due Date, Unlock Date, and Lock Date from all selected Assignments
     * Not currently Functioning. 
     */
    public void clearSelectedDates() {
        for (Assignment a : getSelectedAssignments()) {
            clearDates(a);
        }
    }
    @Deprecated
    /**
     * Removes the Due Date, Unlock Date, and Lock Date from the given Assignment.
     * Not currently Functioning. 
     * 
     * @param a
     */
    public void clearDates(Assignment a) {
        a.setDueAt(null);
        a.setUnlockAt(null);
        a.setLockAt(null);
    }
    
    /**
     * Sets the Published state of all selected Assignments to the indicated boolean
     * @param published 
     */
    public void setSelectedPublish(boolean published) {
        for(Assignment a : getSelectedAssignments()) {
            a.setPublished(published);
        }
    }
    /**
     * Sets the given Assignment's Published state to the indicated boolean
     * @param a
     * @param published 
     */
    public void setPublish(Assignment a, boolean published) {
        a.setPublished(published);
    }
    
      //--------------------------------------------------------------------------------
        //<editor-fold desc="Add Students">
    
        public void addStudent() {
            
        }

        //</editor-fold>
      //--------------------------------------------------------------------------------
    
    public void createAssignment(Assignment a) {
        try {
            agmtWriter.createAssignment(courseID, a);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Flushes all changes made to assignments since last flush to Canvas
     */
    public void flushAssignments() {
        for (Assignment a : getAssignments()) {
            try {
                agmtWriter.editAssignment(courseID, a);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //</editor-fold>
    
}
