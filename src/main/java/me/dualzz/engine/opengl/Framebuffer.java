package me.dualzz.engine.opengl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

public class Framebuffer {
	private final int id;
	private final int width;
	private final int height;

	private final List<FramebufferAttribute> attributes = new ArrayList<>();

	public Framebuffer(int width, int height) {
		this.width = width;
		this.height = height;
		this.id = GL30.glGenFramebuffers();
	}

	public void bind() {
		this.bind(true, true);
	}

	public void bind(boolean clear, boolean viewport) {
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, this.id);
		if (clear) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		}
		if (viewport) {
			GL11.glViewport(0, 0, width, height);
		}
	}

	public void unbind() {
		this.unbind(true);
	}

	public void unbind(boolean viewport) {
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
		if (viewport) {
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}
	}

	public void cleanUp() {
		GL30.glDeleteFramebuffers(this.id);
		this.attributes.forEach(FramebufferAttribute::cleanUp);
	}

	public void blit(Framebuffer target){
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, target.id);
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.id);

		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL30.glBlitFramebuffer(0,0, this.width, this.height,0,0, target.width, target.height,
				GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, GL11.GL_NEAREST);
	}

	public void blitToScreen(){
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.id);

		GL11.glDrawBuffer(GL11.GL_BACK);

		GL30.glBlitFramebuffer( 0, 0, width, height, 0, 0, Display.getWidth(), Display.getHeight(),
				GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);
	}

	public void createColorTextureAttachment() {
		int id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL11.GL_TEXTURE_2D, id, 0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		this.attributes.add(new FramebufferAttribute(id, AttributeType.COLOR_TEXTURE));
	}

	public void createDepthTextureAttachment() {
		int id = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, width, height, 0, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, (ByteBuffer) null);

		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, id, 0);

		this.attributes.add(new FramebufferAttribute(id, AttributeType.DEPTH_TEXTURE));
	}

	public void createColorBufferAttachment() {
		int id = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, id);
		GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, 4, GL11.GL_RGBA16, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_COLOR_ATTACHMENT0, GL30.GL_RENDERBUFFER, id);

		this.attributes.add(new FramebufferAttribute(id, AttributeType.COLOR_BUFFER));
	}

	private void createDepthBufferAttachment() {
		int id = GL30.glGenRenderbuffers();
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, id);
		GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, 4, GL14.GL_DEPTH_COMPONENT32, width, height);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, id);
		GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, 0);

		this.attributes.add(new FramebufferAttribute(id, AttributeType.DEPTH_BUFFER));
	}

	public FramebufferAttribute getAttribute(int index) {
		return this.attributes.get(index);
	}

	public static class FramebufferAttribute {
		private int id;
		private AttributeType type;

		public FramebufferAttribute(int id, AttributeType type) {
			this.id = id;
			this.type = type;
		}

		public int getId() {
			return this.id;
		}

		public AttributeType getType() {
			return this.type;
		}

		public void cleanUp() {
			switch (this.type) {
				case COLOR_TEXTURE:
				case DEPTH_TEXTURE:
					GL11.glDeleteTextures(this.id);
					break;
				case COLOR_BUFFER:
				case DEPTH_BUFFER:
					GL30.glDeleteRenderbuffers(this.id);
					break;
			}
		}
	}

	public enum AttributeType {
		COLOR_TEXTURE,
		DEPTH_TEXTURE,
		COLOR_BUFFER,
		DEPTH_BUFFER
	}
}
