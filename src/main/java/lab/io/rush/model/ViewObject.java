package lab.io.rush.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
/**
 * Created by FIN on 2016/12/8.
 */

public class ViewObject {
    private Map<String,Object> objs=new HashMap<String,Object>();

    public void set(String key,Object value){
        objs.put(key,value);
    }

    public Object get(String key){
        return objs.get(key);
    }

}
