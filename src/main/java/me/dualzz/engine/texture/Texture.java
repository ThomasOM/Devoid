package me.dualzz.engine.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture {
    private final int textureId;
    private final int target;
    private final int width;
    private final int height;

    Texture(int textureId, int target, int width, int height) {
        this.textureId = textureId;
        this.target = target;
        this.width = width;
        this.height = height;
    }

    public void bindToUnit(int unit) {
        GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
        GL11.glBindTexture(this.target, this.textureId);
    }

    public void delete() {
        GL11.glDeleteTextures(this.textureId);
    }

    public int getTextureId() {
        return this.textureId;
    }

    public int getTarget() {
        return this.target;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
