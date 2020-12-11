package me.dualzz.engine.shader.uniforms;

import me.dualzz.engine.shader.Uniform;
import org.lwjgl.opengl.GL20;

public class UniformSampler extends Uniform<Integer> {
    public UniformSampler(String name) {
        super(name);
    }

    @Override
    protected void loadUniformInternal(Integer texUnit) {
        GL20.glUniform1i(super.getLocation(), texUnit);
    }
}
