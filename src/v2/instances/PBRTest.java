package v2.instances;

import org.joml.Vector3f;
import v2.engine.scene.Node;
import v2.engine.scene.Scenegraph;
import v2.engine.system.EngineInterface;
import v2.engine.system.InputCore;
import v2.engine.system.RenderEngine;
import v2.modules.pbr.PBRModel;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_R;

public class PBRTest implements EngineInterface {

    private Scenegraph scene;
    Node object;

    PBRModel model, model2;

    @Override
    public void init(){

//        model = PBRModel.quickModel("res/models/M4A1.obj",
//                "res/images/streakmetal/", "albedo.png", null,
//                "rough.png", "metal.png");

//        model = PBRModel.quickModel("res/models/barrels.obj",
//                "res/images/barrel2/", "albedo.png", "normal.png",
//                "rough.png", "metal.png");

//        model = PBRModel.quickModel("res/models/nightstand.obj",
//        "res/images/nightstand/", "albedo.png", "normal.png",
////                "rough.png", "metal.png");
//        model.scale(5f);
//        model.getWorldTransform().setTranslation(new Vector3f(0,1,0));

        model2 = PBRModel.quickModel("res/models/thing.obj",
                "res/images/plastic_squares/", "albedo.png", null,
                "rough.png", "metal.png");



        scene = RenderEngine.instance().getScenegraph();
        object = new Node();
        //object.addChild(model);
        object.addChild(model2);
        scene.addChild(object);

    }

    double time = 0;

    @Override
    public void update(double duration) {


        if(InputCore.instance().isKeyHeld(GLFW_KEY_R)) {
            model2.getWorldTransform().setRotation(new Vector3f(
                    0, (float) time / 40 * 1000, 0
            ));
            time += duration;
        }

    }

    @Override
    public void cleanup() {

    }
}
