/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kapandaria.YDateQuery;

import kapandaria.YDate.YDate;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.BracketPair;
import com.fathzer.soft.javaluator.Constant;
import com.fathzer.soft.javaluator.Function;
import com.fathzer.soft.javaluator.Operator;
import com.fathzer.soft.javaluator.Parameters;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author orr
 */
public class YDateEvaluator extends AbstractEvaluator<Integer>
{

    static final int TRUE = 1;
    static final int FALSE = 0;
    /**
     * The negate unary operator.
     */
    private final static Operator NEGATE = new Operator("!", 1, Operator.Associativity.RIGHT, 5);
    private final static Operator NEGATE_MINUS = new Operator("-", 1, Operator.Associativity.RIGHT, 5);
    /**
     * The logical AND operator.
     */
    private final static Operator AND = new Operator("and", 2, Operator.Associativity.LEFT, 2);
    /**
     * The logical OR operator.
     */
    private final static Operator OR = new Operator("or", 2, Operator.Associativity.LEFT, 1);
    private final static Operator GT = new Operator(">", 2, Operator.Associativity.LEFT, 3);
    private final static Operator GTE = new Operator(">=", 2, Operator.Associativity.LEFT, 3);
    private final static Operator LT = new Operator("<", 2, Operator.Associativity.LEFT, 3);
    private final static Operator LTE = new Operator("<=", 2, Operator.Associativity.LEFT, 3);
    private final static Operator EQ = new Operator("=", 2, Operator.Associativity.LEFT, 3);
    private final static Operator EQ2 = new Operator("==", 2, Operator.Associativity.LEFT, 3);
    private final static Operator UEQ = new Operator("!=", 2, Operator.Associativity.LEFT, 3);
    private final static Operator UEQ2 = new Operator("<>", 2, Operator.Associativity.LEFT, 3);
    /**
     * The subtraction operator.
     */
    public static final Operator MINUS = new Operator("-", 2, Operator.Associativity.LEFT, 4);
    /**
     * The addition operator.
     */
    public static final Operator PLUS = new Operator("+", 2, Operator.Associativity.LEFT, 4);

    public static final Function INSIDE = new Function("inside", 2, Integer.MAX_VALUE);

    private static final Parameters PARAMETERS;

    static
    {
        // Create the evaluator's parameters
        PARAMETERS = new Parameters();
        // Add the supported operators
        PARAMETERS.add(AND);
        PARAMETERS.add(OR);
        PARAMETERS.add(NEGATE);

        PARAMETERS.add(GT);
        PARAMETERS.add(GTE);
        PARAMETERS.add(LT);
        PARAMETERS.add(LTE);
        PARAMETERS.add(EQ);
        PARAMETERS.add(EQ2);
        PARAMETERS.add(UEQ);
        PARAMETERS.add(UEQ2);

        PARAMETERS.add(INSIDE);
        PARAMETERS.addExpressionBracket(BracketPair.PARENTHESES);
        PARAMETERS.addFunctionBracket(BracketPair.PARENTHESES);
    }

    public YDateEvaluator()
    {
        super(PARAMETERS);
    }

    @Override
    protected Integer toValue(String literal, Object evaluationContext)
    {
        try
        {
            return Integer.valueOf(literal);
        }
        catch (NumberFormatException ex)
        {
            return null;
        }
    }

    @Override
    protected Integer evaluate(Operator operator, Iterator<Integer> operands, Object evaluationContext)
    {
        if (operator == NEGATE)
        {
            int o = operands.next();
            return (o == 0) ? 1 : 0;
        }
        else if (operator.getOperandCount() == 2)
        {
            int o1 = operands.next();
            int o2 = operands.next();
            if (operator == OR)
            {
                return ((o1 != FALSE) || (o2 != FALSE)) ? TRUE : FALSE;
            }
            else if (operator == AND)
            {
                return ((o1 != FALSE) && (o2 != FALSE)) ? TRUE : FALSE;
            }
            else if (operator == GT)
            {
                return (o1 > o2) ? TRUE : FALSE;
            }
            else if (operator == GTE)
            {
                return (o1 >= o2) ? TRUE : FALSE;
            }
            else if (operator == LT)
            {
                return (o1 < o2) ? TRUE : FALSE;
            }
            else if (operator == LTE)
            {
                return (o1 <= o2) ? TRUE : FALSE;
            }
            else if (operator == EQ || operator == EQ2)
            {
                return (o1 == o2) ? TRUE : FALSE;
            }
            else if (operator == UEQ || operator == UEQ2)
            {
                return (o1 != o2) ? TRUE : FALSE;
            }
            else if (operator == MINUS)
            {
                return o1 - o2;
            }
            else if (operator == PLUS)
            {
                return o1 + o2;
            }
        }
        return super.evaluate(operator, operands, evaluationContext);
    }

    @Override
    protected Integer evaluate(Function function, Iterator<Integer> arguments,
             Object evaluationContext
    )
    {
        if (INSIDE.equals(function))// checks if the value of the first element exists in the list of all other elements.
        {
            int ofirst = arguments.next();

            while (arguments.hasNext())
            {
                if (ofirst == arguments.next())
                {
                    return TRUE;
                }
            }
            return FALSE;
        }
        else
        {
            return super.evaluate(function, arguments, evaluationContext);
        }
    }

}
