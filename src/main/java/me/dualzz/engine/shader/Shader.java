package me.dualzz.engine.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

public class Shader {
    private int programID;

    public Shader(File vertexFile, File fragmentFile, String... inVariables) {
        int vertexShaderID = this.loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        int fragmentShaderID = this.loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        this.programID = GL20.glCreateProgram();

        GL20.glAttachShader(this.programID, vertexShaderID);
        GL20.glAttachShader(this.programID, fragmentShaderID);

        this.bindAttributes(inVariables);

        GL20.glLinkProgram(this.programID);
        GL20.glDetachShader(this.programID, vertexShaderID);
        GL20.glDetachShader(this.programID, fragmentShaderID);

        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
    }

    public Shader(File vertexFile, File geometryFile, File fragmentFile, String... inVariables) {
        int vertexShaderID = this.loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        int fragmentShaderID = this.loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        int geometryShaderID = this.loadShader(geometryFile, GL32.GL_GEOMETRY_SHADER);
        this.programID = GL20.glCreateProgram();

        GL20.glAttachShader(this.programID, vertexShaderID);
        GL20.glAttachShader(this.programID, fragmentShaderID);
        GL20.glAttachShader(this.programID, geometryShaderID);

        this.bindAttributes(inVariables);

        GL20.glLinkProgram(this.programID);
        GL20.glDetachShader(this.programID, vertexShaderID);
        GL20.glDetachShader(this.programID, fragmentShaderID);
        GL20.glDetachShader(this.programID, geometryShaderID);

        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteShader(geometryShaderID);
    }

    protected void storeAllUniformLocations(Uniform<?>... uniforms) {
        for (Uniform<?> uniform : uniforms) {
            uniform.storeUniformLocation(programID);
        }
        GL20.glValidateProgram(programID);
    }

    public void start() {
        GL20.glUseProgram(this.programID);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void delete() {
        this.stop();
        GL20.glDeleteProgram(this.programID);
    }

    protected void bindAttributes(String[] inVariables) {
        for (int i = 0; i < inVariables.length; i++) {
            if (!inVariables[i].isEmpty()){
                this.bindAttribute(i, inVariables[i]);
            }
        }
    }

    protected void bindAttribute(int id, String variable){
        GL20.glBindAttribLocation(this.programID, id, variable);
    }

    private int loadShader(File file, int type) {
        StringBuilder shaderSource = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("//\n");
            }
        } catch (Exception e) {
            System.err.println("Could not read shader file: " + file.getName());
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader: " + file.getName());
            System.exit(-1);
        }

        return shaderID;
    }
}
