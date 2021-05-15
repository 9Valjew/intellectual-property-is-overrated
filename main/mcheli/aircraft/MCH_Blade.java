package mcheli.aircraft;

import mcheli.*;

public class MCH_Blade
{
    private float baseRotation;
    private float rotation;
    private float prevRotation;
    private float foldSpeed;
    private float foldRotation;
    
    public MCH_Blade(final float baseRotation) {
        this.rotation = 0.0f;
        this.prevRotation = 0.0f;
        this.baseRotation = baseRotation;
        this.foldSpeed = 3.0f;
        this.foldRotation = 5.0f;
    }
    
    public float getRotation() {
        return this.rotation;
    }
    
    public void setRotation(final float rotation) {
        this.rotation = (float)MCH_Lib.getRotate360(rotation);
    }
    
    public float getPrevRotation() {
        return this.prevRotation;
    }
    
    public void setPrevRotation(final float rotation) {
        this.prevRotation = (float)MCH_Lib.getRotate360(rotation);
    }
    
    public MCH_Blade setBaseRotation(final float rotation) {
        if (rotation >= 0.0) {
            this.baseRotation = rotation;
        }
        return this;
    }
    
    public float getBaseRotation() {
        return this.baseRotation;
    }
    
    public MCH_Blade setFoldSpeed(final float speed) {
        if (speed > 0.1) {
            this.foldSpeed = speed;
        }
        return this;
    }
    
    public MCH_Blade setFoldRotation(final float rotation) {
        if (rotation > this.foldSpeed * 2.0f) {
            this.foldRotation = rotation;
        }
        return this;
    }
    
    public void forceFold() {
        if (this.rotation > this.foldRotation && this.rotation < 360.0f - this.foldRotation) {
            if (this.rotation < 180.0f) {
                this.rotation = this.foldRotation;
            }
            else {
                this.rotation = 360.0f - this.foldRotation;
            }
            this.rotation %= 360.0f;
            this.prevRotation = this.rotation;
        }
    }
    
    public boolean folding() {
        boolean isCmpFolding = false;
        if (this.rotation > this.foldRotation && this.rotation < 360.0f - this.foldRotation) {
            if (this.rotation < 180.0f) {
                this.rotation -= this.foldSpeed;
            }
            else {
                this.rotation += this.foldSpeed;
            }
            this.rotation %= 360.0f;
        }
        else {
            isCmpFolding = true;
        }
        return isCmpFolding;
    }
    
    public boolean unfolding(final float rot) {
        boolean isCmpUnfolding = false;
        final float targetRot = (float)MCH_Lib.getRotate360(rot + this.baseRotation);
        final float prevRot = this.rotation;
        if (targetRot <= 180.0f) {
            this.rotation = (float)MCH_Lib.getRotate360(this.rotation + this.foldSpeed);
            if (this.rotation >= targetRot && prevRot <= targetRot) {
                this.rotation = targetRot;
                isCmpUnfolding = true;
            }
        }
        else {
            this.rotation = (float)MCH_Lib.getRotate360(this.rotation - this.foldSpeed);
            if (this.rotation <= targetRot && prevRot >= targetRot) {
                this.rotation = targetRot;
                isCmpUnfolding = true;
            }
        }
        return isCmpUnfolding;
    }
}
