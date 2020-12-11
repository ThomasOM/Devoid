package me.dualzz.engine.texture;

import java.io.File;
import org.lwjgl.opengl.GL11;

public class TextureBuilder {
    private TextureStreamer.TextureData data;

    private boolean clampEdges = false;
    private boolean anisotropic = true;
    private boolean nearest = false;
    private boolean mipmap = false;
    private float mipmapBias = 0f;
    private int target = GL11.GL_TEXTURE_2D;

    public TextureBuilder(File file) {
        this.data = TextureStreamer.decodeTextureFile(file);
    }

    public Texture build() {
        int id = TextureStreamer.loadToOpenGL(this.data, this);
        return new Texture(id, this.target, this.data.getWidth(), this.data.getHeight());
    }

    public void clampEdges(boolean clampEdges) {
        this.clampEdges = clampEdges;
    }

    public void anisotropic(boolean anisotropic) {
        this.anisotropic = anisotropic;
    }

    public void nearest(boolean nearest) {
        this.nearest = nearest;
    }

    public void mipmap(boolean mipmap) {
        this.mipmap = mipmap;
    }

    public void mipmapBias(float mipmapBias) {
        this.mipmapBias = mipmapBias;
    }

    public void target(int target) {
        this.target = target;
    }

    boolean isClampEdges() {
        return this.clampEdges;
    }

    boolean isAnisotropic() {
        return this.anisotropic;
    }

    boolean isNearest() {
        return this.nearest;
    }

    boolean isMipmap() {
        return this.mipmap;
    }

    float getMipmapBias() {
        return this.mipmapBias;
    }
}
