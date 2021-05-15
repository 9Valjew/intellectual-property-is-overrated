package mcheli.wrapper.modelloader;

import cpw.mods.fml.relauncher.*;
import java.util.regex.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraftforge.client.model.*;
import net.minecraft.client.resources.*;
import java.net.*;
import java.util.*;
import java.io.*;
import net.minecraft.client.renderer.*;

@SideOnly(Side.CLIENT)
public class W_WavefrontObject extends W_ModelCustom
{
    private static Pattern vertexPattern;
    private static Pattern vertexNormalPattern;
    private static Pattern textureCoordinatePattern;
    private static Pattern face_V_VT_VN_Pattern;
    private static Pattern face_V_VT_Pattern;
    private static Pattern face_V_VN_Pattern;
    private static Pattern face_V_Pattern;
    private static Pattern groupObjectPattern;
    private static Matcher vertexMatcher;
    private static Matcher vertexNormalMatcher;
    private static Matcher textureCoordinateMatcher;
    private static Matcher face_V_VT_VN_Matcher;
    private static Matcher face_V_VT_Matcher;
    private static Matcher face_V_VN_Matcher;
    private static Matcher face_V_Matcher;
    private static Matcher groupObjectMatcher;
    public ArrayList<W_Vertex> vertices;
    public ArrayList<W_Vertex> vertexNormals;
    public ArrayList<W_TextureCoordinate> textureCoordinates;
    public ArrayList<W_GroupObject> groupObjects;
    private W_GroupObject currentGroupObject;
    private String fileName;
    
    public W_WavefrontObject(final ResourceLocation resource) throws ModelFormatException {
        this.vertices = new ArrayList<W_Vertex>();
        this.vertexNormals = new ArrayList<W_Vertex>();
        this.textureCoordinates = new ArrayList<W_TextureCoordinate>();
        this.groupObjects = new ArrayList<W_GroupObject>();
        this.fileName = resource.toString();
        try {
            final IResource res = Minecraft.func_71410_x().func_110442_L().func_110536_a(resource);
            this.loadObjModel(res.func_110527_b());
        }
        catch (IOException e) {
            throw new ModelFormatException("IO Exception reading model format", (Throwable)e);
        }
    }
    
    public W_WavefrontObject(final String fileName, final URL resource) throws ModelFormatException {
        this.vertices = new ArrayList<W_Vertex>();
        this.vertexNormals = new ArrayList<W_Vertex>();
        this.textureCoordinates = new ArrayList<W_TextureCoordinate>();
        this.groupObjects = new ArrayList<W_GroupObject>();
        this.fileName = fileName;
        try {
            this.loadObjModel(resource.openStream());
        }
        catch (IOException e) {
            throw new ModelFormatException("IO Exception reading model format", (Throwable)e);
        }
    }
    
    public W_WavefrontObject(final String filename, final InputStream inputStream) throws ModelFormatException {
        this.vertices = new ArrayList<W_Vertex>();
        this.vertexNormals = new ArrayList<W_Vertex>();
        this.textureCoordinates = new ArrayList<W_TextureCoordinate>();
        this.groupObjects = new ArrayList<W_GroupObject>();
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
                if (!currentLine.startsWith("#")) {
                    if (currentLine.length() == 0) {
                        continue;
                    }
                    if (currentLine.startsWith("v ")) {
                        final W_Vertex vertex = this.parseVertex(currentLine, lineCount);
                        if (vertex == null) {
                            continue;
                        }
                        this.checkMinMax(vertex);
                        this.vertices.add(vertex);
                    }
                    else if (currentLine.startsWith("vn ")) {
                        final W_Vertex vertex = this.parseVertexNormal(currentLine, lineCount);
                        if (vertex == null) {
                            continue;
                        }
                        this.vertexNormals.add(vertex);
                    }
                    else if (currentLine.startsWith("vt ")) {
                        final W_TextureCoordinate textureCoordinate = this.parseTextureCoordinate(currentLine, lineCount);
                        if (textureCoordinate == null) {
                            continue;
                        }
                        this.textureCoordinates.add(textureCoordinate);
                    }
                    else if (currentLine.startsWith("f ")) {
                        if (this.currentGroupObject == null) {
                            this.currentGroupObject = new W_GroupObject("Default");
                        }
                        final W_Face face = this.parseFace(currentLine, lineCount);
                        if (face == null) {
                            continue;
                        }
                        this.currentGroupObject.faces.add(face);
                    }
                    else {
                        if (!(currentLine.startsWith("g ") | currentLine.startsWith("o "))) {
                            continue;
                        }
                        if (currentLine.charAt(2) != '$') {
                            continue;
                        }
                        final W_GroupObject group = this.parseGroupObject(currentLine, lineCount);
                        if (group != null && this.currentGroupObject != null) {
                            this.groupObjects.add(this.currentGroupObject);
                        }
                        this.currentGroupObject = group;
                    }
                }
            }
            this.groupObjects.add(this.currentGroupObject);
        }
        catch (IOException e) {
            throw new ModelFormatException("IO Exception reading model format", (Throwable)e);
        }
        finally {
            this.checkMinMaxFinal();
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
        for (final W_GroupObject groupObject : this.groupObjects) {
            if (partName.equalsIgnoreCase(groupObject.name)) {
                groupObject.render();
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
    
    private W_Vertex parseVertex(String line, final int lineCount) throws ModelFormatException {
        final W_Vertex vertex = null;
        if (isValidVertexLine(line)) {
            line = line.substring(line.indexOf(" ") + 1);
            final String[] tokens = line.split(" ");
            try {
                if (tokens.length == 2) {
                    return new W_Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]));
                }
                if (tokens.length == 3) {
                    return new W_Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                }
            }
            catch (NumberFormatException e) {
                throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), (Throwable)e);
            }
            return vertex;
        }
        throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
    }
    
    private W_Vertex parseVertexNormal(String line, final int lineCount) throws ModelFormatException {
        final W_Vertex vertexNormal = null;
        if (isValidVertexNormalLine(line)) {
            line = line.substring(line.indexOf(" ") + 1);
            final String[] tokens = line.split(" ");
            try {
                if (tokens.length == 3) {
                    return new W_Vertex(Float.parseFloat(tokens[0]), Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                }
            }
            catch (NumberFormatException e) {
                throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), (Throwable)e);
            }
            return vertexNormal;
        }
        throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
    }
    
    private W_TextureCoordinate parseTextureCoordinate(String line, final int lineCount) throws ModelFormatException {
        final W_TextureCoordinate textureCoordinate = null;
        if (isValidTextureCoordinateLine(line)) {
            line = line.substring(line.indexOf(" ") + 1);
            final String[] tokens = line.split(" ");
            try {
                if (tokens.length == 2) {
                    return new W_TextureCoordinate(Float.parseFloat(tokens[0]), 1.0f - Float.parseFloat(tokens[1]));
                }
                if (tokens.length == 3) {
                    return new W_TextureCoordinate(Float.parseFloat(tokens[0]), 1.0f - Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]));
                }
            }
            catch (NumberFormatException e) {
                throw new ModelFormatException(String.format("Number formatting error at line %d", lineCount), (Throwable)e);
            }
            return textureCoordinate;
        }
        throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
    }
    
    private W_Face parseFace(final String line, final int lineCount) throws ModelFormatException {
        W_Face face = null;
        if (isValidFaceLine(line)) {
            face = new W_Face();
            final String trimmedLine = line.substring(line.indexOf(" ") + 1);
            final String[] tokens = trimmedLine.split(" ");
            String[] subTokens = null;
            if (tokens.length == 3) {
                if (this.currentGroupObject.glDrawingMode == -1) {
                    this.currentGroupObject.glDrawingMode = 4;
                }
                else if (this.currentGroupObject.glDrawingMode != 4) {
                    throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Invalid number of points for face (expected 4, found " + tokens.length + ")");
                }
            }
            else if (tokens.length == 4) {
                if (this.currentGroupObject.glDrawingMode == -1) {
                    this.currentGroupObject.glDrawingMode = 7;
                }
                else if (this.currentGroupObject.glDrawingMode != 7) {
                    throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Invalid number of points for face (expected 3, found " + tokens.length + ")");
                }
            }
            if (isValidFace_V_VT_VN_Line(line)) {
                face.vertices = new W_Vertex[tokens.length];
                face.textureCoordinates = new W_TextureCoordinate[tokens.length];
                face.vertexNormals = new W_Vertex[tokens.length];
                for (int i = 0; i < tokens.length; ++i) {
                    subTokens = tokens[i].split("/");
                    face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.textureCoordinates[i] = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
                    face.vertexNormals[i] = this.vertexNormals.get(Integer.parseInt(subTokens[2]) - 1);
                }
                face.faceNormal = face.calculateFaceNormal();
            }
            else if (isValidFace_V_VT_Line(line)) {
                face.vertices = new W_Vertex[tokens.length];
                face.textureCoordinates = new W_TextureCoordinate[tokens.length];
                for (int i = 0; i < tokens.length; ++i) {
                    subTokens = tokens[i].split("/");
                    face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.textureCoordinates[i] = this.textureCoordinates.get(Integer.parseInt(subTokens[1]) - 1);
                }
                face.faceNormal = face.calculateFaceNormal();
            }
            else if (isValidFace_V_VN_Line(line)) {
                face.vertices = new W_Vertex[tokens.length];
                face.vertexNormals = new W_Vertex[tokens.length];
                for (int i = 0; i < tokens.length; ++i) {
                    subTokens = tokens[i].split("//");
                    face.vertices[i] = this.vertices.get(Integer.parseInt(subTokens[0]) - 1);
                    face.vertexNormals[i] = this.vertexNormals.get(Integer.parseInt(subTokens[1]) - 1);
                }
                face.faceNormal = face.calculateFaceNormal();
            }
            else {
                if (!isValidFace_V_Line(line)) {
                    throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
                }
                face.vertices = new W_Vertex[tokens.length];
                for (int i = 0; i < tokens.length; ++i) {
                    face.vertices[i] = this.vertices.get(Integer.parseInt(tokens[i]) - 1);
                }
                face.faceNormal = face.calculateFaceNormal();
            }
            return face;
        }
        throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
    }
    
    private W_GroupObject parseGroupObject(final String line, final int lineCount) throws ModelFormatException {
        W_GroupObject group = null;
        if (isValidGroupObjectLine(line)) {
            final String trimmedLine = line.substring(line.indexOf(" ") + 1);
            if (trimmedLine.length() > 0) {
                group = new W_GroupObject(trimmedLine);
            }
            return group;
        }
        throw new ModelFormatException("Error parsing entry ('" + line + "'" + ", line " + lineCount + ") in file '" + this.fileName + "' - Incorrect format");
    }
    
    private static boolean isValidVertexLine(final String line) {
        if (W_WavefrontObject.vertexMatcher != null) {
            W_WavefrontObject.vertexMatcher.reset();
        }
        W_WavefrontObject.vertexMatcher = W_WavefrontObject.vertexPattern.matcher(line);
        return W_WavefrontObject.vertexMatcher.matches();
    }
    
    private static boolean isValidVertexNormalLine(final String line) {
        if (W_WavefrontObject.vertexNormalMatcher != null) {
            W_WavefrontObject.vertexNormalMatcher.reset();
        }
        W_WavefrontObject.vertexNormalMatcher = W_WavefrontObject.vertexNormalPattern.matcher(line);
        return W_WavefrontObject.vertexNormalMatcher.matches();
    }
    
    private static boolean isValidTextureCoordinateLine(final String line) {
        if (W_WavefrontObject.textureCoordinateMatcher != null) {
            W_WavefrontObject.textureCoordinateMatcher.reset();
        }
        W_WavefrontObject.textureCoordinateMatcher = W_WavefrontObject.textureCoordinatePattern.matcher(line);
        return W_WavefrontObject.textureCoordinateMatcher.matches();
    }
    
    private static boolean isValidFace_V_VT_VN_Line(final String line) {
        if (W_WavefrontObject.face_V_VT_VN_Matcher != null) {
            W_WavefrontObject.face_V_VT_VN_Matcher.reset();
        }
        W_WavefrontObject.face_V_VT_VN_Matcher = W_WavefrontObject.face_V_VT_VN_Pattern.matcher(line);
        return W_WavefrontObject.face_V_VT_VN_Matcher.matches();
    }
    
    private static boolean isValidFace_V_VT_Line(final String line) {
        if (W_WavefrontObject.face_V_VT_Matcher != null) {
            W_WavefrontObject.face_V_VT_Matcher.reset();
        }
        W_WavefrontObject.face_V_VT_Matcher = W_WavefrontObject.face_V_VT_Pattern.matcher(line);
        return W_WavefrontObject.face_V_VT_Matcher.matches();
    }
    
    private static boolean isValidFace_V_VN_Line(final String line) {
        if (W_WavefrontObject.face_V_VN_Matcher != null) {
            W_WavefrontObject.face_V_VN_Matcher.reset();
        }
        W_WavefrontObject.face_V_VN_Matcher = W_WavefrontObject.face_V_VN_Pattern.matcher(line);
        return W_WavefrontObject.face_V_VN_Matcher.matches();
    }
    
    private static boolean isValidFace_V_Line(final String line) {
        if (W_WavefrontObject.face_V_Matcher != null) {
            W_WavefrontObject.face_V_Matcher.reset();
        }
        W_WavefrontObject.face_V_Matcher = W_WavefrontObject.face_V_Pattern.matcher(line);
        return W_WavefrontObject.face_V_Matcher.matches();
    }
    
    private static boolean isValidFaceLine(final String line) {
        return isValidFace_V_VT_VN_Line(line) || isValidFace_V_VT_Line(line) || isValidFace_V_VN_Line(line) || isValidFace_V_Line(line);
    }
    
    private static boolean isValidGroupObjectLine(final String line) {
        if (W_WavefrontObject.groupObjectMatcher != null) {
            W_WavefrontObject.groupObjectMatcher.reset();
        }
        W_WavefrontObject.groupObjectMatcher = W_WavefrontObject.groupObjectPattern.matcher(line);
        return W_WavefrontObject.groupObjectMatcher.matches();
    }
    
    public String getType() {
        return "obj";
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
        return this.vertices.size();
    }
    
    @Override
    public int getFaceNum() {
        return this.getVertexNum() / 3;
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
    
    static {
        W_WavefrontObject.vertexPattern = Pattern.compile("(v( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(v( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
        W_WavefrontObject.vertexNormalPattern = Pattern.compile("(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *\\n)|(vn( (\\-){0,1}\\d+\\.\\d+){3,4} *$)");
        W_WavefrontObject.textureCoordinatePattern = Pattern.compile("(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *\\n)|(vt( (\\-){0,1}\\d+\\.\\d+){2,3} *$)");
        W_WavefrontObject.face_V_VT_VN_Pattern = Pattern.compile("(f( \\d+/\\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+/\\d+){3,4} *$)");
        W_WavefrontObject.face_V_VT_Pattern = Pattern.compile("(f( \\d+/\\d+){3,4} *\\n)|(f( \\d+/\\d+){3,4} *$)");
        W_WavefrontObject.face_V_VN_Pattern = Pattern.compile("(f( \\d+//\\d+){3,4} *\\n)|(f( \\d+//\\d+){3,4} *$)");
        W_WavefrontObject.face_V_Pattern = Pattern.compile("(f( \\d+){3,4} *\\n)|(f( \\d+){3,4} *$)");
        W_WavefrontObject.groupObjectPattern = Pattern.compile("([go]( [-\\$\\w\\d]+) *\\n)|([go]( [-\\$\\w\\d]+) *$)");
    }
}
