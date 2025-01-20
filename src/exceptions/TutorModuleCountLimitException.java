package exceptions;

public class TutorModuleCountLimitException extends Throwable{
    public TutorModuleCountLimitException() {
        super("A Tutor Cannot be Assigned to More Than 4 Modules");
    }
}
