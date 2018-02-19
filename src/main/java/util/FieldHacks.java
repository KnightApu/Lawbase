package util;

import java.lang.reflect.Field;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;

public class FieldHacks {

    public static void printAllFields( Object obj ) {
        
        Class cls = obj.getClass();
        
        List<Field> fields = FieldUtils.getAllFieldsList( cls );
        
        //System.out.println( fields );
        
        fields.forEach( x -> System.out.println( x.getDeclaringClass().getName() + " " + x.getName() ) ); 
        
        
    }

    
}
