package siedlervoncatan.test;

import java.util.Set;

import siedlervoncatan.utility.Position;

public class PositionTest
{
    public static void main(String[] args)
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

        Set<Position> spos = p1.getNachbarn();
        for (Position position : spos)
        {
            System.out.println(position);
        }
        System.out.println(spos.contains(p2));// true
        System.out.println(spos.contains(p3));// true
        System.out.println(spos.contains(p4));// true
        System.out.println(spos.contains(p5));// true
        System.out.println(spos.contains(p6));// true
        System.out.println(spos.contains(p7));// true
        System.out.println(spos.contains(p9));// false
        System.out.println(p8.isNachbar(p8));// false
        System.out.println(p1.isNachbar(p8));// true
        System.out.println(p2.isNachbar(p4));// false
        System.out.println(p2.isNachbar(p5));// flase
        System.out.println(p2.isNachbar(p6));// false
        System.out.println(p2.isNachbar(p3));// true
    }
}
