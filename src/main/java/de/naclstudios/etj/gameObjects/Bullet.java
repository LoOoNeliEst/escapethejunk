package de.naclstudios.etj.gameObjects;

import de.edgelord.sjgl.core.event.CollisionEvent;
import de.edgelord.sjgl.gameobject.GameObject;
import de.edgelord.sjgl.location.Coordinates;
import de.edgelord.sjgl.utils.Directions;
import de.edgelord.sjgl.utils.StaticSystem;

import java.awt.*;

public class Bullet extends GameObject {

    public static float DEFAULT_RANGE = 550;
    public static float DEFAULT_VELOCITY = 0.85f;
    public static Color color = new Color(166, 121, 45);

    private int id = BulletIdManager.getNewId();

    private float range = DEFAULT_RANGE;
    private float velocity = DEFAULT_VELOCITY;
    private float delta = 0f;
    private Directions.Direction direction;
    private boolean deprecated = false;

    public Bullet(Coordinates coordinates, Directions.Direction direction) {
        super(coordinates, 12, 7, "de.naclstudios.etj.gameObjects.bullet");

        this.direction = direction;

        removeComponent(DEFAULT_PHYSICS_NAME);
        // removeComponent(DEFAULT_PUSH_OUT_ON_COLLISION_NAME);

        if (direction == Directions.Direction.up || direction == Directions.Direction.down){
            setWidth(7);
            setHeight(12);
        }
    }

    public void initialize() {

    }

    public void onCollision(CollisionEvent e) {

        if (e.getRoot().getTag().equals("de.naclstudios.etj.gameObjects.junkHill")){

            JunkHill junkHill = (JunkHill) e.getRoot();

            junkHill.hitByBullet(this);
        }

        if (!e.getRoot().getTag().equals("de.naclstudios.etj.gameObject.player")){

            StaticSystem.currentScene.getGameObjects().remove(this);
        }
    }

    public void onFixedTick() {

        if (delta >= range) {
            StaticSystem.currentScene.getGameObjects().remove(this);
        } else {
            float nextStep = velocity * StaticSystem.fixedTickMillis;
            if (direction == null){
            } else {
                move(nextStep, direction);
                delta += nextStep;
            }
        }
    }

    public void onTick() {

    }

    public void draw(Graphics2D graphics2D) {

        if (!isDeprecated()) {
            graphics2D.setColor(color);
            graphics2D.fillOval(getCoordinates().getX(), getCoordinates().getY(), getWidth(), getHeight());
        }
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public float getVelocity() {
        return velocity;
    }

    public void setVelocity(float velocity) {
        this.velocity = velocity;
    }

    public Directions.Direction getDirection() {
        return direction;
    }

    public void setDirection(Directions.Direction direction) {
        this.direction = direction;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public int getId() {
        return id;
    }
}
