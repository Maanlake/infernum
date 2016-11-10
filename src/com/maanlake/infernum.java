package com.maanlake;

import static org.lwjgl.opengl.util.LWJGLUtils.ioResourceToByteBuffer;
import static org.lwjgl.glfw.GLFW.GLFW_CROSSHAIR_CURSOR;
import static org.lwjgl.glfw.GLFW.GLFW_FALSE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_LEFT_CONTROL;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UNKNOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_REPEAT;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_SAMPLES;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateStandardCursor;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCursor;
import static org.lwjgl.glfw.GLFW.glfwSetCursorPosCallback;
import static org.lwjgl.glfw.GLFW.glfwSetFramebufferSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetMouseButtonCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSizeCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.glfw.GLFW.nglfwGetFramebufferSize;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memAddress;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.FrustumIntersection;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLUtil;
import org.lwjgl.system.Callback;

public class infernum {
	
	/*
	 * Static 
	 */
	
	private static float maxLinearVel = 200.0f;
	/*
	 * Sub Classes
	 */
	
	private static class FirstPersonCamera {
        public Vector3f linearAcc = new Vector3f();
        public Vector3f linearVel = new Vector3f();
        public float linearDamping = 0.08f;

        /** ALWAYS rotation about the local XYZ axes of the camera! */
        public Vector3f angularAcc = new Vector3f();
        public Vector3f angularVel = new Vector3f();
        public float angularDamping = 0.5f;

        public Vector3d position = new Vector3d(0, 0, 10);
        public Quaternionf rotation = new Quaternionf();

        public FirstPersonCamera update(float dt) {
            // update linear velocity based on linear acceleration
            linearVel.fma(dt, linearAcc);
            // update angular velocity based on angular acceleration
            angularVel.fma(dt, angularAcc);
            // update the rotation based on the angular velocity
            rotation.integrate(dt, angularVel.x, angularVel.y, angularVel.z);
            angularVel.mul(1.0f - angularDamping * dt);
            // update position based on linear velocity
            position.fma(dt, linearVel);
            linearVel.mul(1.0f - linearDamping * dt);
            return this;
        }
        public Vector3f right(Vector3f dest) {
            return rotation.positiveX(dest);
        }
        public Vector3f up(Vector3f dest) {
            return rotation.positiveY(dest);
        }
        public Vector3f forward(Vector3f dest) {
            return rotation.positiveZ(dest).negate();
        }
    }
	
	/*
	 * Window Variablen
	 */
	private long window;
    private int width = 800;
    private int height = 600;
    private int fbWidth = 800;
    private int fbHeight = 600;
    private boolean windowed = false;
    
    /*
     * Kontrollelemente
     */
    
    private boolean[] keyDown = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean leftMouseDown = false;
    private boolean rightMouseDown = false;
    private float mouseX = 0.0f;
    private float mouseY = 0.0f;
    private long lastTime = System.nanoTime();
    private long lastActionTime = 0L;
    private static int actionMilliseconds = 80;
    
    private FirstPersonCamera cam = new FirstPersonCamera();
    
    /*
     * Views
     */
    
    private Matrix4f projMatrix = new Matrix4f();
    private Matrix4f viewMatrix = new Matrix4f();
    private Matrix4f viewProjMatrix = new Matrix4f();
    private Matrix4f invViewMatrix = new Matrix4f();
    private Matrix4f invViewProjMatrix = new Matrix4f();
    private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    private FrustumIntersection frustumIntersection = new FrustumIntersection();
    
    /*
     * OpenGL Variablen
     */
    private GLCapabilities caps;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cpCallback;
    private GLFWMouseButtonCallback mbCallback;
    private GLFWFramebufferSizeCallback fbCallback;
    private GLFWWindowSizeCallback wsCallback;
    private Callback debugProc;

	public static void main(String[] args) {
		// Start Game
		new infernum().run();
	}
	
	 private void run() {
	        try {
	            init();
	            loop();

	            if (debugProc != null)
	                debugProc.free();

	            keyCallback.free();
	            cpCallback.free();
	            mbCallback.free();
	            fbCallback.free();
	            wsCallback.free();
	            glfwDestroyWindow(window);
	        } catch (Throwable t) {
	            t.printStackTrace();
	        } finally {
	            glfwTerminate();
	        }
	    }
	 
	 

	 private void init() throws IOException {
	        if (!glfwInit())
	            throw new IllegalStateException("Unable to initialize GLFW");

	        glfwDefaultWindowHints();
	        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
	        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
	        glfwWindowHint(GLFW_SAMPLES, 4);

	        long monitor = glfwGetPrimaryMonitor();
	        GLFWVidMode vidmode = glfwGetVideoMode(monitor);
	        if (!windowed) {
	            width = vidmode.width();
	            height = vidmode.height();
	            fbWidth = width;
	            fbHeight = height;
	        }
	        window = glfwCreateWindow(width, height, "Little Space Shooter Game", !windowed ? monitor : 0L, NULL);
	        if (window == NULL) {
	            throw new AssertionError("Failed to create the GLFW window");
	        }
	        glfwSetCursor(window, glfwCreateStandardCursor(GLFW_CROSSHAIR_CURSOR));

	        glfwSetFramebufferSizeCallback(window, fbCallback = new GLFWFramebufferSizeCallback() {
	            public void invoke(long window, int width, int height) {
	                if (width > 0 && height > 0 && (infernum.this.fbWidth != width || infernum.this.fbHeight != height)) {
	                	infernum.this.fbWidth = width;
	                	infernum.this.fbHeight = height;
	                }
	            }
	        });
	        glfwSetWindowSizeCallback(window, wsCallback = new GLFWWindowSizeCallback() {
	            public void invoke(long window, int width, int height) {
	                if (width > 0 && height > 0 && (infernum.this.width != width || infernum.this.height != height)) {
	                	infernum.this.width = width;
	                	infernum.this.height = height;
	                }
	            }
	        });

	        System.out.println("Press W/S to move forward/backward");
	        System.out.println("Press L.Ctrl/Spacebar to move down/up");
	        System.out.println("Press A/D to strafe left/right");
	        System.out.println("Press Q/E to roll left/right");
	        System.out.println("Hold the left mouse button to shoot");
	        System.out.println("Hold the right mouse button to rotate towards the mouse cursor");
	        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
	            public void invoke(long window, int key, int scancode, int action, int mods) {
	                if (key == GLFW_KEY_UNKNOWN) 
	                    return;
	                if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
	                    glfwSetWindowShouldClose(window, true);
	                }
	                if (action == GLFW_PRESS || action == GLFW_REPEAT) {
	                    keyDown[key] = true;
	                } else {
	                    keyDown[key] = false;
	                }
	            }
	        });
	        glfwSetCursorPosCallback(window, cpCallback = new GLFWCursorPosCallback() {
	            public void invoke(long window, double xpos, double ypos) {
	                float normX = (float) ((xpos - width/2.0) / width * 2.0);
	                float normY = (float) ((ypos - height/2.0) / height * 2.0);
	                infernum.this.mouseX = Math.max(-width/2.0f, Math.min(width/2.0f, normX));
	                infernum.this.mouseY = Math.max(-height/2.0f, Math.min(height/2.0f, normY));
	            }
	        });
	        glfwSetMouseButtonCallback(window, mbCallback = new GLFWMouseButtonCallback() {
	            public void invoke(long window, int button, int action, int mods) {
	                if (button == GLFW_MOUSE_BUTTON_LEFT) {
	                    if (action == GLFW_PRESS)
	                        leftMouseDown = true;
	                    else if (action == GLFW_RELEASE)
	                        leftMouseDown = false;
	                } else if (button == GLFW_MOUSE_BUTTON_RIGHT) {
	                    if (action == GLFW_PRESS)
	                        rightMouseDown = true;
	                    else if (action == GLFW_RELEASE)
	                        rightMouseDown = false;
	                }
	            }
	        });
	        glfwMakeContextCurrent(window);
	        glfwSwapInterval(0);
	        glfwShowWindow(window);

	        IntBuffer framebufferSize = BufferUtils.createIntBuffer(2);
	        nglfwGetFramebufferSize(window, memAddress(framebufferSize), memAddress(framebufferSize) + 4);
	        fbWidth = framebufferSize.get(0);
	        fbHeight = framebufferSize.get(1);
	        caps = GL.createCapabilities();
	        if (!caps.OpenGL20) {
	            throw new AssertionError("This Game requires OpenGL 2.0.");
	        }
	        debugProc = GLUtil.setupDebugMessageCallback();

	        /* Create all needed GL resources */
	        //createWallProgram();
	        
	        createRoom();
	        
	        /*
	         *  GL ressourcen erstellen
	         */
	        glEnableClientState(GL_VERTEX_ARRAY);
	        glEnable(GL_DEPTH_TEST);
	        glEnable(GL_CULL_FACE);
	        glBlendFunc(GL_SRC_ALPHA, GL_ONE);
	    }
	 
	 private static int createShader(String resource, int type) throws IOException {
	        int shader = glCreateShader(type);
	        ByteBuffer source = ioResourceToByteBuffer(resource, 1024);
	        PointerBuffer strings = BufferUtils.createPointerBuffer(1);
	        IntBuffer lengths = BufferUtils.createIntBuffer(1);
	        strings.put(0, source);
	        lengths.put(0, source.remaining());
	        glShaderSource(shader, strings, lengths);
	        glCompileShader(shader);
	        int compiled = glGetShaderi(shader, GL_COMPILE_STATUS);
	        String shaderLog = glGetShaderInfoLog(shader);
	        if (shaderLog.trim().length() > 0) {
	            System.err.println(shaderLog);
	        }
	        if (compiled == 0) {
	            throw new AssertionError("Could not compile shader");
	        }
	        return shader;
	    }

	    private static int createProgram(int vshader, int fshader) {
	        int program = glCreateProgram();
	        glAttachShader(program, vshader);
	        glAttachShader(program, fshader);
	        glLinkProgram(program);
	        int linked = glGetProgrami(program, GL_LINK_STATUS);
	        String programLog = glGetProgramInfoLog(program);
	        if (programLog.trim().length() > 0) {
	            System.err.println(programLog);
	        }
	        if (linked == 0) {
	            throw new AssertionError("Could not link program");
	        }
	        return program;
	    }
	 
	 private void loop() {
	        while (!glfwWindowShouldClose(window)) {
	            glfwPollEvents();
	            glViewport(0, 0, fbWidth, fbHeight);
	            update();
	            render();
	            glfwSwapBuffers(window);
	        }
	    }
	 
	 private void update() {
	        long thisTime = System.nanoTime();
	        float dt = (thisTime - lastTime) / 1E9f;
	        lastTime = thisTime;
	        /*
	         * Update Environment like Particles
	         */
	        // updateParticles
	        
	        cam.update(dt);

	        projMatrix.setPerspective((float) Math.toRadians(40.0f), (float) width / height, 0.1f, 5000.0f);
	        viewMatrix.set(cam.rotation).invert(invViewMatrix);
	        viewProjMatrix.set(projMatrix).mul(viewMatrix).invert(invViewProjMatrix);
	        frustumIntersection.set(viewProjMatrix);

	        
	        /* Update the wall shader */
	        /*glUseProgram(wallProgram);
	        glUniformMatrix4fv(wall_viewUniform, false, viewMatrix.get(matrixBuffer));
	        glUniformMatrix4fv(wall_projUniform, false, projMatrix.get(matrixBuffer));
	        */

	        /* Update the particle shader */
	        //glUseProgram(particleProgram);
	        //glUniformMatrix4fv(particle_projUniform, false, matrixBuffer);

	        updateControls();

	        /* Let the player do some action */
	        if (leftMouseDown && (thisTime - lastActionTime >= 1E6 * actionMilliseconds)) {
	            //shoot();
	            //lastShotTime = thisTime;
	        }
	        /* Let the opponent do some actions */
	        // Code here
	    }
	 
	 private void updateControls() {
	        cam.linearAcc.zero();
	        float rotZ = 0.0f;
	        if (keyDown[GLFW_KEY_W])
	        //    cam.linearAcc.fma(mainThrusterAccFactor, cam.forward(tmp2));
	        if (keyDown[GLFW_KEY_S])
	          //  cam.linearAcc.fma(-mainThrusterAccFactor, cam.forward(tmp2));
	        if (keyDown[GLFW_KEY_D])
	            //cam.linearAcc.fma(straveThrusterAccFactor, cam.right(tmp2));
	        if (keyDown[GLFW_KEY_A])
	           // cam.linearAcc.fma(-straveThrusterAccFactor, cam.right(tmp2));
	        if (keyDown[GLFW_KEY_Q])
	            rotZ = -1.0f;
	        if (keyDown[GLFW_KEY_E])
	            rotZ = +1.0f;
	        if (keyDown[GLFW_KEY_SPACE])
	            //cam.linearAcc.fma(straveThrusterAccFactor, cam.up(tmp2));
	        if (keyDown[GLFW_KEY_LEFT_CONTROL])
	            //cam.linearAcc.fma(-straveThrusterAccFactor, cam.up(tmp2));
	        if (rightMouseDown)
	            cam.angularAcc.set(2.0f*mouseY*mouseY*mouseY, 2.0f*mouseX*mouseX*mouseX, rotZ);
	        else if (!rightMouseDown)
	            cam.angularAcc.set(0, 0, rotZ);
	        //cam.angularAcc.set(2.0f*mouseY*mouseY*mouseY, 2.0f*mouseX*mouseX*mouseX, rotZ); // always move cam 
	        double linearVelAbs = cam.linearVel.length();
	        if (linearVelAbs > maxLinearVel)
	            cam.linearVel.normalize().mul(maxLinearVel);	         
	    }
	 
	 private void render() {
	        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
	     /* 
	      * Draw Objects
	      */
	        drawRoom();
	    }
	 
	 private void createRoom() {
		
		 
	 }
	 
	 private void drawRoom() {
		 glClear(GL_COLOR_BUFFER_BIT);
		 glBegin(GL_TRIANGLES);
		 glColor3f(0,0,1);/* blue */
		    glVertex3i(0, 0,0);
		    glColor3f(0, 1, 0);  /* green */
		    glVertex3i(1, 1,1);
		    glColor3f(1, 0, 0);  /* red */
		    glVertex3i(0, 1,0);
		  glEnd();
		  glFlush();  /* Single buffered, so needs a flush. */
	 }
	 
}
