package mcheli.wrapper.modelloader;

import net.minecraftforge.client.model.*;

public abstract class W_ModelCustom implements IModelCustom
{
    public float min;
    public float minX;
    public float minY;
    public float minZ;
    public float max;
    public float maxX;
    public float maxY;
    public float maxZ;
    public float size;
    public float sizeX;
    public float sizeY;
    public float sizeZ;
    
    public W_ModelCustom() {
        this.min = 100000.0f;
        this.minX = 100000.0f;
        this.minY = 100000.0f;
        this.minZ = 100000.0f;
        this.max = -100000.0f;
        this.maxX = -100000.0f;
        this.maxY = -100000.0f;
        this.maxZ = -100000.0f;
        this.size = 0.0f;
        this.sizeX = 0.0f;
        this.sizeY = 0.0f;
        this.sizeZ = 0.0f;
    }
    
    public void checkMinMax(final W_Vertex v) {
        if (v.x < this.minX) {
            this.minX = v.x;
        }
        if (v.y < this.minY) {
            this.minY = v.y;
        }
        if (v.z < this.minZ) {
            this.minZ = v.z;
        }
        if (v.x > this.maxX) {
            this.maxX = v.x;
        }
        if (v.y > this.maxY) {
            this.maxY = v.y;
        }
        if (v.z > this.maxZ) {
            this.maxZ = v.z;
        }
    }
    
    public void checkMinMaxFinal() {
        if (this.minX < this.min) {
            this.min = this.minX;
        }
        if (this.minY < this.min) {
            this.min = this.minY;
        }
        if (this.minZ < this.min) {
            this.min = this.minZ;
        }
        if (this.maxX > this.max) {
            this.max = this.maxX;
        }
        if (this.maxY > this.max) {
            this.max = this.maxY;
        }
        if (this.maxZ > this.max) {
            this.max = this.maxZ;
        }
        this.sizeX = this.maxX - this.minX;
        this.sizeY = this.maxY - this.minY;
        this.sizeZ = this.maxZ - this.minZ;
        this.size = this.max - this.min;
    }
    
    public abstract boolean containsPart(final String p0);
    
    public abstract void renderAll(final int p0, final int p1);
    
    public abstract void renderAllLine(final int p0, final int p1);
    
    public abstract int getVertexNum();
    
    public abstract int getFaceNum();
}
