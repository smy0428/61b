public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int diff = (y - x) * (y - x);
        return diff == 1;
    }
}
