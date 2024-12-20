package de.ksbrwsk.cws;


public record WebsiteState(String name, State state, String time) {

    @Override
    public String toString() {
        return state.name();
    }
}
