package camcompiler;

public abstract class SemanticAction {
    LexicAnalyzer lA;
    public SemanticAction(LexicAnalyzer lA){
        this.lA = lA;
    }
    public abstract void run();
}
