public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        int len = word.length();
        Deque<Character> d = new ArrayDeque<>();
        for (int i = 0; i < len; i += 1) {
            d.addLast(word.charAt(i));
        }
        return d;
    }


    public boolean isPalindrome(String word) {
        Deque<Character> w = wordToDeque(word);
        int wSize = w.size();
        if (wSize <= 1) {
            return true;
        }

        int result = 0;
        for (int i = 0; i < wSize / 2; i += 1) {
            if (w.removeFirst() != w.removeLast()) {
                result = 1;
                break;
            }
        }
        return result == 0;
    }


    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> w = wordToDeque(word);
        int wSize = w.size();
        if (wSize <= 1) {
            return true;
        }

        int result = 0;
        for (int i = 0; i < wSize / 2; i += 1) {
            if (!cc.equalChars(w.removeFirst(), w.removeLast())) {
                result = 1;
                break;
            }
        }
        return result == 0;
    }
}
