package mcheli.eval.eval.ref;

public class RefactorVarName extends RefactorAdapter
{
    protected Class targetClass;
    protected String oldName;
    protected String newName;
    
    public RefactorVarName(final Class targetClass, final String oldName, final String newName) {
        this.targetClass = targetClass;
        this.oldName = oldName;
        this.newName = newName;
        if (oldName == null || newName == null) {
            throw new NullPointerException();
        }
    }
    
    @Override
    public String getNewName(final Object target, final String name) {
        if (!name.equals(this.oldName)) {
            return null;
        }
        if (this.targetClass == null) {
            if (target == null) {
                return this.newName;
            }
        }
        else if (target != null && this.targetClass.isAssignableFrom(target.getClass())) {
            return this.newName;
        }
        return null;
    }
}
