package mcheli.hud;

import net.minecraft.entity.*;
import mcheli.*;

public class MCH_HudItemCameraRot extends MCH_HudItem
{
    private final String drawPosX;
    private final String drawPosY;
    
    public MCH_HudItemCameraRot(final int fileLine, final String posx, final String posy) {
        super(fileLine);
        this.drawPosX = MCH_HudItem.toFormula(posx);
        this.drawPosY = MCH_HudItem.toFormula(posy);
    }
    
    @Override
    public void execute() {
        this.drawCommonGunnerCamera(MCH_HudItemCameraRot.ac, MCH_HudItemCameraRot.ac.camera, MCH_HudItemCameraRot.colorSetting, MCH_HudItemCameraRot.centerX + MCH_HudItem.calc(this.drawPosX), MCH_HudItemCameraRot.centerY + MCH_HudItem.calc(this.drawPosY));
    }
    
    private void drawCommonGunnerCamera(final Entity ac, final MCH_Camera camera, final int color, final double posX, final double posY) {
        if (camera == null) {
            return;
        }
        final int WW = 20;
        final int WH = 10;
        final int LW = 1;
        double[] line = { posX - 21.0, posY - 11.0, posX + 21.0, posY - 11.0, posX + 21.0, posY + 11.0, posX - 21.0, posY + 11.0 };
        this.drawLine(line, color, 2);
        line = new double[] { posX - 21.0, posY, posX, posY, posX + 21.0, posY, posX, posY, posX, posY - 11.0, posX, posY, posX, posY + 11.0, posX, posY };
        this.drawLineStipple(line, color, 1, 52428);
        float pitch = camera.rotationPitch;
        if (pitch < -30.0f) {
            pitch = -30.0f;
        }
        if (pitch > 70.0f) {
            pitch = 70.0f;
        }
        pitch -= 20.0f;
        pitch *= 0.16;
        final float heliYaw = ac.field_70126_B + (ac.field_70177_z - ac.field_70126_B) / 2.0f;
        final float cameraYaw = camera.prevRotationYaw + (camera.rotationYaw - camera.prevRotationYaw) / 2.0f;
        float yaw = (float)MCH_Lib.getRotateDiff(ac.field_70177_z, camera.rotationYaw);
        yaw *= 2.0f;
        if (yaw < -50.0f) {
            yaw = -50.0f;
        }
        if (yaw > 50.0f) {
            yaw = 50.0f;
        }
        yaw *= 0.34;
        line = new double[] { posX + yaw - 3.0, posY + pitch - 2.0, posX + yaw + 3.0, posY + pitch - 2.0, posX + yaw + 3.0, posY + pitch + 2.0, posX + yaw - 3.0, posY + pitch + 2.0 };
        this.drawLine(line, color, 2);
    }
}
