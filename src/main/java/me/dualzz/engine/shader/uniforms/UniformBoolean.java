package me.dualzz.engine.shader.uniforms;

import me.dualzz.engine.shader.Uniform;
import org.lwjgl.opengl.GL20;

public class UniformBoolean extends Uniform<Boolean> {
    protected UniformBoolean(String name) {
        super(name);
    }

    @Override
    public void loadUniformInternal(Boolean bool) {
        GL20.glUniform1f(super.getLocation(), bool ? 1f : 0f);
    }
}
