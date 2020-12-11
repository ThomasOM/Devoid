package me.dualzz.engine.opengl;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class VertexArrayObject {
	private final int id;
	private final Map<Integer, VertexBufferObject> vertexBuffers = new HashMap<>();

	public VertexArrayObject() {
		this.id = GL30.glGenVertexArrays();
	}

	public void bind(int... attributes) {
		GL30.glBindVertexArray(this.id);

		for (int i : attributes) {
			GL20.glEnableVertexAttribArray(i);
		}
	}

	public void unbind(int... attributes) {
		for (int i : attributes) {
			GL20.glDisableVertexAttribArray(i);
		}

		GL30.glBindVertexArray(0);
	}

	public void createAttribute(int index, int size, int stride, int offset, FloatBuffer data) {
		VertexBufferObject vertexBuffer = new VertexBufferObject();
		vertexBuffer.bind();
		vertexBuffer.data(data);

		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, stride, offset);

		vertexBuffer.unbind();
		this.vertexBuffers.put(index, vertexBuffer);
	}

	public void createAttribute(int index, int size, int stride, int offset, IntBuffer data) {
		VertexBufferObject vertexBuffer = new VertexBufferObject();
		vertexBuffer.bind();
		vertexBuffer.data(data);

		GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, stride, offset);

		vertexBuffer.unbind();
		this.vertexBuffers.put(index, vertexBuffer);
	}

	public VertexBufferObject getVertexBuffer(int index) {
		return this.vertexBuffers.get(index);
	}

	public int getId() {
		return this.id;
	}
}
