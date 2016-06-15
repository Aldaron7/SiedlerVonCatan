package siedlervoncatan.utility;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Position implements Serializable
{
    private static final long   serialVersionUID = 1L;
    private static final double X_SKALIERUNG     = Math.sqrt(3.0) / 2.0;
    private int                 xAchse;
    private int                 yAchse;
    private Set<Position>       nachbarn;

    public Position(int xAchse, int yAchse)
    {
        super();
        this.xAchse = xAchse;
        this.yAchse = yAchse;
        this.nachbarn = new HashSet<>();
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.xAchse;
        result = prime * result + this.yAchse;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof Position))
        {
            return false;
        }
        Position other = (Position) obj;
        if (this.xAchse != other.xAchse)
        {
            return false;
        }
        if (this.yAchse != other.yAchse)
        {
            return false;
        }
        return true;
    }

    public Set<Position> getNachbarn()
    {
        if (this.nachbarn.isEmpty())
        {
            this.setNachbarn();
        }
        return this.nachbarn;
    }

    public int getxAchse()
    {
        return this.xAchse;
    }

    public double getXAchseSkaliert()
    {
        return this.xAchse * Position.X_SKALIERUNG;
    }

    public int getyAchse()
    {
        return this.yAchse;
    }

    /**
     * Überprüft, ob Position position Nachbar von this ist.
     * 
     * @param position
     * @return true, wenn position Nachbar ist.
     */
    public boolean isNachbar(Position position)
    {
        int deltaX = Math.abs(this.xAchse - position.xAchse);
        int deltaY = Math.abs(this.yAchse - position.yAchse);
        int summe = deltaX + deltaY;
        return (deltaX <= 1 && deltaY <= 2 && summe == 2);
    }

    /**
     * Erzeugt das Set nachbarn aus allen Nachbarpositionen.
     */
    private void setNachbarn()
    {
        for (int x = -1; x <= 1; x++)
        {
            for (int y = -2; y <= 2; y++)
            {
                int xNeu = this.xAchse + x;
                int yNeu = this.yAchse + y;
                Position posNeu = new Position(xNeu, yNeu);
                if (posNeu.isNachbar(this))
                {
                    this.nachbarn.add(posNeu);
                }
            }
        }
    }

    /**
     * Überprüft, ob Position position eine Nachbarlandschaft ist.
     * 
     * @param position
     * @return true, wenn position Nachbarlandschaft ist.
     */
    public boolean isNachbarlandschaft(Position position)
    {
        int deltaX = Math.abs(this.xAchse - position.xAchse);
        int deltaY = Math.abs(this.yAchse - position.yAchse);
        return ((deltaX == 2 && deltaY == 0) || (deltaX == 1 && deltaY == 3));
    }

    @Override
    public String toString()
    {
        return String.format("(%d,%d)", this.xAchse, this.yAchse);
    }

}
