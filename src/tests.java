public class tests {
    public static void main(String[] args){
        MEE mee1 = new MEE(26);
        MEE mee2 = new MEE(new int[] {0, 2, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});

        System.out.println(mee1.retireAleat());
    }
}
