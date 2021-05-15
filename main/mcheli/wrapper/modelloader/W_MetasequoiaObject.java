package mcheli.wrapper.modelloader;

import cpw.mods.fml.relauncher.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraftforge.client.model.*;
import net.minecraft.client.resources.*;
import java.net.*;
import java.util.*;
import java.io.*;
import net.minecraft.client.renderer.*;

@SideOnly(Side.CLIENT)
public class W_MetasequoiaObject extends W_ModelCustom
{
    public ArrayList<W_Vertex> vertices;
    public ArrayList<W_GroupObject> groupObjects;
    private W_GroupObject currentGroupObject;
    private String fileName;
    private int vertexNum;
    private int faceNum;
    
    public W_MetasequoiaObject(final ResourceLocation resource) throws ModelFormatException {
        this.vertices = new ArrayList<W_Vertex>();
        this.groupObjects = new ArrayList<W_GroupObject>();
        this.currentGroupObject = null;
        this.vertexNum = 0;
        this.faceNum = 0;
        this.fileName = resource.toString();
        try {
            final IResource res = Minecraft.func_71410_x().func_110442_L().func_110536_a(resource);
            this.loadObjModel(res.func_110527_b());
        }
        catch (IOException e) {
            throw new ModelFormatException("IO Exception reading model format:" + this.fileName, (Throwable)e);
        }
    }
    
    public W_MetasequoiaObject(final String fileName, final URL resource) throws ModelFormatException {
        this.vertices = new ArrayList<W_Vertex>();
        this.groupObjects = new ArrayList<W_GroupObject>();
        this.currentGroupObject = null;
        this.vertexNum = 0;
        this.faceNum = 0;
        this.fileName = fileName;
        try {
            this.loadObjModel(resource.openStream());
        }
        catch (IOException e) {
            throw new ModelFormatException("IO Exception reading model format:" + this.fileName, (Throwable)e);
        }
    }
    
    public W_MetasequoiaObject(final String filename, final InputStream inputStream) throws ModelFormatException {
        this.vertices = new ArrayList<W_Vertex>();
        this.groupObjects = new ArrayList<W_GroupObject>();
        this.currentGroupObject = null;
        this.vertexNum = 0;
        this.faceNum = 0;
        this.fileName = filename;
        this.loadObjModel(inputStream);
    }
    
    @Override
    public boolean containsPart(final String partName) {
        for (final W_GroupObject groupObject : this.groupObjects) {
            if (partName.equalsIgnoreCase(groupObject.name)) {
                return true;
            }
        }
        return false;
    }
    
    private void loadObjModel(final InputStream inputStream) throws ModelFormatException {
        BufferedReader reader = null;
        String currentLine = null;
        int lineCount = 0;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((currentLine = reader.readLine()) != null) {
                ++lineCount;
                currentLine = currentLine.replaceAll("\\s+", " ").trim();
                if (isValidGroupObjectLine(currentLine)) {
                    final W_GroupObject group = this.parseGroupObject(currentLine, lineCount);
                    if (group == null) {
                        continue;
                    }
                    group.glDrawingMode = 4;
                    this.vertices.clear();
                    int vertexNum = 0;
                    boolean mirror = false;
                    double facet = Math.cos(0.785398163375);
                    boolean shading = false;
                    while ((currentLine = reader.readLine()) != null) {
                        ++lineCount;
                        currentLine = currentLine.replaceAll("\\s+", " ").trim();
                        if (currentLine.equalsIgnoreCase("mirror 1")) {
                            mirror = true;
                        }
                        if (currentLine.equalsIgnoreCase("shading 1")) {
                            shading = true;
                        }
                        final String[] s = currentLine.split(" ");
                        if (s.length == 2 && s[0].equalsIgnoreCase("facet")) {
                            facet = Math.cos(Double.parseDouble(s[1]) * 3.1415926535 / 180.0);
                        }
                        if (isValidVertexLine(currentLine)) {
                            vertexNum = Integer.valueOf(currentLine.split(" ")[1]);
                            break;
                        }
                    }
                    if (vertexNum > 0) {
                        while ((currentLine = reader.readLine()) != null) {
                            ++lineCount;
                            currentLine = currentLine.replaceAll("\\s+", " ").trim();
                            final String[] s = currentLine.split(" ");
                            if (s.length == 3) {
                                final W_Vertex v = new W_Vertex(Float.valueOf(s[0]) / 100.0f, Float.valueOf(s[1]) / 100.0f, Float.valueOf(s[2]) / 100.0f);
                                this.checkMinMax(v);
                                this.vertices.add(v);
                                if (--vertexNum <= 0) {
                                    break;
                                }
                                continue;
                            }
                            else {
                                if (s.length > 0) {
                                    throw new ModelFormatException("format error : " + this.fileName + " : line=" + lineCount);
                                }
                                continue;
                            }
                        }
                        int faceNum = 0;
                        while ((currentLine = reader.readLine()) != null) {
                            ++lineCount;
                            currentLine = currentLine.replaceAll("\\s+", " ").trim();
                            if (isValidFaceLine(currentLine)) {
                                faceNum = Integer.valueOf(currentLine.split(" ")[1]);
                                break;
                            }
                        }
                        if (faceNum > 0) {
                            while ((currentLine = reader.readLine()) != null) {
                                ++lineCount;
                                currentLine = currentLine.replaceAll("\\s+", " ").trim();
                                final String[] s2 = currentLine.split(" ");
                                if (s2.length > 2) {
                                    if (Integer.valueOf(s2[0]) >= 3) {
                                        final W_Face[] arr$;
                                        final W_Face[] faces = arr$ = this.parseFace(currentLine, lineCount, mirror);
                                        for (final W_Face face : arr$) {
                                            group.faces.add(face);
                                        }
                                    }
                                    if (--faceNum <= 0) {
                                        break;
                                    }
                                    continue;
                                }
                                else {
                                    if (s2.length > 2 && Integer.valueOf(s2[0]) != 3) {
                                        throw new ModelFormatException("found face is not triangle : " + this.fileName + " : line=" + lineCount);
                                    }
                                    continue;
                                }
                            }
                            this.calcVerticesNormal(group, shading, facet);
                        }
                    }
                    this.vertexNum += this.vertices.size();
                    this.faceNum += group.faces.size();
                    this.vertices.clear();
                    this.groupObjects.add(group);
                }
            }
        }
        catch (IOException e) {
            throw new ModelFormatException("IO Exception reading model format : " + this.fileName, (Throwable)e);
        }
        finally {
            this.checkMinMaxFinal();
            this.vertices = null;
            try {
                reader.close();
            }
            catch (IOException ex) {}
            try {
                inputStream.close();
            }
            catch (IOException ex2) {}
        }
    }
    
    public void calcVerticesNormal(final W_GroupObject group, final boolean shading, final double facet) {
        for (final W_Face f : group.faces) {
            f.vertexNormals = new W_Vertex[f.verticesID.length];
            for (int i = 0; i < f.verticesID.length; ++i) {
                final W_Vertex vn = this.getVerticesNormalFromFace(f.faceNormal, f.verticesID[i], group, (float)facet);
                vn.normalize();
                if (shading) {
                    if (f.faceNormal.x * vn.x + f.faceNormal.y * vn.y + f.faceNormal.z * vn.z >= facet) {
                        f.vertexNormals[i] = vn;
                    }
                    else {
                        f.vertexNormals[i] = f.faceNormal;
                    }
                }
                else {
                    f.vertexNormals[i] = f.faceNormal;
                }
            }
        }
    }
    
    public W_Vertex getVerticesNormalFromFace(final W_Vertex faceNormal, final int verticesID, final W_GroupObject group, final float facet) {
        final W_Vertex v = new W_Vertex(0.0f, 0.0f, 0.0f);
        for (final W_Face f : group.faces) {
            final int[] arr$ = f.verticesID;
            final int len$ = arr$.length;
            int i$2 = 0;
            while (i$2 < len$) {
                final int id = arr$[i$2];
                if (id == verticesID) {
                    if (f.faceNormal.x * faceNormal.x + f.faceNormal.y * faceNormal.y + f.faceNormal.z * faceNormal.z >= facet) {
                        v.add(f.faceNormal);
                        break;
                    }
                    break;
                }
                else {
                    ++i$2;
                }
            }
        }
        v.normalize();
        return v;
    }
    
    public void renderAll() {
        final Tessellator tessellator = Tessellator.field_78398_a;
        if (this.currentGroupObject != null) {
            tessellator.func_78371_b(this.currentGroupObject.glDrawingMode);
        }
        else {
            tessellator.func_78371_b(4);
        }
        this.tessellateAll(tessellator);
        tessellator.func_78381_a();
    }
    
    public void tessellateAll(final Tessellator tessellator) {
        for (final W_GroupObject groupObject : this.groupObjects) {
            groupObject.render(tessellator);
        }
    }
    
    public void renderOnly(final String... groupNames) {
        for (final W_GroupObject groupObject : this.groupObjects) {
            for (final String groupName : groupNames) {
                if (groupName.equalsIgnoreCase(groupObject.name)) {
                    groupObject.render();
                }
            }
        }
    }
    
    public void tessellateOnly(final Tessellator tessellator, final String... groupNames) {
        for (final W_GroupObject groupObject : this.groupObjects) {
            for (final String groupName : groupNames) {
                if (groupName.equalsIgnoreCase(groupObject.name)) {
                    groupObject.render(tessellator);
                }
            }
        }
    }
    
    public void renderPart(final String partName) {
        if (partName.charAt(0) == '$') {
            for (int i = 0; i < this.groupObjects.size(); ++i) {
                W_GroupObject groupObject = this.groupObjects.get(i);
                if (partName.equalsIgnoreCase(groupObject.name)) {
                    groupObject.render();
                    ++i;
                    while (i < this.groupObjects.size()) {
                        groupObject = this.groupObjects.get(i);
                        if (groupObject.name.charAt(0) == '$') {
                            break;
                        }
                        groupObject.render();
                        ++i;
                    }
                }
            }
        }
        else {
            for (final W_GroupObject groupObject : this.groupObjects) {
                if (partName.equalsIgnoreCase(groupObject.name)) {
                    groupObject.render();
                }
            }
        }
    }
    
    public void tessellatePart(final Tessellator tessellator, final String partName) {
        for (final W_GroupObject groupObject : this.groupObjects) {
            if (partName.equalsIgnoreCase(groupObject.name)) {
                groupObject.render(tessellator);
            }
        }
    }
    
    public void renderAllExcept(final String... excludedGroupNames) {
        for (final W_GroupObject groupObject : this.groupObjects) {
            boolean skipPart = false;
            for (final String excludedGroupName : excludedGroupNames) {
                if (excludedGroupName.equalsIgnoreCase(groupObject.name)) {
                    skipPart = true;
                }
            }
            if (!skipPart) {
                groupObject.render();
            }
        }
    }
    
    public void tessellateAllExcept(final Tessellator tessellator, final String... excludedGroupNames) {
        for (final W_GroupObject groupObject : this.groupObjects) {
            boolean exclude = false;
            for (final String excludedGroupName : excludedGroupNames) {
                if (excludedGroupName.equalsIgnoreCase(groupObject.name)) {
                    exclude = true;
                }
            }
            if (!exclude) {
                groupObject.render(tessellator);
            }
        }
    }
    
    private W_Face[] parseFace(final String line, final int lineCount, final boolean mirror) {
        final String[] s = line.split("[ VU)(M]+");
        final int vnum = Integer.valueOf(s[0]);
        if (vnum != 3 && vnum != 4) {
            return new W_Face[0];
        }
        if (vnum == 3) {
            final W_Face face = new W_Face();
            face.verticesID = new int[] { Integer.valueOf(s[3]), Integer.valueOf(s[2]), Integer.valueOf(s[1]) };
            face.vertices = new W_Vertex[] { this.vertices.get(face.verticesID[0]), this.vertices.get(face.verticesID[1]), this.vertices.get(face.verticesID[2]) };
            if (s.length >= 11) {
                face.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(Float.valueOf(s[9]), Float.valueOf(s[10])), new W_TextureCoordinate(Float.valueOf(s[7]), Float.valueOf(s[8])), new W_TextureCoordinate(Float.valueOf(s[5]), Float.valueOf(s[6])) };
            }
            else {
                face.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(0.0f, 0.0f), new W_TextureCoordinate(0.0f, 0.0f), new W_TextureCoordinate(0.0f, 0.0f) };
            }
            face.faceNormal = face.calculateFaceNormal();
            return new W_Face[] { face };
        }
        final W_Face face2 = new W_Face();
        face2.verticesID = new int[] { Integer.valueOf(s[3]), Integer.valueOf(s[2]), Integer.valueOf(s[1]) };
        face2.vertices = new W_Vertex[] { this.vertices.get(face2.verticesID[0]), this.vertices.get(face2.verticesID[1]), this.vertices.get(face2.verticesID[2]) };
        if (s.length >= 12) {
            face2.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(Float.valueOf(s[10]), Float.valueOf(s[11])), new W_TextureCoordinate(Float.valueOf(s[8]), Float.valueOf(s[9])), new W_TextureCoordinate(Float.valueOf(s[6]), Float.valueOf(s[7])) };
        }
        else {
            face2.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(0.0f, 0.0f), new W_TextureCoordinate(0.0f, 0.0f), new W_TextureCoordinate(0.0f, 0.0f) };
        }
        face2.faceNormal = face2.calculateFaceNormal();
        final W_Face face3 = new W_Face();
        face3.verticesID = new int[] { Integer.valueOf(s[4]), Integer.valueOf(s[3]), Integer.valueOf(s[1]) };
        face3.vertices = new W_Vertex[] { this.vertices.get(face3.verticesID[0]), this.vertices.get(face3.verticesID[1]), this.vertices.get(face3.verticesID[2]) };
        if (s.length >= 14) {
            face3.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(Float.valueOf(s[12]), Float.valueOf(s[13])), new W_TextureCoordinate(Float.valueOf(s[10]), Float.valueOf(s[11])), new W_TextureCoordinate(Float.valueOf(s[6]), Float.valueOf(s[7])) };
        }
        else {
            face3.textureCoordinates = new W_TextureCoordinate[] { new W_TextureCoordinate(0.0f, 0.0f), new W_TextureCoordinate(0.0f, 0.0f), new W_TextureCoordinate(0.0f, 0.0f) };
        }
        face3.faceNormal = face3.calculateFaceNormal();
        return new W_Face[] { face2, face3 };
    }
    
    private static boolean isValidGroupObjectLine(final String line) {
        final String[] s = line.split(" ");
        return s.length >= 2 && s[0].equals("Object") && s[1].length() >= 4 && s[1].charAt(0) == '\"';
    }
    
    private W_GroupObject parseGroupObject(final String line, final int lineCount) throws ModelFormatException {
        W_GroupObject group = null;
        if (isValidGroupObjectLine(line)) {
            final String[] s = line.split(" ");
            final String trimmedLine = s[1].substring(1, s[1].length() - 1);
            if (trimmedLine.length() > 0) {
                group = new W_GroupObject(trimmedLine);
            }
            return group;
        }
        throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
    }
    
    private static boolean isValidVertexLine(final String line) {
        final String[] s = line.split(" ");
        return s[0].equals("vertex");
    }
    
    private static boolean isValidFaceLine(final String line) {
        final String[] s = line.split(" ");
        return s[0].equals("face");
    }
    
    public String getType() {
        return "mqo";
    }
    
    @Override
    public void renderAllLine(final int startLine, final int maxLine) {
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78371_b(1);
        this.renderAllLine(tessellator, startLine, maxLine);
        tessellator.func_78381_a();
    }
    
    public void renderAllLine(final Tessellator tessellator, final int startLine, final int maxLine) {
        int lineCnt = 0;
        for (final W_GroupObject groupObject : this.groupObjects) {
            if (groupObject.faces.size() > 0) {
                for (final W_Face face : groupObject.faces) {
                    for (int i = 0; i < face.vertices.length / 3; ++i) {
                        final W_Vertex v1 = face.vertices[i * 3 + 0];
                        final W_Vertex v2 = face.vertices[i * 3 + 1];
                        final W_Vertex v3 = face.vertices[i * 3 + 2];
                        if (++lineCnt > maxLine) {
                            return;
                        }
                        tessellator.func_78377_a((double)v1.x, (double)v1.y, (double)v1.z);
                        tessellator.func_78377_a((double)v2.x, (double)v2.y, (double)v2.z);
                        if (++lineCnt > maxLine) {
                            return;
                        }
                        tessellator.func_78377_a((double)v2.x, (double)v2.y, (double)v2.z);
                        tessellator.func_78377_a((double)v3.x, (double)v3.y, (double)v3.z);
                        if (++lineCnt > maxLine) {
                            return;
                        }
                        tessellator.func_78377_a((double)v3.x, (double)v3.y, (double)v3.z);
                        tessellator.func_78377_a((double)v1.x, (double)v1.y, (double)v1.z);
                    }
                }
            }
        }
    }
    
    @Override
    public int getVertexNum() {
        return this.vertexNum;
    }
    
    @Override
    public int getFaceNum() {
        return this.faceNum;
    }
    
    @Override
    public void renderAll(int startFace, final int maxFace) {
        if (startFace < 0) {
            startFace = 0;
        }
        final Tessellator tessellator = Tessellator.field_78398_a;
        tessellator.func_78371_b(4);
        this.renderAll(tessellator, startFace, maxFace);
        tessellator.func_78381_a();
    }
    
    public void renderAll(final Tessellator tessellator, final int startFace, final int maxLine) {
        int faceCnt = 0;
        for (final W_GroupObject groupObject : this.groupObjects) {
            if (groupObject.faces.size() > 0) {
                for (final W_Face face : groupObject.faces) {
                    if (++faceCnt < startFace) {
                        continue;
                    }
                    if (faceCnt > maxLine) {
                        return;
                    }
                    face.addFaceForRender(tessellator);
                }
            }
        }
    }
}
