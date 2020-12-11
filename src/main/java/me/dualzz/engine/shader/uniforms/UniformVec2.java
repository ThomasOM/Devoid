package me.dualzz.engine.shader.uniforms;

import me.dualzz.engine.shader.Uniform;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;

public class UniformVec2 extends Uniform<Vector2f> {
    public UniformVec2(String name) {
        super(name);
    }

    @Override
    protected void loadUniformInternal(Vector2f vector2f) {
        GL20.glUniform2f(super.getLocation(), vector2f.x, vector2f.y);
    }
}
