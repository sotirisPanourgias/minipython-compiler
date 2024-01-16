import minipython.analysis.DepthFirstAdapter;
import minipython.node.*;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class visitor extends DepthFirstAdapter {

    private Hashtable<String, String> variables;
    private Hashtable<String, String> functions;

    visitor(Hashtable<String, String> variables,Hashtable<String, String> functions){
        this.variables = variables;
        this.functions = functions;
    }
    private String getValueType(PValue value) {
        if (value instanceof AStringValue) {
            return "string";
        }
        if(value instanceof ANumberValue) {
            return "number"; 
        }
        return null;
    }

    private boolean isNumber(PExpression expression) {
        String expressionText = expression.toString();  // Υποθέτοντας ότι η toString() επιστρέφει το κείμενο της έκφρασης
        return expressionText.matches("\\d+|\\d+\\.\\d+");
    }
    
    @Override
    public void inAIdentifierExpression(AIdentifierExpression node) {
        String variable_name = node.getId().getText();
        int line = node.getId().getLine();
        int pos = node.getId().getPos();
        if (!variables.containsKey(variable_name)) {
            System.out.println("Error variable " + variable_name + ", line: " + line + " position: "+ pos+ " does not exist");
        }
    }
    @Override
    public void inAAssignEqStatement(AAssignEqStatement node) {
        String name = node.getId().getText();
        variables.put(name, name);
    }
     @Override
    public void inAFunction(AFunction node) {
        LinkedList<AArgument> arguments = node.getArgument(); //size = 0 or 1
        AArgument argument;
        if (arguments.size() > 0) {
            argument = arguments.getFirst();
            LinkedList<ANotFirstArgument> nfargs = argument.getNotFirstArgument();
            String varName = argument.getId().toString().trim();
            variables.put(varName, varName);
            for (ANotFirstArgument nfarg : nfargs) {
                varName = nfarg.getId().toString().trim();
                variables.put(varName, varName);
            }
        }
    }
    @Override
    public void inAFuncCall(AFuncCall node) {
        String function_name = node.getId().getText();
        int numOfArgs = node.getExpression().size();
        int line = node.getId().getLine();
        int pos = node.getId().getPos();
        if (!functions.containsKey(function_name)) {
            System.out.println("Error function " + function_name + ", line: " + line + " position: " + pos + " does not exist");
        } else {
            String f = functions.get(function_name);
            if (f == null) {
                System.out.println("Error function " + function_name + " with " + numOfArgs + " parameters in line: " + line + " position: " + pos + " does not exist");
            }
        }
    }
    @Override
    public void inAPlusExpression(APlusExpression node) {
        PExpression leftExpression = node.getL();
        PExpression rightExpression = node.getR();
    
        //String leftType = leftExpression;
        //String rightType = getValueType(rightExpression);
        boolean n = isNumber(leftExpression);
        boolean r = isNumber(rightExpression);
        if (leftExpression == null || rightExpression == null || leftExpression.equals(rightExpression)) {
            System.out.println("Error: Incompatible types in addition expression: " + leftExpression + " + " + rightExpression);
            // Εδώ μπορείτε να προσθέσετε περισσότερες λεπτομέρειες για τη γραμμή και τη θέση του λάθους αν χρειαστεί
        }
        if ("none".equals(leftExpression) || "none".equals(rightExpression)) {
            System.out.println("Error: Incompatible type 'None' in addition expression");}
    }
    @Override
    public void inAMultExpression(AMultExpression node) {
        PExpression leftExpression = node.getL();
        PExpression rightExpression = node.getR();
    
        //String leftType = getValueType(leftExpression);
       // String rightType = getValueType(rightExpression);
    
        if (leftExpression == null || rightExpression == null || leftExpression.equals(rightExpression)) {
            System.out.println("Error: Incompatible types in multiplication expression: " + leftExpression + " * " + rightExpression);
            // Εδώ μπορείτε να προσθέσετε περισσότερες λεπτομέρειες για τη γραμμή και τη θέση του λάθους αν χρειαστεί
        }
        if ("none".equals(leftExpression) || "none".equals(rightExpression)) {
            System.out.println("Error: Incompatible type 'None' in addition expression");}
    }
    @Override
    public void inAModExpression(AModExpression node){
        PExpression leftExpression = node.getL();
        PExpression rightExpression = node.getR();
    
        //String leftType = getValueType(leftExpression);
        //String rightType = getValueType(rightExpression);
    
        if (leftExpression == null || rightExpression == null || leftExpression.equals(rightExpression)) {
            System.out.println("Error: Incompatible types in mod expression: " + leftExpression + " mod " + rightExpression);
            // Εδώ μπορείτε να προσθέσετε περισσότερες λεπτομέρειες για τη γραμμή και τη θέση του λάθους αν χρειαστεί
        }
        if ("none".equals(leftExpression) || "none".equals(rightExpression)) {
            System.out.println("Error: Incompatible type 'None' in addition expression");}
    }
    @Override
    public void inAPowerExpression(APowerExpression node){
        PExpression leftExpression = node.getL();
        PExpression rightExpression = node.getR();
    
        //String leftType = getValueType(leftExpression);
        //String rightType = getValueType(rightExpression);
    
        if (leftExpression == null || rightExpression == null || leftExpression.equals(rightExpression)) {
            System.out.println("Error: Incompatible types in power expression: " + leftExpression + " * " + rightExpression);
            // Εδώ μπορείτε να προσθέσετε περισσότερες λεπτομέρειες για τη γραμμή και τη θέση του λάθους αν χρειαστεί
        }
        if ("none".equals(leftExpression) || "none".equals(rightExpression)) {
            System.out.println("Error: Incompatible type 'None' in addition expression");}
    }
    @Override
    public void inAMinusExpression(AMinusExpression node){
        PExpression leftExpression = node.getL();
        PExpression rightExpression = node.getR();
        //String leftType = getValueType((PValue) leftExpression)
        //String leftType = getValueType(leftExpression);
        //String rightType = getValueType(rightExpression);
    
        if (leftExpression == null || rightExpression == null || leftExpression.equals(rightExpression)) {
            System.out.println("Error: Incompatible types in minus expression: " + leftExpression + " -" + rightExpression);
            // Εδώ μπορείτε να προσθέσετε περισσότερες λεπτομέρειες για τη γραμμή και τη θέση του λάθους αν χρειαστεί
        }
        if ("none".equals(leftExpression) || "none".equals(rightExpression)) {
            System.out.println("Error: Incompatible type 'None' in addition expression");}
    }
    


}