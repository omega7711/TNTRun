package fr.derycube.omega7711.TNTRun.utils;

import lombok.Getter;

@Getter
public enum Values {
    ZERO(new Range(1, 3), 1),
    FIVE(new Range(3, 6), 2),
    TEN(new Range(6, 12), 3),
    TWENTY(new Range(10, 25), 4),
    THIRTY(new Range(25, 35), 5);
    private final Range possiblemoneytohave;
    private final int numoftnt;
    Values(Range range, int numoftnt) {
        this.possiblemoneytohave = range;
        this.numoftnt = numoftnt;
    }
}
