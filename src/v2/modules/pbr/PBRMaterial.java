package v2.modules.pbr;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import v2.engine.gldata.TextureObject;
import v2.engine.scene.Module;

@AllArgsConstructor
public class PBRMaterial extends Module {

    @Setter
    @Getter
    TextureObject albedoMap, normalMap, roughnessMap, metallicMap, aoMap;

}
