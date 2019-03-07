package de.hannespries.globalstate.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class ObjectPathReader {
    public static Object read(Object parent, String path) throws ObjectReaderException{
        String[] pathList = path.split("\\.");
        return read(parent, pathList, 0);
    }

    public static Object read(Object parent, String[] path, int index) throws ObjectReaderException{
        boolean isLastInPath = index == path.length - 1;

        Object result = null;
        if(parent instanceof Map){
            Map parentMap = (Map) parent;
            if(parentMap.containsKey(path[index])){
                result = parentMap.get(path[index]);
            }
            else {
                throw new ObjectReaderException("key not exists");
            }
        }
        else if(path[index].endsWith("()")){
            try{
                String methodName = path[index].substring(0, path[index].length() - 2);
                Method method = parent.getClass().getMethod(methodName);
                if(method.getParameterCount() == 0){
                    method.setAccessible(true);
                    result = method.invoke(parent);
                }
            }
            catch(Exception e){
                throw new ObjectReaderException("method not exists");
            }
        }
        else {
            try{
                Field field = parent.getClass().getDeclaredField(path[index]);
                field.setAccessible(true);
                result = field.get(parent);
            }
            catch(Exception e){
                throw new ObjectReaderException("field not exists");
            }
        }

        if(result != null && !isLastInPath){
            result = read(result, path, ++index);
        }
        else if(result == null && !isLastInPath) {
            throw new ObjectReaderException("inner-path null break");
        }
        return result;
    }
}
