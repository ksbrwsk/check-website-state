package de.ksbrwsk.cws;

public enum State {
    UP("UP", "fa-thumbs-up", "bg-success"),
    DOWN("DOWN", "fa-thumbs-down", "bg-danger"),
    UNKNOWN("UNKNOWN", "fa-circle-question", "bg-warning");

    private final String state;
    private final String fontAwesomeIcon;
    private final String backgroundColor;

    State(String state, String fontAwesomeIcon, String backgroundColor) {
        this.state = state;
        this.fontAwesomeIcon = fontAwesomeIcon;
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public String getFontAwesomeIcon() {
        return fontAwesomeIcon;
    }

    public String getState() {
        return state;
    }
}
