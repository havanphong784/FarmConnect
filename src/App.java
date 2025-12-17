import UI.UIStyle;

public class App {
    static void main(String[] args) {
        UIStyle.setDefaultsTheme();
        new UI.LoginFrame().setVisible(true);
    }
}


class Solution {
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0 ; i < nums.length ; i++ ) {
            for (int y = 0 ; y < nums.length ; y++ ) {
                if (i + y == target) return new int[] {i,y};
            }
        }
        return null;
    }
}