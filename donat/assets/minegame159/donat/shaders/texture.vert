#version 330 core
layout(location = 0) in vec4 pos;
layout(location = 1) in vec2 texturePos;

out vec2 v_TexturePos;

void main() {
    gl_Position = pos;

    v_TexturePos = texturePos;
}