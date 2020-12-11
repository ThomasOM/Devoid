package me.dualzz.engine.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL15;

public class VertexBufferObject {
	private final int id;

	public VertexBufferObject() {
		this.id = GL15.glGenBuffers();
	}

	public void bind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.id);
	}

	public void unbind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public void data(IntBuffer data) {
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
	}

	public void data(FloatBuffer data) {
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, data, GL15.GL_STATIC_DRAW);
	}

	public void delete() {
		GL15.glDeleteBuffers(this.id);
	}

	public int getId() {
		return this.id;
	}
}
