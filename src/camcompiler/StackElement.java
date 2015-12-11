package camcompiler;


public class StackElement {
    private int size;
    private String type;
    private int regNumber;
    private int pointer;
    private String name;
    
    public StackElement() {
        
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return this.size;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return this.type;
    }
    public void setRegNumber(int regNumber) {
        this.regNumber = regNumber;
    }
    public int getRegNumber() {
        return this.regNumber;
    }
    public void setPointer(int var) {
        this.pointer = var;
    }
    public int getPointer() {
        return this.pointer;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    
}
