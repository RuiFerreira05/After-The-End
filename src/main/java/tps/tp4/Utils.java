package tps.tp4;

public class Utils {
    public static void printTitle(String title) {
        int size = title.length() + 4;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append("=");
        }
        System.out.println();
        System.out.println(sb.toString());
        System.out.println("  " + title + "  ");
        System.out.println(sb.toString());
    }
}
