package ua.training.model.dto;

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

    @Override
    public String toString() {
        return "UserGroup{" +
                "groupName='" + groupName + '\'' +
                '}';
    }
}
