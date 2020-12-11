package me.dualzz.engine.shader.uniforms;

import me.dualzz.engine.shader.Uniform;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;

public class UniformVec3 extends Uniform<Vector3f> {
    public UniformVec3(String name) {
        super(name);
    }

    @Override
    protected void loadUniformInternal(Vector3f vector3f) {
        GL20.glUniform3f(super.getLocation(), vector3f.x, vector3f.y, vector3f.z);
    }
}
