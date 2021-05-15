package mcheli;

public class MCH_ConfigPrm
{
    public final int type;
    public final String name;
    public int prmInt;
    public String prmString;
    public boolean prmBool;
    public double prmDouble;
    public String desc;
    public int prmIntDefault;
    public String validVer;
    
    @Override
    public String toString() {
        if (this.type == 0) {
            return String.valueOf(this.prmInt);
        }
        if (this.type == 1) {
            return this.prmString;
        }
        if (this.type == 2) {
            return String.valueOf(this.prmBool);
        }
        if (this.type == 3) {
            return String.format("%.2f", this.prmDouble).replace(',', '.');
        }
        return "";
    }
    
    public MCH_ConfigPrm(final String parameterName, final int defaultParameter) {
        this.prmInt = 0;
        this.prmString = "";
        this.prmBool = false;
        this.prmDouble = 0.0;
        this.desc = "";
        this.prmIntDefault = 0;
        this.validVer = "";
        this.prmInt = defaultParameter;
        this.prmIntDefault = defaultParameter;
        this.type = 0;
        this.name = parameterName;
    }
    
    public MCH_ConfigPrm(final String parameterName, final String defaultParameter) {
        this.prmInt = 0;
        this.prmString = "";
        this.prmBool = false;
        this.prmDouble = 0.0;
        this.desc = "";
        this.prmIntDefault = 0;
        this.validVer = "";
        this.prmString = defaultParameter;
        this.type = 1;
        this.name = parameterName;
    }
    
    public MCH_ConfigPrm(final String parameterName, final boolean defaultParameter) {
        this.prmInt = 0;
        this.prmString = "";
        this.prmBool = false;
        this.prmDouble = 0.0;
        this.desc = "";
        this.prmIntDefault = 0;
        this.validVer = "";
        this.prmBool = defaultParameter;
        this.type = 2;
        this.name = parameterName;
    }
    
    public MCH_ConfigPrm(final String parameterName, final double defaultParameter) {
        this.prmInt = 0;
        this.prmString = "";
        this.prmBool = false;
        this.prmDouble = 0.0;
        this.desc = "";
        this.prmIntDefault = 0;
        this.validVer = "";
        this.prmDouble = defaultParameter;
        this.type = 3;
        this.name = parameterName;
    }
    
    public boolean compare(final String s) {
        return this.name.equalsIgnoreCase(s);
    }
    
    public boolean isValidVer(final String configVer) {
        if (configVer.length() >= 5 && this.validVer.length() >= 5) {
            final String[] configVerSplit = configVer.split("\\.");
            final String[] validVerSplit = this.validVer.split("\\.");
            if (configVerSplit.length == 3 && validVerSplit.length == 3) {
                for (int i = 0; i < 3; ++i) {
                    final int n1 = Integer.valueOf(configVerSplit[i].replaceAll("[a-zA-Z-_]", "").trim());
                    final int n2 = Integer.valueOf(validVerSplit[i].replaceAll("[a-zA-Z-_]", "").trim());
                    if (n1 > n2) {
                        return true;
                    }
                    if (n1 < n2) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public void setPrm(final int n) {
        if (this.type == 0) {
            this.prmInt = n;
        }
    }
    
    public void setPrm(String s) {
        if (this.type == 0) {
            this.prmInt = Integer.parseInt(s);
        }
        if (this.type == 1) {
            this.prmString = s;
        }
        if (this.type == 2) {
            s = s.toLowerCase();
            if (s.compareTo("true") == 0) {
                this.prmBool = true;
            }
            if (s.compareTo("false") == 0) {
                this.prmBool = false;
            }
        }
        if (this.type == 3 && !s.isEmpty()) {
            this.prmDouble = MCH_Lib.parseDouble(s);
        }
    }
    
    public void setPrm(final boolean b) {
        if (this.type == 2) {
            this.prmBool = b;
        }
    }
    
    public void setPrm(final double f) {
        if (this.type == 3) {
            this.prmDouble = f;
        }
    }
}
