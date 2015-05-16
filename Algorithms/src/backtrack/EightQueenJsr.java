package backtrack;

import org.eclipse.vjet.vsf.jsref.JsFunc;
import org.eclipse.vjet.dsf.common.binding.IValueBinding;
import org.eclipse.vjet.vsf.jsref.JsObj;
import org.eclipse.vjet.vsf.jsref.JsProp;
import org.eclipse.vjet.vsf.jsruntime.jsref.IJsPropSetter;
import org.eclipse.vjet.vsf.jsref.internals.JsCmpMeta;
import org.eclipse.vjet.vsf.jsref.JsTypeRef;
import org.eclipse.vjet.vsf.jsref.JsObjData;
import org.eclipse.vjet.dsf.spec.component.IComponentSpec;
import org.eclipse.vjet.vsf.resource.pattern.js.JsResource;
import org.eclipse.vjet.vsf.resource.pattern.js.IJsResourceRef;
import org.eclipse.vjet.vjo.java.lang.SystemJsr;

@org.eclipse.vjet.dsf.resource.utils.CodeGen("JsrGenerator")
public class EightQueenJsr extends JsObj {
    private static final long serialVersionUID = 1L;

    private static final JsObjData S = 
        new JsObjData("backtrack.EightQueen", EightQueenJsr.class, "EightQueen", true);

    
    public static class ResourceSpec {
        public static IComponentSpec getInstance() {
            return S.getResourceSpec(); 
        }
        public static final JsResource RESOURCE = S.getJsResource();
        public static final IJsResourceRef REF = S.getJsResourceRef();
    }

    public static final IComponentSpec SPEC = S.getResourceSpec()
        .addDependentComponent(SystemJsr.ResourceSpec.getInstance());

    public EightQueenJsr(){
        super(S.getJsCmpMeta(), true);
    }

    protected EightQueenJsr(JsCmpMeta cmpMeta, boolean isInstance, Object... args) {
        super(cmpMeta, isInstance, args);
    }

    public static final JsProp<Integer> BOARD_SIZE(){
        return getProp(S, Integer.class, "BOARD_SIZE");
    }

    public static final JsProp<Boolean> EMPTY(){
        return getProp(S, Boolean.class, "EMPTY");
    }

    public static final JsProp<Boolean> QUEEN(){
        return getProp(S, Boolean.class, "QUEEN");
    }

    public static final JsProp<Integer> MOVES(){
        return getProp(S, Integer.class, "MOVES");
    }

    public static JsFunc<Void> main(String[] s){
        return call(S, "main").with((Object)s);
    }

    public static JsFunc<Void> main(IValueBinding<String[]> s){
        return call(S, "main").with(s);
    }

    public JsProp<Integer> queens(){
        return getProp(Integer.class, "queens");
    }

    public IJsPropSetter queens(int v) {
        return setProp("queens", v);
    }

    public IJsPropSetter queens(IValueBinding<Integer> v) {
        return setProp("queens", v);
    }

    public JsFunc<Boolean> placeQueens(){
        return call(Boolean.class, "placeQueens");
    }

    public JsFunc<Boolean> isUnderAttack(int row, int col){
        return call(Boolean.class, "isUnderAttack").with(row, col);
    }

    public JsFunc<Boolean> isUnderAttack(IValueBinding<Integer> row, IValueBinding<Integer> col){
        return call(Boolean.class, "isUnderAttack").with(row, col);
    }

    public JsFunc<Void> displayBoard(){
        return call("displayBoard");
    }
    
    public static JsTypeRef<EightQueenJsr> prototype = new JsTypeRef<EightQueenJsr>(S);
}