package mcheli.hud;

import org.lwjgl.opengl.*;
import mcheli.*;

public class MCH_HudItemGraduation extends MCH_HudItem
{
    private final String drawRot;
    private final String drawRoll;
    private final String drawPosX;
    private final String drawPosY;
    private final int type;
    
    public MCH_HudItemGraduation(final int fileLine, final int type, final String rot, final String roll, final String posx, final String posy) {
        super(fileLine);
        this.drawRot = MCH_HudItem.toFormula(rot);
        this.drawRoll = MCH_HudItem.toFormula(roll);
        this.drawPosX = MCH_HudItem.toFormula(posx);
        this.drawPosY = MCH_HudItem.toFormula(posy);
        this.type = type;
    }
    
    @Override
    public void execute() {
        GL11.glPushMatrix();
        final int x = (int)(MCH_HudItemGraduation.centerX + MCH_HudItem.calc(this.drawPosX));
        final int y = (int)(MCH_HudItemGraduation.centerY + MCH_HudItem.calc(this.drawPosY));
        GL11.glTranslated((double)x, (double)y, 0.0);
        GL11.glRotatef((float)MCH_HudItem.calc(this.drawRoll), 0.0f, 0.0f, 1.0f);
        GL11.glTranslated((double)(-x), (double)(-y), 0.0);
        if (this.type == 0) {
            this.drawCommonGraduationYaw(MCH_HudItem.calc(this.drawRot), MCH_HudItemGraduation.colorSetting, x, y);
        }
        else if (this.type == 1) {
            this.drawCommonGraduationPitch1(MCH_HudItem.calc(this.drawRot), MCH_HudItemGraduation.colorSetting, x, y);
        }
        else if (this.type == 2 || this.type == 3) {
            this.drawCommonGraduationPitch2(MCH_HudItem.calc(this.drawRot), MCH_HudItemGraduation.colorSetting, x, y);
        }
        GL11.glPopMatrix();
    }
    
    private void drawCommonGraduationPitch2(double playerPitch, final int color, final int posX, final int posY) {
        playerPitch = -playerPitch;
        final int pitch_n = (int)playerPitch / 5 * 5;
        final double[] line = new double[8];
        final int start = (this.type != 2) ? 1 : 0;
        final int end = (this.type == 2) ? 5 : 4;
        final int INT = (this.type == 2) ? 1 : 2;
        for (int i = start; i < end; ++i) {
            final int pitch = -(-pitch_n - 10 + i * 5);
            final double p_rest = playerPitch % 5.0;
            final int XO = 50;
            final int XI = 30;
            final int x = (pitch != 0) ? 50 : 100;
            final int y = posY + (int)(-60 * INT + p_rest * 6.0 * INT + i * 30 * INT);
            line[0] = posX - x;
            line[1] = y + ((pitch == 0) ? 0 : ((pitch > 0) ? 2 : -2));
            line[2] = posX - 50;
            line[3] = y;
            line[4] = posX + x;
            line[5] = line[1];
            line[6] = posX + 50;
            line[7] = y;
            this.drawLine(line, color);
            line[0] = posX - 50;
            line[1] = y;
            line[2] = posX - 30;
            line[3] = y;
            line[4] = posX + 50;
            line[5] = y;
            line[6] = posX + 30;
            line[7] = y;
            if (pitch >= 0) {
                this.drawLine(line, color);
            }
            else {
                this.drawLineStipple(line, color, 1, 52428);
            }
            if (pitch != 0) {
                this.drawCenteredString("" + pitch, posX - 50 - 10, y - 4, color);
                this.drawCenteredString("" + pitch, posX + 50 + 10, y - 4, color);
            }
        }
    }
    
    private void drawCommonGraduationPitch1(final double playerPitch, final int color, final int posX, int posY) {
        final int pitch = (int)playerPitch % 360;
        final int INVY = 10;
        int y = (int)(playerPitch * 10.0 % 10.0);
        if (y < 0) {
            y += 10;
        }
        final int GW = 100;
        final int posX_L = posX - 100;
        final int posX_R = posX + 100;
        final int linePosY = posY;
        posY -= 80;
        final double[] line = new double[144];
        for (int p = (playerPitch >= 0.0 || y == 0) ? (pitch - 8) : (pitch - 9), i = 0; i < line.length / 8; ++i, ++p) {
            final int olx = (p % 3 == 0) ? 15 : 5;
            final int ilx = 0;
            line[i * 8 + 0] = posX_L - olx;
            line[i * 8 + 1] = posY + i * 10 - y;
            line[i * 8 + 2] = posX_L + ilx;
            line[i * 8 + 3] = posY + i * 10 - y;
            line[i * 8 + 4] = posX_R + olx;
            line[i * 8 + 5] = posY + i * 10 - y;
            line[i * 8 + 6] = posX_R - ilx;
            line[i * 8 + 7] = posY + i * 10 - y;
        }
        this.drawLine(line, color);
        double[] verticalLine = { posX_L - 25, linePosY - 90, posX_L, linePosY - 90, posX_L, linePosY + 90, posX_L - 25, linePosY + 90 };
        this.drawLine(verticalLine, color, 3);
        verticalLine = new double[] { posX_R + 25, linePosY - 90, posX_R, linePosY - 90, posX_R, linePosY + 90, posX_R + 25, linePosY + 90 };
        this.drawLine(verticalLine, color, 3);
    }
    
    private void drawCommonGraduationYaw(final double playerYaw, final int color, int posX, final int posY) {
        final double yaw = MCH_Lib.getRotate360(playerYaw);
        final int INVX = 10;
        posX -= 90;
        final double[] line = new double[76];
        final int x = (int)(yaw * 10.0) % 10;
        for (int y = (int)yaw - 9, i = 0; i < line.length / 4; ++i, ++y) {
            final int azPosX = posX + i * 10 - x;
            line[i * 4 + 0] = azPosX;
            line[i * 4 + 1] = posY;
            line[i * 4 + 2] = azPosX;
            line[i * 4 + 3] = posY + ((y % 45 == 0) ? 15 : ((y % 3 == 0) ? 10 : 5));
            if (y % 45 == 0) {
                this.drawCenteredString(MCH_Lib.getAzimuthStr8(y), azPosX, posY - 10, -65536);
            }
            else if (y % 3 == 0) {
                int rot = y + 180;
                if (rot < 0) {
                    rot += 360;
                }
                if (rot > 360) {
                    rot -= 360;
                }
                this.drawCenteredString(String.format("%d", rot), azPosX, posY - 10, color);
            }
        }
        this.drawLine(line, color);
    }
}
