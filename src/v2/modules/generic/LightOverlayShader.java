package v2.modules.generic;

import v2.engine.scene.light.Light;
import v2.engine.scene.node.ModuleNode;
import v2.engine.scene.Camera;
import v2.engine.system.Shader;

public class LightOverlayShader extends Shader {

    private static LightOverlayShader instance;
    public static LightOverlayShader instance(){
        if(instance == null)
            instance = new LightOverlayShader();
        return instance;
    }

    private LightOverlayShader(){
        super();
//        createVertexShader("res/shaders/overlay/overlay_vs.glsl");
//        createFragmentShader("res/shaders/overlay/outline_fs.glsl");
//        link();
//
//        addUniform("color");
//        addUniform("projectionMatrix");
//        addUniform("modelMatrix");
//        addUniform("viewMatrix");
    }

    @Override
    public void updateUniforms(ModuleNode node){

        Light light = (Light) node;

        Camera camera = boundContext.getCamera();
        setUniform("projectionMatrix", camera.getProjectionMatrix());
        setUniform("modelMatrix", light.getModelMatrix());
        setUniform("viewMatrix", camera.getViewMatrix());
        setUniform("color", light.getColor());
    }
}
