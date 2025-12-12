import UI.UIStyle;

public class Main {
    static void main(String[] args) {
        UIStyle.setDefaultsTheme();
        new UI.LoginFrame().setVisible(true);
    }
}
