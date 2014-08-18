import com.gamecopter.wongillalib.scripts.Namespace;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Kevin Wong on 8/13/2014.
 */
public class NamespaceTest {

    @Test
    public void SameFullNameTest()
    {
        Namespace root = Namespace.createRootNamespace("root");
        Namespace a = root.namespace("com.gamecopter.wongillalib");
        Namespace b = root.namespace("com").namespace("gamecopter").namespace("wongillalib");
        Namespace c = root.namespace("com").namespace("gamecopter.wongillalib");
        Namespace d = root.namespace("com.gamecopter").namespace("wongillalib");

        assertEquals(a.getFullName(),  b.getFullName());
        assertEquals(a.getFullName(), b.getFullName());
        assertEquals(a.getFullName(), c.getFullName());
        assertEquals(a.getFullName(), d.getFullName());
        assertEquals(b.getFullName(), c.getFullName());
        assertEquals(b.getFullName(), d.getFullName());
        assertEquals(c.getFullName(), d.getFullName());
    }

    @Test
    public void DifferentFullNameDifferentReferenceTest() {
        Namespace root = Namespace.createRootNamespace("root");
        Namespace a = root.namespace("com.gamecopter.wongillalib");
        Namespace b = root.namespace("com").namespace("gamecopter").namespace("scripts");

        assertNotEquals(a.getFullName(), b.getFullName());
        assertTrue(a != b);

    }

    @Test
    public void SameNameSameReferenceTest()
    {
        Namespace root = Namespace.createRootNamespace("root");
        Namespace a = root.namespace("com.gamecopter.wongillalib");
        Namespace b = root.namespace("com").namespace("gamecopter").namespace("wongillalib");
        Namespace c = root.namespace("com").namespace("gamecopter.wongillalib");
        Namespace d = root.namespace("com.gamecopter").namespace("wongillalib");

        assertTrue(a == b);
        assertTrue(a == c);
        assertTrue(a == d);
        assertTrue(b == c);
        assertTrue(b == d);
        assertTrue(c == d);
     
    }

}
