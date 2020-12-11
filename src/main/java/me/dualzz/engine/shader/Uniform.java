package me.dualzz.engine.shader;

import org.lwjgl.opengl.GL20;

public abstract class Uniform<T> {
    private final String name;
    private int location;
    private T currentValue;

    protected Uniform(String name) {
        this.name = name;
    }

    public void storeUniformLocation(int programID) {
        this.location = GL20.glGetUniformLocation(programID, name);
        if (this.location == -1) {
            System.err.println("No uniform variable called " + name + " found!");
        }
    }

    protected final int getLocation() {
        return this.location;
    }

    public final void loadUniform(T t) {
        if (this.currentValue != t) {
            this.loadUniformInternal(t);
            this.currentValue = t;
        }
    }

    protected abstract void loadUniformInternal(T t);
}
