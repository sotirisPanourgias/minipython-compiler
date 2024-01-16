import minipython.analysis.DepthFirstAdapter;
import minipython.node.*;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;


class Function {

    private String name;
    private int line;
    private PStatement statement;
    private int numOfDefaultParameters;
    private int numOfAllParameters;
    private String[] parameters;

    public Function(String name, int line, int numOfDefaultParameters, int numOfAllParameters, PStatement statement, String[] argumentsArray) {
        this.name = name;
        this.line = line;
        this.statement = statement;
        this.numOfDefaultParameters = numOfDefaultParameters;
        this.numOfAllParameters = numOfAllParameters;
        this.parameters = argumentsArray;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public PStatement getStatement() {
        return statement;
    }

    public void setStatement(PStatement statement) {
        this.statement = statement;
    }

    public int getNumOfDefaultParameters() {
        return numOfDefaultParameters;
    }

    public void setNumOfDefaultParameters(int numOfDefaultParameters) {
        this.numOfDefaultParameters = numOfDefaultParameters;
    }

    public int getNumOfAllParameters() {
        return numOfAllParameters;
    }

    public void setNumOfAllParameters(int numOfAllParameters) {
        this.numOfAllParameters = numOfAllParameters;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }
}
public class visitor1 extends DepthFirstAdapter {

    private Hashtable<String, String> variables;
    private Hashtable<String, List<Function>> functions;

    visitor1(Hashtable<String, String> variables,Hashtable<String, List<Function>> functions){
        this.variables = variables;
        this.functions = functions;
    }
    private String getValueType(PValue value) {
        if (value instanceof AStringValue) return "string";
        else if (value instanceof ANumberValue) return "number";
        else if (value instanceof ANoneValue) return "none";
        return null;
    }
    private void putToFunctions(Function f) {
        boolean check = true;
        if (!functions.containsKey(f.getName())) {
            List function = new LinkedList();
            function.add(f);
            functions.put(f.getName(), function);
        }else {
            List<Function> functions1 = (List) functions.get(f.getName());
            int allArgs = f.getNumOfAllParameters();
            int nonDefaults = allArgs - f.getNumOfDefaultParameters();
            for (Function function : functions1) {
                if (function.getNumOfAllParameters() == allArgs ||
                        (function.getNumOfAllParameters() - function.getNumOfDefaultParameters()) == nonDefaults) {
                    check = false;
                    System.out.println("Error on line: "+ f.getLine()+", Function " + f.getName() + " with "+ f.getNumOfAllParameters() + " arguments is already defined on line: " + function.getLine());
                }
            }
            if (check) {
                functions1.add(f);
                functions.put(f.getName(), functions1);
            }
        }
    }

    // TODO: Check if parameter already exists in argumentsArray
    @Override
    public void inAFunction(AFunction node) {
        String name = node.getId().toString().trim();
        int line = node.getId().getLine();
        LinkedList<AArgument> arguments = node.getArgument(); //size = 0 or 1
        AArgument argument;
        String[] argumentsArray = null;
        int defaultArgsNum = 0;
        if (arguments.size() > 0) {
            argument = arguments.getFirst();
            LinkedList<ANotFirstArgument> nfargs = argument.getNotFirstArgument();
            argumentsArray = new String[nfargs.size() + 1];
            String varName = argument.getId().toString().trim();
            String varType = null;
            if (argument.getValue().size() > 0) {
                varType = getValueType((PValue) (argument.getValue().getFirst()));
                defaultArgsNum++;
            }
            argumentsArray[0] = varName;
            int i = 1;
            for (ANotFirstArgument nfarg : nfargs) {
                varName = nfarg.getId().toString().trim();
                varType = null;
                if (nfarg.getValue().size() > 0) {
                    varType = getValueType((PValue) (nfarg.getValue().getFirst()));
                    defaultArgsNum++;
                }
                argumentsArray[i] = varName;
                i++;
            }
        }
        int numOfAllParams = 0;
        if (argumentsArray != null) numOfAllParams = argumentsArray.length;
        Function f = new Function(name, line, defaultArgsNum, numOfAllParams, node.getStatement(), argumentsArray);
        putToFunctions(f);
    }
}
