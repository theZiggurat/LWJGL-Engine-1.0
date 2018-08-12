package v2.engine.system;

import lombok.Getter;
import lombok.Setter;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Camera {

    private final Vector3f ABS_UP = new Vector3f(0,1,0);

    @Setter @Getter private double FOV;

    @Getter private final float ZNEAR = .1f;
    @Getter private final float ZFAR = 10000;

    @Setter @Getter private Vector3f position;
    @Setter @Getter private Vector3f rotation;

    @Setter @Getter private float mouseSens = 0.3f;


    public Camera(){
        setPosition(new Vector3f(0,0,-2));
        setRotation(new Vector3f(0,180,0));
        FOV = 85;
    }

    public Camera(Vector3f position){
        this.position = position;

    }

    public void update(){
        InputCore input = InputCore.getInstance();

        if(input.isButtonHeld(1)){ // right click
            getRotation().add((float)input.getDisplacement().y * mouseSens,
                    (float)input.getDisplacement().x * mouseSens,  0);
        }

        if(input.isButtonHeld(2)){ // middle click
            getPosition().add(getUp().mul((float)input.getDisplacement().y * .05f));
            getPosition().add(getRight().mul((float)input.getDisplacement().x * -.05f));
        }

        FOV += input.getScrollAmount();

        if(input.isKeyHeld(GLFW_KEY_W)){
            position.add(getForward().mul(.05f));
        }
        if(input.isKeyHeld(GLFW_KEY_S)){
            position.add(getForward().mul(-.05f));
        }
        if(input.isKeyHeld(GLFW_KEY_A)){
            position.add(getRight().mul(.05f));
        }
        if(input.isKeyHeld(GLFW_KEY_D)){
            position.add(getRight().mul(-.05f));
        }

    }

    public Matrix4f getProjectionMatrix() {
        float apectRatio = (float) EngineCore.getInstance().getWindow().getWidth() /
                (float) EngineCore.getInstance().getWindow().getHeight();
        Matrix4f ret = new Matrix4f();
        ret.identity();
        ret.perspective((float)Math.toRadians(FOV), apectRatio, ZNEAR, ZFAR);
        return ret;
    }

    public Matrix4f getViewMatrix(){
        Matrix4f ret = new Matrix4f();
        ret.identity();
        ret.rotate((float)Math.toRadians(rotation.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(rotation.y), new Vector3f(0, 1, 0))
                .rotate((float)Math.toRadians(rotation.z), new Vector3f(0,0,1));

        ret.translate(-position.x, -position.y, -position.z);
        return ret;
    }

    public Vector3f getForward(){
        Matrix4f viewMatrix = getViewMatrix();
        Vector3f ret = new Vector3f();
        ret.x = -viewMatrix.m02();
        ret.y = -viewMatrix.m12();
        ret.z = -viewMatrix.m22();
        return ret;
    }

    public Vector3f getUp(){
        Matrix4f viewMatrix = getViewMatrix();
        Vector3f ret = new Vector3f();
        ret.x = -viewMatrix.m01();
        ret.y = -viewMatrix.m11();
        ret.z = -viewMatrix.m21();
        return ret;
    }

    public Vector3f getRight(){
        Matrix4f viewMatrix = getViewMatrix();
        Vector3f ret = new Vector3f();
        ret.x = -viewMatrix.m00();
        ret.y = -viewMatrix.m10();
        ret.z = -viewMatrix.m20();
        return ret;
    }

}