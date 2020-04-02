package core;

import java.io.Serializable;

public class MorraInfo implements Serializable
{
    private static final long serialVersionUID = 5950169519310163575L;
    public int p1Points;
    public int p2Points;
    public String p1Plays;
    public String p2Plays;
    public boolean have2players;

    public MorraInfo() {
        this.p1Points = 0;
        this.p2Points = 0;
        this.p1Plays = null;
        this.p2Plays = null;
        this.have2players = false;
    }

    @Override
    public String toString() {
        return "MorraInfo{" +
                "p1Points=" + p1Points +
                ", p2Points=" + p2Points +
                ", p1Plays='" + p1Plays + '\'' +
                ", p2Plays='" + p2Plays + '\'' +
                ", have2players=" + have2players +
                '}';
    }
}
