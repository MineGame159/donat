#version 330 core
layout(location = 0) in vec4 pos;
layout(location = 1) in vec2 texturePos;

uniform mat4 u_Projection;
uniform mat4 u_View;

out vec2 v_TexturePos;

void main() {
    gl_Position = u_Projection * u_View * pos;

    v_TexturePos = texturePos;
}