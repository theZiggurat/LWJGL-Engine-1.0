# version 430

layout (location = 0) out vec4 overlay_vbo;

uniform vec3 color;

void main(){

    overlay_vbo = vec4(color, 1);

}