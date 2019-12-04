package com.gcs.invictus.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Common {

    private final Map<String,Object> values = new HashMap<>();

    public static Map<String, Object> object2Map(Object o)
    {
        Logger log = LogManager.getLogger(Common.class);
        Class co = o.getClass();
        Field [] cfields = co.getDeclaredFields();
        Map<String, Object> ret = new HashMap<>();
        for(Field f: cfields)
        {
            String attributeName = f.getName();
            String getterMethodName = "get"
                    + attributeName.substring(0, 1).toUpperCase()
                    + attributeName.substring(1, attributeName.length());
            Method m = null;
            try {
                m = co.getMethod(getterMethodName);
                Object valObject = m.invoke(o);
                ret.put(attributeName, valObject);
            } catch (Exception e) {
                log.error(e);
            }
        }
        return ret;
    }

    public void put( String key, Object value ) {
        values.put( key, value );
    }

    public Object get( String key ) {
        return values.get( key );
    }

    public static String generateUUID(){

        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }








}
