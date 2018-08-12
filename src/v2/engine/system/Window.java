package v2.engine.system;

import lombok.Getter;
import lombok.Setter;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    @Getter private int height, width;
    @Getter private long handle;
    @Getter @Setter private boolean resized = false;
    @Getter private String title;

    public Window(String title, int width, int height){
        this.title = title;
        this.height = height;
        this.width = width;
    }

    /**
     * Makes necessary GLFW calls to instantiate a system window
     * with openGL context.
     * @throws IllegalStateException Unable to initialize GLFW
     * @throws RuntimeException Failed to create GLFW window
     */
    public void init(){

        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()){
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwWindowHint(GLFW_VISIBLE, GL_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        handle = glfwCreateWindow(width, height, title, 0, NULL);
        if(handle == NULL){
            throw new RuntimeException("Failed to create GLFW window");
        }

        glfwSetFramebufferSizeCallback(handle, (window, width, height) ->{
            this.width = width;
            this.height = height;
            resized = true;
        });

        glfwSetKeyCallback(handle, (window, key, scancode, action, mods) ->{
            if(key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true);
            }
        });

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(handle,
                (vidmode.width() - width)/2,
                (vidmode.height() - height)/2);



        glfwMakeContextCurrent(handle);
        GL.createCapabilities();
        glfwShowWindow(handle);

        glClearColor(0,0,0, 1f);


       glEnable(GL_DEPTH_TEST);
       glEnable(GL_CULL_FACE);
       glCullFace(GL_BACK);
       glPolygonMode(GL_FRONT_FACE, GL_FILL);

       glLineWidth(1);

       setIcon("res/images/icon.png");
    }

    public void update(){
        //glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwSwapBuffers(handle);
        glfwPollEvents();
    }

    /**
     *  Window poll for EngineCore.loop()
      */
    public boolean shouldClose(){
        return glfwWindowShouldClose(handle);
    }

    /**
     * 32 x 32 window icon
     * @param path image path with format "res/image/*"
     */
    public void setIcon(String path){
        GLFWImage.Buffer images = GLFWImage.malloc(1);
        ByteBuffer buffer = StaticLoader.loadImage(path);

        GLFWImage icon = GLFWImage.malloc();
        icon.set(32,32,buffer);

        images.put(0,icon);
        glfwSetWindowIcon(handle, images);
    }

}
