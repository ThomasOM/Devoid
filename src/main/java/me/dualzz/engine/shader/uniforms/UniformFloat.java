package me.dualzz.engine.shader.uniforms;

import me.dualzz.engine.shader.Uniform;
import org.lwjgl.opengl.GL20;

public class UniformFloat extends Uniform<Float> {
    protected UniformFloat(String name) {
        super(name);
    }

    @Override
    public void loadUniformInternal(Float val) {
        GL20.glUniform1f(super.getLocation(), val);
    }
}
