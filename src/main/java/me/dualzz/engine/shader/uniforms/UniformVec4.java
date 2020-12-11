package me.dualzz.engine.shader.uniforms;

import me.dualzz.engine.shader.Uniform;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector4f;

public class UniformVec4 extends Uniform<Vector4f> {
    public UniformVec4(String name) {
        super(name);
    }

    @Override
    protected void loadUniformInternal(Vector4f vector4f) {
        GL20.glUniform4f(super.getLocation(), vector4f.x, vector4f.y, vector4f.z, vector4f.w);
    }
}
