package mcheli.aircraft;

public class MCH_Rotor
{
    public MCH_Blade[] blades;
    private int numBlade;
    private int invRot;
    private boolean isFoldBlade;
    private boolean isFoldBladeTarget;
    private boolean haveFoldBladeFunc;
    
    public MCH_Rotor(final int numBlade, final int invRot, final int foldSpeed, final float posx, final float posy, final float posz, final float rotx, final float roty, final float rotz, final boolean canFoldBlade) {
        this.setupBlade(numBlade, invRot, foldSpeed);
        this.isFoldBlade = false;
        this.isFoldBladeTarget = false;
        this.haveFoldBladeFunc = canFoldBlade;
        this.setPostion(posx, posy, posz);
        this.setRotation(rotx, roty, rotz);
    }
    
    public MCH_Rotor setPostion(final float x, final float y, final float z) {
        return this;
    }
    
    public MCH_Rotor setRotation(final float x, final float y, final float z) {
        return this;
    }
    
    public int getBladeNum() {
        return this.numBlade;
    }
    
    public void setupBlade(final int num, final int invRot, final int foldSpeed) {
        this.invRot = ((invRot > 0) ? invRot : 1);
        this.numBlade = ((num > 0) ? num : 1);
        this.blades = new MCH_Blade[this.numBlade];
        for (int i = 0; i < this.numBlade; ++i) {
            this.blades[i] = new MCH_Blade(i * this.invRot);
            this.blades[i].setFoldRotation(5 + i * 3).setFoldSpeed(foldSpeed);
        }
    }
    
    public boolean isFoldingOrUnfolding() {
        return this.isFoldBlade != this.isFoldBladeTarget;
    }
    
    public float getBladeRotaion(final int bladeIndex) {
        if (bladeIndex >= this.numBlade) {
            return 0.0f;
        }
        return this.blades[bladeIndex].getRotation();
    }
    
    public void startUnfold() {
        this.isFoldBladeTarget = false;
    }
    
    public void startFold() {
        if (this.haveFoldBladeFunc) {
            this.isFoldBladeTarget = true;
        }
    }
    
    public void forceFold() {
        if (this.haveFoldBladeFunc) {
            this.isFoldBladeTarget = true;
            this.isFoldBlade = true;
            for (final MCH_Blade b : this.blades) {
                b.forceFold();
            }
        }
    }
    
    public void update(final float rot) {
        boolean isCmpFoldUnfold = true;
        for (final MCH_Blade b : this.blades) {
            b.setPrevRotation(b.getRotation());
            if (!this.isFoldBlade) {
                if (!this.isFoldBladeTarget) {
                    b.setRotation(rot + b.getBaseRotation());
                    isCmpFoldUnfold = false;
                }
                else if (!b.folding()) {
                    isCmpFoldUnfold = false;
                }
            }
            else if (this.isFoldBladeTarget) {
                isCmpFoldUnfold = false;
            }
            else if (!b.unfolding(rot)) {
                isCmpFoldUnfold = false;
            }
        }
        if (isCmpFoldUnfold) {
            this.isFoldBlade = this.isFoldBladeTarget;
        }
    }
}
