package general;

import org.bson.types.ObjectId;
import org.junit.Test;


public class ObjectIdTests {

    @Test
    public void test() {


        ObjectId id = new ObjectId();
        
        System.out.println( id );
        System.out.println( id.toHexString() );
        System.out.println( id.toStringMongod() );
        
    }

}
