package de.andwari.agon.model.event;

public enum Result {

    G00(0,0), G10(1,0), G01(0,1), G11(1,1), G20(2,0), G02(0,2), G21(2,1), G12(1,2), DEFAULT(0,0);

    public final int p1;
    public final int p2;

    Result(int p1, int p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
}
