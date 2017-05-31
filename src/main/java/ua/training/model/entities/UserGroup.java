package ua.training.model.entities;

public enum UserGroup {
    USER("USER"),
    ADMIN("ADMIN");

    private String groupName;

    UserGroup(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }
}
