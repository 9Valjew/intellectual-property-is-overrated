package mcheli;

import net.minecraft.util.*;
import java.nio.*;

public class MCH_Math
{
    public static float PI;
    public static MCH_Math instance;
    
    public FVector3D privateNewVec3D(final float x, final float y, final float z) {
        final FVector3D v = new FVector3D();
        v.x = x;
        v.y = y;
        v.z = z;
        return v;
    }
    
    public static FVector3D newVec3D() {
        return MCH_Math.instance.privateNewVec3D(0.0f, 0.0f, 0.0f);
    }
    
    public static FVector3D newVec3D(final float x, final float y, final float z) {
        return MCH_Math.instance.privateNewVec3D(x, y, z);
    }
    
    private FQuat privateNewQuat() {
        final FQuat q = new FQuat();
        QuatIdentity(q);
        return new FQuat();
    }
    
    public static FQuat newQuat() {
        return MCH_Math.instance.privateNewQuat();
    }
    
    private FMatrix privateNewMatrix() {
        final FMatrix m = new FMatrix();
        MatIdentity(m);
        return m;
    }
    
    public static FMatrix newMatrix() {
        return MCH_Math.instance.privateNewMatrix();
    }
    
    public static FQuat EulerToQuatTestNG(final float yaw, final float pitch, final float roll) {
        final FVector3D axis = newVec3D();
        final float rot = VecNormalize(axis);
        final FQuat dqtn = newQuat();
        QuatRotation(dqtn, rot, axis.x, axis.y, axis.z);
        return dqtn;
    }
    
    public static FMatrix EulerToMatrix(final float yaw, final float pitch, final float roll) {
        final FMatrix m = newMatrix();
        MatTurnZ(m, roll / 180.0f * MCH_Math.PI);
        MatTurnX(m, pitch / 180.0f * MCH_Math.PI);
        MatTurnY(m, yaw / 180.0f * MCH_Math.PI);
        return m;
    }
    
    public static FQuat EulerToQuat(final float yaw, final float pitch, final float roll) {
        final FQuat dqtn = newQuat();
        MatrixToQuat(dqtn, EulerToMatrix(yaw, pitch, roll));
        return dqtn;
    }
    
    public static FVector3D QuatToEuler(final FQuat q) {
        final FMatrix m = QuatToMatrix(q);
        return MatrixToEuler(m);
    }
    
    public static FVector3D MatrixToEuler(final FMatrix m) {
        final float xx = m.m00;
        final float xy = m.m01;
        final float xz = m.m02;
        final float yy = m.m11;
        final float zx = m.m20;
        final float zy = m.m21;
        final float zz = m.m22;
        float b = (float)(-Math.asin(zy));
        final float cosB = Cos(b);
        float c;
        float a;
        if (Math.abs(cosB) >= 1.0E-4) {
            c = Atan2(zx, zz);
            float xy_cos = xy / cosB;
            if (xy_cos > 1.0f) {
                xy_cos = 1.0f;
            }
            else if (xy_cos < -1.0f) {
                xy_cos = -1.0f;
            }
            a = (float)Math.asin(xy_cos);
            if (Float.isNaN(a)) {
                a = 0.0f;
            }
        }
        else {
            c = Atan2(-xz, xx);
            a = 0.0f;
        }
        a *= (float)(180.0 / MCH_Math.PI);
        b *= (float)(180.0 / MCH_Math.PI);
        c *= (float)(180.0 / MCH_Math.PI);
        if (yy < 0.0f) {
            a = 180.0f - a;
        }
        return newVec3D(-b, -c, -a);
    }
    
    public float atan2(final float y, final float x) {
        return Atan2(y, x);
    }
    
    public static float SIGN(final float x) {
        return (x >= 0.0f) ? 1.0f : -1.0f;
    }
    
    public static float NORM(final float a, final float b, final float c, final float d) {
        return (float)Math.sqrt(a * a + b * b + c * c + d * d);
    }
    
    public static void QuatNormalize(final FQuat q) {
        final float r = NORM(q.w, q.x, q.y, q.z);
        if (MathHelper.func_76135_e(r) > 1.0E-4) {
            q.w /= r;
            q.x /= r;
            q.y /= r;
            q.z /= r;
        }
    }
    
    public static boolean MatrixToQuat(final FQuat q, final FMatrix m) {
        q.w = (m.m00 + m.m11 + m.m22 + 1.0f) / 4.0f;
        q.x = (m.m00 - m.m11 - m.m22 + 1.0f) / 4.0f;
        q.y = (-m.m00 + m.m11 - m.m22 + 1.0f) / 4.0f;
        q.z = (-m.m00 - m.m11 + m.m22 + 1.0f) / 4.0f;
        if (q.w < 0.0f) {
            q.w = 0.0f;
        }
        if (q.x < 0.0f) {
            q.x = 0.0f;
        }
        if (q.y < 0.0f) {
            q.y = 0.0f;
        }
        if (q.z < 0.0f) {
            q.z = 0.0f;
        }
        q.w = (float)Math.sqrt(q.w);
        q.x = (float)Math.sqrt(q.x);
        q.y = (float)Math.sqrt(q.y);
        q.z = (float)Math.sqrt(q.z);
        if (q.w >= q.x && q.w >= q.y && q.w >= q.z) {
            q.w *= 1.0f;
            q.x *= SIGN(m.m21 - m.m12);
            q.y *= SIGN(m.m02 - m.m20);
            q.z *= SIGN(m.m10 - m.m01);
        }
        else if (q.x >= q.w && q.x >= q.y && q.x >= q.z) {
            q.w *= SIGN(m.m21 - m.m12);
            q.x *= 1.0f;
            q.y *= SIGN(m.m10 + m.m01);
            q.z *= SIGN(m.m02 + m.m20);
        }
        else if (q.y >= q.w && q.y >= q.x && q.y >= q.z) {
            q.w *= SIGN(m.m02 - m.m20);
            q.x *= SIGN(m.m10 + m.m01);
            q.y *= 1.0f;
            q.z *= SIGN(m.m21 + m.m12);
        }
        else {
            if (q.z < q.w || q.z < q.x || q.z < q.y) {
                QuatIdentity(q);
                return false;
            }
            q.w *= SIGN(m.m10 - m.m01);
            q.x *= SIGN(m.m20 + m.m02);
            q.y *= SIGN(m.m21 + m.m12);
            q.z *= 1.0f;
        }
        correctQuat(q);
        final float r = NORM(q.w, q.x, q.y, q.z);
        q.w /= r;
        q.x /= r;
        q.y /= r;
        q.z /= r;
        correctQuat(q);
        return true;
    }
    
    public static void correctQuat(final FQuat q) {
        if (Float.isNaN(q.w) || Float.isInfinite(q.w)) {
            q.w = 0.0f;
        }
        if (Float.isNaN(q.x) || Float.isInfinite(q.x)) {
            q.x = 0.0f;
        }
        if (Float.isNaN(q.y) || Float.isInfinite(q.y)) {
            q.y = 0.0f;
        }
        if (Float.isNaN(q.z) || Float.isInfinite(q.z)) {
            q.z = 0.0f;
        }
    }
    
    public static FQuat motionTest(final int x, final int y, final FQuat prevQtn) {
        final FVector3D axis = newVec3D();
        final FQuat dqtn = newQuat();
        axis.x = 2.0f * MCH_Math.PI * y / 200.0f;
        axis.y = 2.0f * MCH_Math.PI * x / 200.0f;
        axis.z = 0.0f;
        final float rot = VecNormalize(axis);
        QuatRotation(dqtn, rot, axis.x, axis.y, axis.z);
        return QuatMult(dqtn, prevQtn);
    }
    
    public static float Sin(final float rad) {
        return (float)Math.sin(rad);
    }
    
    public static float Cos(final float rad) {
        return (float)Math.cos(rad);
    }
    
    public static float Tan(final float rad) {
        return (float)Math.tan(rad);
    }
    
    public static float Floor(final float x) {
        return (float)Math.floor(x);
    }
    
    public static float Atan(final float x) {
        return (float)Math.atan(x);
    }
    
    public static float Atan2(final float y, final float x) {
        return (float)Math.atan2(y, x);
    }
    
    public static float Fabs(final float x) {
        return (x >= 0.0f) ? x : (-x);
    }
    
    public static float Sqrt(final float x) {
        return (float)Math.sqrt(x);
    }
    
    public static float InvSqrt(final float x) {
        return 1.0f / (float)Math.sqrt(x);
    }
    
    public static float Pow(final float a, final float b) {
        return (float)Math.pow(a, b);
    }
    
    public static float VecNormalize(final FVector3D lpV) {
        final float len2 = lpV.x * lpV.x + lpV.y * lpV.y + lpV.z * lpV.z;
        final float length = Sqrt(len2);
        if (length == 0.0f) {
            return 0.0f;
        }
        final float invLength = 1.0f / length;
        lpV.x *= invLength;
        lpV.y *= invLength;
        lpV.z *= invLength;
        return length;
    }
    
    public static float Vec2DNormalize(final FVector2D lpV) {
        final float len2 = lpV.x * lpV.x + lpV.y * lpV.y;
        final float length = Sqrt(len2);
        if (length == 0.0f) {
            return 0.0f;
        }
        final float invLength = 1.0f / length;
        lpV.x *= invLength;
        lpV.y *= invLength;
        return length;
    }
    
    public static FVector3D MatVector(final FMatrix lpM, final FVector3D lpV) {
        final FVector3D lpS = newVec3D();
        final float x = lpV.x;
        final float y = lpV.y;
        final float z = lpV.z;
        lpS.x = lpM.m00 * x + lpM.m01 * y + lpM.m02 * z + lpM.m03;
        lpS.y = lpM.m10 * x + lpM.m11 * y + lpM.m12 * z + lpM.m13;
        lpS.z = lpM.m20 * x + lpM.m21 * y + lpM.m22 * z + lpM.m23;
        return lpS;
    }
    
    public static FVector3D MatDirection(final FMatrix lpM, final FVector3D lpDir) {
        final FVector3D lpSDir = newVec3D();
        final float x = lpDir.x;
        final float y = lpDir.y;
        final float z = lpDir.z;
        lpSDir.x = lpM.m00 * x + lpM.m01 * y + lpM.m02 * z;
        lpSDir.y = lpM.m10 * x + lpM.m11 * y + lpM.m12 * z;
        lpSDir.z = lpM.m20 * x + lpM.m21 * y + lpM.m22 * z;
        return lpSDir;
    }
    
    public static void MatIdentity(final FMatrix lpM) {
        final float n = 0.0f;
        lpM.m32 = n;
        lpM.m31 = n;
        lpM.m30 = n;
        lpM.m23 = n;
        lpM.m21 = n;
        lpM.m20 = n;
        lpM.m13 = n;
        lpM.m12 = n;
        lpM.m10 = n;
        lpM.m03 = n;
        lpM.m02 = n;
        lpM.m01 = n;
        final float n2 = 1.0f;
        lpM.m33 = n2;
        lpM.m22 = n2;
        lpM.m11 = n2;
        lpM.m00 = n2;
    }
    
    public static void MatCopy(final FMatrix lpMa, final FMatrix lpMb) {
        lpMa.m00 = lpMb.m00;
        lpMa.m10 = lpMb.m10;
        lpMa.m20 = lpMb.m20;
        lpMa.m30 = lpMb.m30;
        lpMa.m01 = lpMb.m01;
        lpMa.m11 = lpMb.m11;
        lpMa.m21 = lpMb.m21;
        lpMa.m31 = lpMb.m31;
        lpMa.m02 = lpMb.m02;
        lpMa.m12 = lpMb.m12;
        lpMa.m22 = lpMb.m22;
        lpMa.m32 = lpMb.m32;
        lpMa.m03 = lpMb.m03;
        lpMa.m13 = lpMb.m13;
        lpMa.m23 = lpMb.m23;
        lpMa.m33 = lpMb.m33;
    }
    
    public static void MatTranslate(final FMatrix m, final float x, final float y, final float z) {
        final float m2 = m.m30;
        final float m3 = m.m31;
        final float m4 = m.m32;
        final float m5 = m.m33;
        m.m00 += m2 * x;
        m.m01 += m3 * x;
        m.m02 += m4 * x;
        m.m03 += m5 * x;
        m.m10 += m2 * y;
        m.m11 += m3 * y;
        m.m12 += m4 * y;
        m.m13 += m5 * y;
        m.m20 += m2 * z;
        m.m21 += m3 * z;
        m.m22 += m4 * z;
        m.m23 += m5 * z;
    }
    
    public static void MatMove(final FMatrix m, final float x, final float y, final float z) {
        m.m03 += m.m00 * x + m.m01 * y + m.m02 * z;
        m.m13 += m.m10 * x + m.m11 * y + m.m12 * z;
        m.m23 += m.m20 * x + m.m21 * y + m.m22 * z;
        m.m33 += m.m30 * x + m.m31 * y + m.m32 * z;
    }
    
    public static void MatRotateX(final FMatrix m, float rad) {
        if (rad > 2.0f * MCH_Math.PI || rad < -2.0f * MCH_Math.PI) {
            rad -= 2.0f * MCH_Math.PI * (int)(rad / (2.0f * MCH_Math.PI));
        }
        final float cosA = Cos(rad);
        final float sinA = Sin(rad);
        float tmp1 = m.m10;
        float tmp2 = m.m20;
        m.m10 = cosA * tmp1 - sinA * tmp2;
        m.m20 = sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m11;
        tmp2 = m.m21;
        m.m11 = cosA * tmp1 - sinA * tmp2;
        m.m21 = sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m12;
        tmp2 = m.m22;
        m.m12 = cosA * tmp1 - sinA * tmp2;
        m.m22 = sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m13;
        tmp2 = m.m23;
        m.m13 = cosA * tmp1 - sinA * tmp2;
        m.m23 = sinA * tmp1 + cosA * tmp2;
    }
    
    public static void MatRotateY(final FMatrix m, float rad) {
        if (rad > 2.0f * MCH_Math.PI || rad < -2.0f * MCH_Math.PI) {
            rad -= 2.0f * MCH_Math.PI * (int)(rad / (2.0f * MCH_Math.PI));
        }
        final float cosA = Cos(rad);
        final float sinA = Sin(rad);
        float tmp1 = m.m00;
        float tmp2 = m.m20;
        m.m00 = cosA * tmp1 + sinA * tmp2;
        m.m20 = -sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m01;
        tmp2 = m.m21;
        m.m01 = cosA * tmp1 + sinA * tmp2;
        m.m21 = -sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m02;
        tmp2 = m.m22;
        m.m02 = cosA * tmp1 + sinA * tmp2;
        m.m22 = -sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m03;
        tmp2 = m.m23;
        m.m03 = cosA * tmp1 + sinA * tmp2;
        m.m23 = -sinA * tmp1 + cosA * tmp2;
    }
    
    public static void MatRotateZ(final FMatrix m, float rad) {
        if (rad > 2.0f * MCH_Math.PI || rad < -2.0f * MCH_Math.PI) {
            rad -= 2.0f * MCH_Math.PI * (int)(rad / (2.0f * MCH_Math.PI));
        }
        final float cosA = Cos(rad);
        final float sinA = Sin(rad);
        float tmp1 = m.m00;
        float tmp2 = m.m10;
        m.m00 = cosA * tmp1 - sinA * tmp2;
        m.m10 = sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m01;
        tmp2 = m.m11;
        m.m01 = cosA * tmp1 - sinA * tmp2;
        m.m11 = sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m02;
        tmp2 = m.m12;
        m.m02 = cosA * tmp1 - sinA * tmp2;
        m.m12 = sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m03;
        tmp2 = m.m13;
        m.m03 = cosA * tmp1 - sinA * tmp2;
        m.m13 = sinA * tmp1 + cosA * tmp2;
    }
    
    public static void MatTurnX(final FMatrix m, float rad) {
        if (rad > 2.0f * MCH_Math.PI || rad < -2.0f * MCH_Math.PI) {
            rad -= 2.0f * MCH_Math.PI * (int)(rad / (2.0f * MCH_Math.PI));
        }
        final float cosA = Cos(rad);
        final float sinA = Sin(rad);
        float tmp1 = m.m01;
        float tmp2 = m.m02;
        m.m01 = cosA * tmp1 + sinA * tmp2;
        m.m02 = -sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m11;
        tmp2 = m.m12;
        m.m11 = cosA * tmp1 + sinA * tmp2;
        m.m12 = -sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m21;
        tmp2 = m.m22;
        m.m21 = cosA * tmp1 + sinA * tmp2;
        m.m22 = -sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m31;
        tmp2 = m.m32;
        m.m31 = cosA * tmp1 + sinA * tmp2;
        m.m32 = -sinA * tmp1 + cosA * tmp2;
    }
    
    public static void MatTurnY(final FMatrix m, float rad) {
        if (rad > 2.0f * MCH_Math.PI || rad < -2.0f * MCH_Math.PI) {
            rad -= 2.0f * MCH_Math.PI * (int)(rad / (2.0f * MCH_Math.PI));
        }
        final float cosA = Cos(rad);
        final float sinA = Sin(rad);
        float tmp1 = m.m00;
        float tmp2 = m.m02;
        m.m00 = cosA * tmp1 - sinA * tmp2;
        m.m02 = sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m10;
        tmp2 = m.m12;
        m.m10 = cosA * tmp1 - sinA * tmp2;
        m.m12 = sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m20;
        tmp2 = m.m22;
        m.m20 = cosA * tmp1 - sinA * tmp2;
        m.m22 = sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m30;
        tmp2 = m.m32;
        m.m30 = cosA * tmp1 - sinA * tmp2;
        m.m32 = sinA * tmp1 + cosA * tmp2;
    }
    
    public static void MatTurnZ(final FMatrix m, float rad) {
        if (rad > 2.0f * MCH_Math.PI || rad < -2.0f * MCH_Math.PI) {
            rad -= 2.0f * MCH_Math.PI * (int)(rad / (2.0f * MCH_Math.PI));
        }
        final float cosA = Cos(rad);
        final float sinA = Sin(rad);
        float tmp1 = m.m00;
        float tmp2 = m.m01;
        m.m00 = cosA * tmp1 + sinA * tmp2;
        m.m01 = -sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m10;
        tmp2 = m.m11;
        m.m10 = cosA * tmp1 + sinA * tmp2;
        m.m11 = -sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m20;
        tmp2 = m.m21;
        m.m20 = cosA * tmp1 + sinA * tmp2;
        m.m21 = -sinA * tmp1 + cosA * tmp2;
        tmp1 = m.m30;
        tmp2 = m.m31;
        m.m30 = cosA * tmp1 + sinA * tmp2;
        m.m31 = -sinA * tmp1 + cosA * tmp2;
    }
    
    public static void MatScale(final FMatrix lpM, final float scalex, final float scaley, final float scalez) {
        lpM.m00 *= scalex;
        lpM.m01 *= scalex;
        lpM.m02 *= scalex;
        lpM.m03 *= scalex;
        lpM.m10 *= scaley;
        lpM.m11 *= scaley;
        lpM.m12 *= scaley;
        lpM.m13 *= scaley;
        lpM.m20 *= scalez;
        lpM.m21 *= scalez;
        lpM.m22 *= scalez;
        lpM.m23 *= scalez;
    }
    
    public static void MatSize(final FMatrix lpM, final float scalex, final float scaley, final float scalez) {
        lpM.m00 *= scalex;
        lpM.m01 *= scaley;
        lpM.m02 *= scalez;
        lpM.m10 *= scalex;
        lpM.m11 *= scaley;
        lpM.m12 *= scalez;
        lpM.m20 *= scalex;
        lpM.m21 *= scaley;
        lpM.m22 *= scalez;
        lpM.m30 *= scalex;
        lpM.m31 *= scaley;
        lpM.m32 *= scalez;
    }
    
    public static FQuat QuatMult(final FQuat lpP, final FQuat lpQ) {
        final FQuat lpR = newQuat();
        final float pw = lpP.w;
        final float px = lpP.x;
        final float py = lpP.y;
        final float pz = lpP.z;
        final float qw = lpQ.w;
        final float qx = lpQ.x;
        final float qy = lpQ.y;
        final float qz = lpQ.z;
        lpR.w = pw * qw - px * qx - py * qy - pz * qz;
        lpR.x = pw * qx + px * qw + py * qz - pz * qy;
        lpR.y = pw * qy - px * qz + py * qw + pz * qx;
        lpR.z = pw * qz + px * qy - py * qx + pz * qw;
        return lpR;
    }
    
    public static void QuatAdd(final FQuat q_out, final FQuat q) {
        q_out.w += q.w;
        q_out.x += q.x;
        q_out.y += q.y;
        q_out.z += q.z;
    }
    
    public static FMatrix QuatToMatrix(final FQuat lpQ) {
        final FMatrix lpM = newMatrix();
        final float qw = lpQ.w;
        final float qx = lpQ.x;
        final float qy = lpQ.y;
        final float qz = lpQ.z;
        final float x2 = 2.0f * qx * qx;
        final float y2 = 2.0f * qy * qy;
        final float z2 = 2.0f * qz * qz;
        final float xy = 2.0f * qx * qy;
        final float yz = 2.0f * qy * qz;
        final float zx = 2.0f * qz * qx;
        final float wx = 2.0f * qw * qx;
        final float wy = 2.0f * qw * qy;
        final float wz = 2.0f * qw * qz;
        lpM.m00 = 1.0f - y2 - z2;
        lpM.m01 = xy - wz;
        lpM.m02 = zx + wy;
        lpM.m03 = 0.0f;
        lpM.m10 = xy + wz;
        lpM.m11 = 1.0f - z2 - x2;
        lpM.m12 = yz - wx;
        lpM.m13 = 0.0f;
        lpM.m20 = zx - wy;
        lpM.m21 = yz + wx;
        lpM.m22 = 1.0f - x2 - y2;
        lpM.m23 = 0.0f;
        final FMatrix fMatrix = lpM;
        final FMatrix fMatrix2 = lpM;
        final FMatrix fMatrix3 = lpM;
        final float m30 = 0.0f;
        fMatrix3.m32 = m30;
        fMatrix2.m31 = m30;
        fMatrix.m30 = m30;
        lpM.m33 = 1.0f;
        return lpM;
    }
    
    public static void QuatRotation(final FQuat lpQ, final float rad, final float ax, final float ay, final float az) {
        final float hrad = 0.5f * rad;
        final float s = Sin(hrad);
        lpQ.w = Cos(hrad);
        lpQ.x = s * ax;
        lpQ.y = s * ay;
        lpQ.z = s * az;
    }
    
    public static void QuatIdentity(final FQuat lpQ) {
        lpQ.w = 1.0f;
        lpQ.x = 0.0f;
        lpQ.y = 0.0f;
        lpQ.z = 0.0f;
    }
    
    public static void QuatCopy(final FQuat lpTo, final FQuat lpFrom) {
        lpTo.w = lpFrom.w;
        lpTo.x = lpFrom.x;
        lpTo.y = lpFrom.y;
        lpTo.z = lpFrom.z;
    }
    
    static {
        MCH_Math.PI = 3.1415927f;
        MCH_Math.instance = new MCH_Math();
    }
    
    public class FQuat
    {
        public float w;
        public float x;
        public float y;
        public float z;
    }
    
    public class FVector3D
    {
        public float x;
        public float y;
        public float z;
    }
    
    public class FVector2D
    {
        public float x;
        public float y;
    }
    
    public class FMatrix
    {
        float m00;
        float m10;
        float m20;
        float m30;
        float m01;
        float m11;
        float m21;
        float m31;
        float m02;
        float m12;
        float m22;
        float m32;
        float m03;
        float m13;
        float m23;
        float m33;
        
        public FloatBuffer toFloatBuffer() {
            final ByteBuffer bb = ByteBuffer.allocateDirect(64);
            final FloatBuffer fb = bb.asFloatBuffer();
            fb.put(this.m00);
            fb.put(this.m10);
            fb.put(this.m20);
            fb.put(this.m30);
            fb.put(this.m01);
            fb.put(this.m11);
            fb.put(this.m21);
            fb.put(this.m31);
            fb.put(this.m02);
            fb.put(this.m12);
            fb.put(this.m22);
            fb.put(this.m32);
            fb.put(this.m03);
            fb.put(this.m13);
            fb.put(this.m23);
            fb.put(this.m33);
            float f = fb.get(0);
            f = fb.get(1);
            fb.position(0);
            return fb;
        }
    }
}
