package v2.instances;

import org.joml.Vector3f;
import v2.engine.gldata.TextureObject;
import v2.engine.gldata.VertexBufferObject;
import v2.engine.javadata.MeshData;
import v2.engine.scene.Node;
import v2.engine.scene.Scenegraph;
import v2.engine.system.EngineInterface;
import v2.engine.system.RenderEngine;
import v2.engine.system.StaticLoader;
import v2.modules.pbr.PBRMaterial;
import v2.modules.pbr.PBRModel;

public class PBRTest implements EngineInterface {

    private Scenegraph scene;
    Node object;

    @Override
    public void init(){

        VertexBufferObject mesh;

        TextureObject albedo = StaticLoader.loadTexture(
                "res/images/paintchip/albedo.png")
                .bilinearFilter();

        TextureObject normal = StaticLoader.loadTexture(
                "res/images/paintchip/normal.png")
                .bilinearFilter();

        TextureObject roughness = StaticLoader.loadTexture(
                "res/images/paintchip/rough.png")
                .bilinearFilter();

        TextureObject metal = StaticLoader.loadTexture(
                "res/images/paintchip/metal.png")
                .bilinearFilter();

        TextureObject ao = StaticLoader.loadTexture(
                "res/images/paintchip/ao.png")
                .bilinearFilter();

        mesh = new VertexBufferObject(MeshData.loadMesh("res/models/teapot.obj"));

        PBRMaterial material = new PBRMaterial(albedo, normal, roughness, metal, ao);

        PBRModel model = new PBRModel(mesh, material);
        model.scale(30f);

        scene = RenderEngine.getInstance().getScenegraph();
        object = new Node();
        object.addChild(model);
        scene.addChild(object);

    }

    @Override
    public void update(double duration) {
        object.getWorldTransform().setTranslation(object.getWorldTransform().getTranslation().add(new Vector3f(0,0f, .01f)));

    }

    @Override
    public void cleanup() {

    }
}
