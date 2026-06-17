package siedlervoncatan.test;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import siedlervoncatan.utility.Position;

public class PositionTest
{

    @Test
    public void positionsTest()
    {
        Position p1 = new Position(3, 2);
        Position p2 = new Position(3, 0);
        Position p3 = new Position(4, 1);
        Position p4 = new Position(4, 3);
        Position p5 = new Position(3, 4);
        Position p6 = new Position(2, 3);
        Position p7 = new Position(2, 1);
        Position p8 = new Position(2, 1);
        Position p9 = new Position(1, 1);

        Assert.assertEquals(p9.getxAchse(), 1);
        Assert.assertEquals(p9.getyAchse(), 1);

        Set<Position> spos = p1.getNachbarn();

        Assert.assertTrue(spos.contains(p2));// true
        Assert.assertTrue(spos.contains(p3));// true
        Assert.assertTrue(spos.contains(p4));// true
        Assert.assertTrue(spos.contains(p5));// true
        Assert.assertTrue(spos.contains(p6));// true
        Assert.assertTrue(spos.contains(p7));// true
        Assert.assertFalse(spos.contains(p9));// false

        Assert.assertFalse(p8.isNachbar(p8));// false
        Assert.assertTrue(p1.isNachbar(p8));// true
        Assert.assertFalse(p2.isNachbar(p4));// false
        Assert.assertFalse(p2.isNachbar(p5));// flase
        Assert.assertFalse(p2.isNachbar(p6));// false
        Assert.assertTrue(p2.isNachbar(p3));// true
    }
}
