public class OffByN implements CharacterComparator {
    int nDiff;
    public OffByN(int N) {
        nDiff = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff = (y - x) * (y - x);
        return diff == nDiff * nDiff;
    }
}
