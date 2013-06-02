package ${package};

${annotation}
@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ParseException extends Exception {
    private int position;
    private String message;

    public ParseException(String message, int position) {
        this.position = position;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message + " \nPosition: " + position;
    }
}
