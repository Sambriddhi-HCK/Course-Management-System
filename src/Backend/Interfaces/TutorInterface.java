package Backend.Interfaces;

/*
 * CRUD operations for tutors
 */

import Backend.Details.TutorDetails;
public interface TutorInterface {
    public boolean createTutor(TutorDetails t);
    public boolean updateTutor(TutorDetails t);
    public boolean deleteTutor(int tutID);
    public boolean enrollModule(TutorDetails tutorDetails);
    public TutorDetails getTutorDetails(int tutID);
    public int generateTutorID();
    public boolean assignMarks(float mark, int stdId, String moduleCode);
}
