package model;

import java.util.UUID;

public abstract class Entity {
    private UUID guid;

    public Entity(){
        this.guid = null;
    }

    public Entity(UUID guid) {
        this.guid = guid;
    }

    public UUID getGuid() {
        return guid;
    }

    public void setGuid(UUID guid) {
        this.guid = guid;
    }
}
