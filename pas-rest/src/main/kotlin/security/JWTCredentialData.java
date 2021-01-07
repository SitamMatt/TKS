package security;

import java.util.Set;

public class JWTCredentialData {
    private Set<String> groups;
    private String subject;

    public JWTCredentialData(Set<String> groups, String subject) {
        this.groups = groups;
        this.subject = subject;
    }

    public Set<String> getGroups() {
        return groups;
    }

    public void setGroups(Set<String> groups) {
        this.groups = groups;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
