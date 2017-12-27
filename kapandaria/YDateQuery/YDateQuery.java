/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kapandaria.YDateQuery;

import kapandaria.YDate.YDate;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author orr
 */
public class YDateQuery
{
    
    static final YDateEvaluator eval = new YDateEvaluator();

    public static boolean query(YDateVariableSet ydate_vset,String expression)
    {
        return eval.evaluate(expression,ydate_vset)!=YDateEvaluator.FALSE;
    }
}
