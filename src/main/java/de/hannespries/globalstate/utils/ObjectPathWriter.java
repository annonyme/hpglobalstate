package de.hannespries.globalstate.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public class ObjectPathWriter {
    public static void write(Object parent, String path, Object obj) throws ObjectReaderException{
        String[] pathList = path.split(".");
        write(parent, pathList, 0, obj);
    }

    public static void write(Object parent, String[] path, int index, Object obj) throws ObjectReaderException{
        boolean isLastInPath = index == path.length - 1;

        Object result = null;
        if(parent instanceof Map){
            Map parentMap = (Map) parent;
            if(index < path.length - 2){
                if(parentMap.containsKey(path[index])){
                    result = parentMap.get(path[index]);
                }
            }
            else{
                parentMap.put(path[index], obj);
            }
        }
        else if(path[index].endsWith("()")){
            try{
                String methodName = path[index].substring(0, path[index].length() - 2);
                Method method = parent.getClass().getMethod(methodName);
                if(!isLastInPath && method.getParameterCount() == 0 ){
                    method.setAccessible(true);
                    result = method.invoke(parent);
                }
                else if(isLastInPath && method.getParameterCount() == 1 && method.getParameterTypes()[0].isAssignableFrom(parent.getClass())){
                    method.setAccessible(true);
                    method.invoke(parent, obj);
                }
            }
            catch(Exception e){
                throw new ObjectReaderException("method not exists");
            }
        }
        else {
            try{
                Field field = parent.getClass().getField(path[index]);
                field.setAccessible(true);
                if(!isLastInPath){
                    result = field.get(parent);
                }
                else if(isLastInPath && field.getType().isAssignableFrom(obj.getClass())){
                    field.set(parent, obj);
                }
            }
            catch(Exception e){
                throw new ObjectReaderException("field not exists");
            }
        }

        if(result != null && !isLastInPath){
            write(result, path, ++index, obj);
        }
        else if(result == null && !isLastInPath) {
            throw new ObjectReaderException("inner-path null break");
        }
    }
}
