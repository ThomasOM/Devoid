package me.dualzz.engine.shader.uniforms;

import java.nio.FloatBuffer;
import me.dualzz.engine.shader.Uniform;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;

public class UniformMatrix extends Uniform<Matrix4f> {
    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);

    public UniformMatrix(String name) {
        super(name);
    }

    @Override
    protected void loadUniformInternal(Matrix4f matrix) {
        matrix.store(UniformMatrix.matrixBuffer);
        UniformMatrix.matrixBuffer.flip();
        GL20.glUniformMatrix4(super.getLocation(), false, UniformMatrix.matrixBuffer);
    }
}
