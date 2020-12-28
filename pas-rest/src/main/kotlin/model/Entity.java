package model;

import java.util.UUID;

public abstract class Entity {
    private UUID id;

    public Entity(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
