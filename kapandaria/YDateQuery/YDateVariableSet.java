/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kapandaria.YDateQuery;

import kapandaria.YDate.YDate;
import kapandaria.YDate.YDatePreferences;
import static kapandaria.YDateQuery.YDateEvaluator.FALSE;
import static kapandaria.YDateQuery.YDateEvaluator.TRUE;

import com.fathzer.soft.javaluator.AbstractVariableSet;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author orr
 */
public class YDateVariableSet implements AbstractVariableSet<Integer>
{
        YDate m_ydate;
        YDatePreferences.DiasporaType m_diaspora;
        static private interface LambdaQuery {
            int get(YDate ydate, YDatePreferences.DiasporaType diaspora);
        }
        public static final int M_ID_TISHREI = 0;
        public static final int M_ID_CHESHVAN = 1;
        public static final int M_ID_KISLEV = 2;
        public static final int M_ID_TEVET = 3;
        public static final int M_ID_SHEVAT = 4;
        public static final int M_ID_ADAR = 5;
        public static final int M_ID_ADAR_I = 6;
        public static final int M_ID_ADAR_II = 7;
        public static final int M_ID_NISAN = 8;
        public static final int M_ID_IYAR = 9;
        public static final int M_ID_SIVAN = 10;
        public static final int M_ID_TAMMUZ = 11;
        public static final int M_ID_AV = 12;
        public static final int M_ID_ELUL = 13;
        static final Map<String, Integer> constants;
        static final Map<String, LambdaQuery> lambdas;
        static {
            constants = new HashMap<>();
            constants.put("tishrei", M_ID_TISHREI);
            constants.put("cheshvan", M_ID_CHESHVAN);
            constants.put("kislev", M_ID_KISLEV);
            constants.put("tevet", M_ID_TEVET);
            constants.put("shevat", M_ID_SHEVAT);
            constants.put("adar", M_ID_ADAR);
            constants.put("adar_i", M_ID_ADAR_I);
            constants.put("adar_ii", M_ID_ADAR_II);
            constants.put("nisan", M_ID_NISAN);
            constants.put("iyar", M_ID_IYAR);
            constants.put("sivan", M_ID_SIVAN);
            constants.put("tammuz", M_ID_TAMMUZ);
            constants.put("av", M_ID_AV);
            constants.put("elul", M_ID_ELUL);

            lambdas = new HashMap<>();
            lambdas.put("dsb", ((ydate, diaspora) ->
            {
                return ydate.hd.daysSinceBeginning();
            }));
            lambdas.put("weekday", ((ydate, diaspora) ->
            {
                return ydate.hd.dayInWeek();
            }));
            lambdas.put("month_id", ((ydate, diaspora) ->
            {
                return ydate.hd.monthID();
            }));
            lambdas.put("day_in_month", ((ydate, diaspora) ->
            {
                return ydate.hd.dayInMonth();
            }));
            lambdas.put("day_in_year", ((ydate, diaspora) ->
            {
                return ydate.hd.dayInYear();
            }));
            lambdas.put("year", ((ydate, diaspora) ->
            {
                return ydate.hd.year();
            }));
            lambdas.put("shabbaton", ((ydate, diaspora) ->
            {
                return ydate.shabbaton(diaspora)?TRUE:FALSE; //shabbat or yom-tov
            }));
            LambdaQuery rosh_chodesh=((ydate, diaspora) ->
            {
                return ydate.hd.roshChodesh()?TRUE:FALSE;
            });
            lambdas.put("rosh_chodesh", rosh_chodesh);
            lambdas.put("rosh_hodesh", rosh_chodesh);
            LambdaQuery chanukkah=((ydate, diaspora) ->
            {
                return (ydate.hd.dayOfChanukkah()>0)?TRUE:FALSE;
            });
            lambdas.put("chanukkah", chanukkah);
            lambdas.put("hanukkah", chanukkah);
        }

        public YDateVariableSet(YDate ydate,YDatePreferences.DiasporaType diaspora)
        {
            m_ydate=ydate;
            m_diaspora=diaspora;
        }
        @Override
        public Integer get(String variableName) {
            variableName=variableName.toLowerCase();
            if (lambdas.containsKey(variableName))
            {
                return lambdas.get(variableName).get(m_ydate,m_diaspora);
            }
            else if (constants.containsKey(variableName))
            {
                return constants.get(variableName);
            }
            return null;
	}

}
