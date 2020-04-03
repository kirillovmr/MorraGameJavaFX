package core;

import java.io.Serializable;

public class MorraInfo implements Serializable
{
    private static final long serialVersionUID = 5950169519310163575L;
    public int p1ID, p2ID;
    public int p1Points;
    public int p2Points;
    public int p1Plays;
    public int p2Plays;
    public boolean have2players;

    public MorraInfo() {
        this.p1Points = 0;
        this.p2Points = 0;
        this.p1Plays = -1;
        this.p2Plays = -1;
        this.have2players = false;
    }

    public MorraInfo(MorraInfo info) {
        this.p1ID = info.p1ID;
        this.p2ID = info.p2ID;
        this.p1Points = info.p1Points;
        this.p2Points = info.p2Points;
        this.p1Plays = info.p1Plays;
        this.p2Plays = info.p2Plays;
        this.have2players = info.have2players;
    }

    @Override
    public String toString() {
        return "MorraInfo{" +
                "p1ID=" + p1ID +
                ", p2ID=" + p2ID +
                ", p1Points=" + p1Points +
                ", p2Points=" + p2Points +
                ", p1Plays='" + p1Plays + '\'' +
                ", p2Plays='" + p2Plays + '\'' +
                ", have2players=" + have2players +
                '}';
    }
}
